package com.alazeprt.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.*;

public class CommandHelp extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        if(args.length >= 1 && args[0].equals("permission")){
            player.sendMessage(getPrefixH());
            for(String string : message.getStringList("commands.help.permission")){
                player.sendMessage(string.replace("&", "§"));
            }
            return;
        }
        player.sendMessage(getPrefixH());
        for(String string : message.getStringList("commands.help.help")){
            player.sendMessage(string.replace("&", "§"));
        }
    }

}
