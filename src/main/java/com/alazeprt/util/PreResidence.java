package com.alazeprt.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;

public class PreResidence {
    private Location location1 = null;
    private Location location2 = null;
    private final Player player;
    public PreResidence(Player player){
        this.player = player;
    }

    public boolean canCreate(){
        if(location1 != null && location2 != null){
            Residence residence = new Residence(location1, location2, player, Residence.getMaxId()+2);
            return residence.found();
        } else {
            return false;
        }
    }

    public void setLocation1(Location location1){
        this.location1 = location1;
    }

    public void setLocation2(Location location2) {
        this.location2 = location2;
    }

    public Player getPlayer(){
        return player;
    }

    public boolean hasLocation1(){
        return location1 != null;
    }

    public Pair<Boolean, String> create(){
        if(canCreate()){
            Residence residence = new Residence(location1, location2, player, Residence.getMaxId() + 1);
            residence.setOnlinePlayer(player);
            String content = residence.save();
            if(content.equals("")){
                return new Pair<>(true, null);
            } else {
                return new Pair<>(false, getPrefixW() + content.replace("&", "§"));
            }
        } else {
            throw new RuntimeException("Incomplete parameters!");
        }
    }
}
