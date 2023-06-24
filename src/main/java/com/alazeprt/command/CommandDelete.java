package com.alazeprt.command;

import com.alazeprt.util.Residence;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;

public class CommandDelete extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Location location = player.getLocation();
        Residence residence = Residence.getResidenceByLocation(location);
        if(residence == null){
            player.sendMessage(getPrefixW() + ChatColor.RED + "此处没有领地!");
        } else {
            if(player.equals(residence.getPlayer())){
                residence.remove();
                player.sendMessage(getPrefixW() + ChatColor.GREEN + "删除成功!");
            } else {
                player.sendMessage(getPrefixW() + ChatColor.RED + "此领地不属于你! 你无法删除!");
            }
        }
    }
}
