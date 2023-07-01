package com.alazeprt.command;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class CommandList extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        List<Residence> list = Residence.getResidenceList(player);
        player.sendMessage(getPrefixW() + message.getString("commands.list.top").replace("&", "§").replace("$num$", String.valueOf(list.size())));
        int i = 1;
        for(Residence residence : list){
            if(residence.getSavedPlayer().equals(player.getName())){
                String id = message.getString("commands.list.element.id").replace("&", "§").replace("$id$", String.valueOf(residence.getId()));
                String location1 = message.getString("commands.list.element.location1").replace("&", "§").replace("$x$", String.valueOf(residence.getLocation().getKey().getBlockX())).replace("$z$", String.valueOf(residence.getLocation().getKey().getBlockZ()));
                String location2 = message.getString("commands.list.element.location2").replace("&", "§").replace("$x$", String.valueOf(residence.getLocation().getValue().getBlockX())).replace("$z$", String.valueOf(residence.getLocation().getValue().getBlockZ()));
                player.sendMessage(message.getString("commands.list.element.result").replace("&", "§").replace("$order_number$", String.valueOf(i)).replace("$id$", id).replace("$location1$", location1).replace("$location2$", location2));
            }
            i++;
        }
    }
}
