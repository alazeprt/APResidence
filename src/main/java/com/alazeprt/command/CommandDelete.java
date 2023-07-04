package com.alazeprt.command;

import com.alazeprt.util.Residence;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class CommandDelete extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        if(args.length == 1){
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
        } else if(args.length == 2){
            try{
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(getPrefixW() + message.getString("commands.exception.delete_exception").replace("&", "§"));
                return;
            }
            int n = Integer.parseInt(args[1]);
            Residence residence = Residence.getResidenceByID(n, player);
            if(residence == null){
                player.sendMessage(getPrefixW() + message.getString("commands.exception.delete_exception").replace("&", "§"));
            } else {
                residence.remove();
                player.sendMessage(getPrefixW() + message.getString("commands.success.delete").replace("&", "§"));

            }
        }
    }
}
