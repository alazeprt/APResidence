package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.alazeprt.APResidence.*;
import static java.lang.Math.abs;

public class Residence {
    private final Location location1;
    private final Location location2;
    private final OfflinePlayer player;
    private Player oplayer = null;
    private final Integer id;
    private final File dataFolder = APResidence.getProvidingPlugin(APResidence.class).getDataFolder();
    public Residence(Location location1, Location location2, OfflinePlayer player, int id){
        this.location1 = location1;
        this.location2 = location2;
        this.player = player;
        this.id = id;
    }

    public void setOnlinePlayer(Player player){
        oplayer = player;
    }

    public boolean hasOnlinePlayer(){
        return oplayer != null;
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

    public String save(){
        int id = canCreate();
        if(id != -1){
            if(id == 0){
                return message.getString("commands.create_exception.unknown");
            } else if(id == 1){
                return message.getString("commands.create_exception.no_permission_to_create");
            } else if(id == 2){
                return message.getString("commands.create_exception.residence_length_too_long");
            } else if(id == 3){
                return message.getString("commands.create_exception.residence_size_too_big");
            } else if(id == 4){
                return message.getString("commands.create_exception.residence_count_too_much");
            } else if(id == 5){
                return message.getString("commands.create_exception.residence_block_total_too_much");
            } else {
                return message.getString("commands.create_exception.unknown");
            }
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
            return "";
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

    public static Residence getResidenceByID(int id, OfflinePlayer player){
        List<Residence> residences = getResidenceList();
        for(Residence res : residences){
            if(res.getSavedPlayer().equals(player.getName()) && res.getId().equals(id)){
                return res;
            }
        }
        return null;
    }

    public boolean remove(){
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

    public Integer canCreate(){
        if(!hasOnlinePlayer()){
            return 0;
        }
        ResidenceGroup group = new ResidenceGroup(oplayer);
        String groupname = group.getGroup();
        if(!message.getBoolean(groupname + ".Residence.CanCreate")){
            return 1;
        }
        int x1 = location1.getBlockX();
        int z1 = location1.getBlockZ();
        int x2 = location2.getBlockX();
        int z2 = location2.getBlockZ();
        long size = (long) abs(x1 - x2) * abs(z1 - z2);
        if(abs(x1-x2) > message.getInt(groupname + ".Residence.max_x") || abs(z1-z2) > message.getInt(groupname + ".Residence.max_z")){
            return 2;
        }
        if(size > message.getLong(groupname + ".Residence.max_size")){
            return 3;
        }
        List<Residence> list = getResidenceList(player);
        if(list.size() >= message.getInt(groupname + ".Residence.max")){
            return 4;
        }
        long total = 0;
        for(Residence residence : list){
            Location location_1 = residence.getLocation().getKey();
            Location location_2 = residence.getLocation().getValue();
            total += ((long) abs(location_1.getBlockX() - location_2.getBlockX()) * abs(location_1.getBlockZ() - location_2.getBlockZ()));
        }
        total += size;
        if(total > message.getLong(groupname + ".Residence.total")){
            return 5;
        }
        return -1;
    }
}
