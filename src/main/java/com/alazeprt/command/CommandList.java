package com.alazeprt.command;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

import static com.alazeprt.APResidence.getPrefixW;

public class CommandList extends PluginCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        List<Residence> list = Residence.getResidenceList(player);
        player.sendMessage(getPrefixW() + ChatColor.GREEN + "领地列表(" + list.size() + ", 记录为x, z坐标): ");
        int i = 1;
        for(Residence residence : list){
            if(residence.getPlayer().equals(player)){
                String id = ChatColor.GREEN + "领地ID: " + residence.getId();
                String x = ChatColor.GREEN + "起始位置: " + residence.getLocation().getKey().getBlockX() + ", " + residence.getLocation().getKey().getBlockZ();
                String z = ChatColor.GREEN + "终止位置: " + residence.getLocation().getValue().getBlockX() + ", " + residence.getLocation().getValue().getBlockZ();
                player.sendMessage(ChatColor.RED + String.valueOf(i) + ". " + id + ", " + x + ", " + z);
            }
            i++;
        }
    }
}
