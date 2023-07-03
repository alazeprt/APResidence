package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.alazeprt.APResidence.*;

public class Residence {
    private Location location1;
    private Location location2;
    private OfflinePlayer player;
    private final Integer id;
    private final File dataFolder = APResidence.getProvidingPlugin(APResidence.class).getDataFolder();
    public Residence(Location location1, Location location2, OfflinePlayer player, int id){
        this.location1 = location1;
        this.location2 = location2;
        this.player = player;
        this.id = id;
    }

    public Pair<Location, Location> getLocation(){
        return new Pair<>(location1, location2);
    }

    public String getSavedPlayer() {
        return player.getName();
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
            if(config.getString("SaveMode").equals("REAL_TIME")){
                try {
                    data.save(new File(dataFolder, "data.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
    }

    public boolean found(){
        for(Residence residence : getResidenceList()){
            if(isOfflinePlayerInRegion(location1, residence.location1, residence.location2) || isOfflinePlayerInRegion(location2, residence.location1, residence.location2)){
                return false;
            }
        }
        return true;
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
            if(data.contains("residence." + id + ".location1") && data.contains("residence." + id + ".location2") && data.contains("residence." + id + ".player")){
                residences.add(new Residence(data.getLocation("residence." + id + ".location1"), data.getLocation("residence." + id + ".location2"), Bukkit.getOfflinePlayer(data.getString("residence." + id + ".player")), id));
            }
        }
        return residences;
    }

    @Deprecated
    public static List<Residence> getResidenceList(OfflinePlayer player){
        List<Residence> residences = new ArrayList<>();
        int maxid = getMaxId();
        for(int id = 1;id<=maxid;id++){
            if(data.contains("residence." + id + ".location1") && data.contains("residence." + id + ".location2") && data.contains("residence." + id + ".player")){
                if(data.getString("residence." + id + ".player").equals(player.getName())){
                    residences.add(new Residence(data.getLocation("residence." + id + ".location1"), data.getLocation("residence." + id + ".location2"), Bukkit.getOfflinePlayer(data.getString("residence." + id + ".player")), id));
                }
            }
        }
        return residences;
    }

    public static Residence getResidenceByLocation(Location playerLocation){
        List<Residence> residences = getResidenceList();
        for(Residence res : residences){
            if(isOfflinePlayerInRegion(playerLocation, res.getLocation().getKey(), res.getLocation().getValue())){
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
            if(config.getString("SaveMode").equals("REAL_TIME")){
                try {
                    data.save(new File(dataFolder, "data.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
    }

    public static Integer getMaxId(){
        return data.getInt("maxid");
    }

    public void updateMaxId(int maxid){
        data.set("maxid", maxid);
        if(config.getString("SaveMode").equals("REAL_TIME")){
            try {
                data.save(new File(dataFolder, "data.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isOfflinePlayerInRegion(Location location, Location res1, Location res2) {
        double x1 = Math.min(res1.getBlockX(), res2.getBlockX());
        double x2 = Math.max(res1.getBlockX(), res2.getBlockX());
        double z1 = Math.min(res1.getBlockZ(), res2.getBlockZ());
        double z2 = Math.max(res1.getBlockZ(), res2.getBlockZ());

        return location.getBlockX() >= x1 && location.getBlockX() <= x2 && location.getBlockZ() >= z1 && location.getBlockZ() <= z2;
    }
}
