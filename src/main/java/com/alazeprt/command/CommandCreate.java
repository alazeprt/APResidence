package com.alazeprt.command;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;

public class CommandCreate extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Location location = player.getLocation();
        Location location1 = new Location(player.getWorld(), location.getBlockX() - 4, 1, location.getBlockZ() - 4);
        Location location2 = new Location(player.getWorld(), location.getBlockX() + 4, 1, location.getBlockZ() + 4);
        Residence residence = new Residence(location1, location2, player, Residence.getMaxId() + 1);
        residence.save();
        player.sendMessage(getPrefixW() + ChatColor.GREEN + "成功创建领地!");
    }
}
