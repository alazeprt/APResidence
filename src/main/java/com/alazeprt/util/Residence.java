package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.alazeprt.APResidence.*;

public class Residence {
    private Location location1;
    private Location location2;
    private Player player;
    private final Integer id;
    private final File dataFolder = APResidence.getProvidingPlugin(APResidence.class).getDataFolder();
    public Residence(Location location1, Location location2, Player player, int id){
        this.location1 = location1;
        this.location2 = location2;
        this.player = player;
        this.id = id;
    }

    public Pair<Location, Location> getLocation(){
        return new Pair<>(location1, location2);
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getId() {
        return id;
    }

    public boolean save(){
        if(exist()){
            return false;
        } else {
            updateMaxId(id);
            data.set("residence." + id + ".location1", location1);
            data.set("residence." + id + ".location2", location2);
            data.set("residence." + id + ".player", player.getName());
            try {
                data.save(new File(dataFolder, "data.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
    }

    public boolean exist(){
        if(data.contains("residence." + id + ".location1") && data.contains("residence." + id + ".location2") && data.contains("residence." + id + ".player")){
            return true;
        } else {
            return false;
        }
    }

    public static List<Residence> getResidenceList(){
        List<Residence> residences = new ArrayList<>();
        int maxid = getMaxId();
        for(int id = 1;id<=maxid;id++){
            if(data.contains("residence." + id + ".location1") || data.contains("residence." + id + ".location2") || data.contains("residence." + id + ".player")){
                residences.add(new Residence(data.getLocation("residence." + id + ".location1"), data.getLocation("residence." + id + ".location2"), Bukkit.getPlayer(data.getString("residence." + id + ".player")), id));
                id++;
            } else{
                break;
            }
        }
        return residences;
    }

    public static List<Residence> getResidenceList(Player player){
        List<Residence> residences = new ArrayList<>();
        int id = 1;
        while(true){
            if(data.contains("residence." + id + ".location1") || data.contains("residence." + id + ".location2") || data.contains("residence." + id + ".player")){
                residences.add(new Residence(data.getLocation("residence." + id + ".location1"), data.getLocation("residence." + id + ".location2"), Bukkit.getPlayer(data.getString("residence." + id + ".player")), id));
                id++;
            } else{
                break;
            }
        }
        return residences;
    }

    public static Residence getResidenceByLocation(Location playerLocation){
        List<Residence> residences = getResidenceList();
        for(Residence res : residences){
            if(isPlayerInRegion(playerLocation, res.getLocation().getKey(), res.getLocation().getValue())){
                return res;
            }
        }
        return null;
    }

    public boolean remove(){
        if(!exist()){
            return false;
        } else {
            data.set("residence." + id, null);
            try {
                data.save(new File(dataFolder, "data.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
    }

    public static Integer getMaxId(){
        return data.getInt("maxid");
    }

    public void updateMaxId(int maxid){
        data.set("maxid", maxid);
        try {
            data.save(new File(dataFolder, "data.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPlayerInRegion(Location playerLocation, Location res1, Location res2) {
        double x1 = Math.min(res1.getBlockX(), res2.getBlockX());
        double x2 = Math.max(res1.getBlockX(), res2.getBlockX());
        double z1 = Math.min(res1.getBlockZ(), res2.getBlockZ());
        double z2 = Math.max(res1.getBlockZ(), res2.getBlockZ());

        return playerLocation.getBlockX() >= x1 && playerLocation.getBlockX() <= x2 && playerLocation.getBlockZ() >= z1 && playerLocation.getBlockZ() <= z2;
    }
}
