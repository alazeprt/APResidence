package com.alazeprt.command;

import com.alazeprt.util.PreResidence;
import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class CommandCreate extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Location location = player.getLocation();
        Location location1 = new Location(player.getWorld(), location.getBlockX() - 4, 1, location.getBlockZ() - 4);
        Location location2 = new Location(player.getWorld(), location.getBlockX() + 4, 1, location.getBlockZ() + 4);
        PreResidence residence = new PreResidence(player);
        residence.setLocation1(location1);
        residence.setLocation2(location2);
        if(!residence.canCreate()){
            player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_exist").replace("&", "§"));
        } else {
            residence.create();
            player.sendMessage(getPrefixW() + message.getString("commands.success.create").replace("&", "§"));
        }
    }
}
