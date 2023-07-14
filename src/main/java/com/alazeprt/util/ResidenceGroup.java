package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.alazeprt.APResidence.groups;

public class ResidenceGroup {
    private String group;
    private Integer weight;
    private final Player player;
    public ResidenceGroup(Player player){
        Pair<String, Integer> pair = new Pair<>(null, -2147483646);
        for(String key : getAllGroups()){
            if(player.hasPermission("apresidence.group." + key)){
                int weight = groups.getInt(key + ".weight");
                if(weight >= pair.getValue()){
                    pair.setKey(key);
                    pair.setValue(weight);
                }
            }
        }
        if(Objects.equals(pair.getKey(), null)){
            pair.setKey("default");
            pair.setValue(groups.getInt("default.weight"));
        }
        this.player = player;
        this.group = pair.getKey();
        this.weight = pair.getValue();
    }

    private Set<String> getAllGroups(){
        return groups.getKeys(false);
    }

    public static boolean verifyConfiguration(){
        return groups.get("default") != null;
    }

    public void updateGroup(){
        Pair<String, Integer> pair = new Pair<>(group, weight);
        for(String key : getAllGroups()){
            if(player.hasPermission("apresidence.group." + key)){
                int weight = groups.getInt(key + ".weight");
                if(weight >= pair.getValue()){
                    pair.setKey(key);
                    pair.setValue(weight);
                }
            }
        }
        if(Objects.equals(pair.getKey(), null)){
            pair.setKey("default");
            pair.setValue(groups.getInt("default.weight"));
        }
        this.group = pair.getKey();
        this.weight = pair.getValue();
    }

    public String getGroup() {
        return group;
    }

    public Integer getWeight() {
        return weight;
    }
}
