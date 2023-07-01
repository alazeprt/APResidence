package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        for(String string : list){
            String[] strings = string.split(";");
            if(strings[0].equals(player.getName()) && strings[1].equals(String.valueOf(permission.getId()))){
                change = true;
                list.remove(string);
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
        for(String string : list){
            String[] strings = string.split(";");
            if(strings[0].equals(player.getName())){
                change = true;
                list.remove(string);
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

    public boolean hasPermission(ResidencePermission permission, OfflinePlayer player){
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