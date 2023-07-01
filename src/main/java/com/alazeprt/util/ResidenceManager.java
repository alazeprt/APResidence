package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.*;

import static com.alazeprt.APResidence.data;

public class ResidenceManager {
    private final Integer id;
    private final File DataFolder = APResidence.getProvidingPlugin(APResidence.class).getDataFolder();
    public ResidenceManager(int residenceid){
        this.id = residenceid;
    }

    public void addPermission(ResidencePermission permission, OfflinePlayer player){
        List<String> list;
        if(hasPermissionSetting() && data.getStringList("residence." + id + ".permissions").size() != 0){
            list = data.getStringList("residence." + id + ".permissions");
        } else {
            list = new ArrayList<>();
        }
        String str = player.getName() + ";" + permission.getId();
        list.add(str);
        data.set("residence." + id + ".permissions", list);
        try {
            data.save(new File(DataFolder, "data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removePermission(ResidencePermission permission, OfflinePlayer player){
        List<String> list;
        if(hasPermissionSetting() && data.getStringList("residence." + id + ".permissions").size() != 0){
            list = data.getStringList("residence." + id + ".permissions");
        } else {
            return false;
        }
        boolean change = false;
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String string = iterator.next();
            String[] strings = string.split(";");
            if(strings[0].equals(player.getName()) && strings[1].equals(String.valueOf(permission.getId()))){
                change = true;
                iterator.remove();
            }
        }
        if(!change){
            return false;
        } else {
            data.set("residence." + id + ".permissions", list);
            try {
                data.save(new File(DataFolder, "data.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean removeAllPermission(OfflinePlayer player){
        List<String> list;
        if(hasPermissionSetting() && data.getStringList("residence." + id + ".permissions").size() != 0){
            list = data.getStringList("residence." + id + ".permissions");
        } else {
            return false;
        }
        boolean change = false;
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            String string = iterator.next();
            String[] strings = string.split(";");
            if(strings[0].equals(player.getName())){
                change = true;
                iterator.remove();
            }
        }
        if(!change){
            return false;
        } else {
            data.set("residence." + id + ".permissions", list);
            try {
                data.save(new File(DataFolder, "data.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public boolean hasPermissionSetting(){
        if(data.contains("residence." + id + ".permissions")){
            return true;
        } else {
            return false;
        }
    }

    public Map<String, List<ResidencePermission>> listPermission(){
        List<String> list;
        if(hasPermissionSetting() && data.getStringList("residence." + id + ".permissions").size() != 0){
            list = data.getStringList("residence." + id + ".permissions");
        } else {
            return null;
        }
        Map<String, List<ResidencePermission>> map = new HashMap<>();
        for(String string : list){
            String[] strings = string.split(";");
            if(!map.containsKey(strings[0])){
                List<ResidencePermission> list1 = new ArrayList<>();
                list1.add(ResidencePermission.getById(Integer.parseInt(strings[1])));
                map.put(strings[0], list1);
            } else {
                map.get(strings[0]).add(ResidencePermission.getById(Integer.parseInt(strings[1])));
            }
        }
        return map;
    }

    public boolean hasPermission(OfflinePlayer player, ResidencePermission permission){
        List<String> list;
        if(hasPermissionSetting() && data.getStringList("residence." + id + ".permissions").size() != 0){
            list = data.getStringList("residence." + id + ".permissions");
        } else {
            return false;
        }
        for(String string : list){
            String[] strings = string.split(";");
            if(strings[0].equals(player.getName()) && strings[1].equals(String.valueOf(permission.getId()))){
                return true;
            }
        }
        return false;
    }
}