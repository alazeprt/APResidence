package com.alazeprt.command;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class CommandDelete extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Location location = player.getLocation();
        Residence residence = Residence.getResidenceByLocation(location);
        if(residence == null){
            player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_no_exist").replace("&", "§"));
        } else {
            if(player.getName().equals(residence.getSavedPlayer())){
                residence.remove();
                player.sendMessage(getPrefixW() + message.getString("commands.success.delete").replace("&", "§"));
            } else {
                player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_no_me").replace("&", "§"));
            }
        }
    }
}
