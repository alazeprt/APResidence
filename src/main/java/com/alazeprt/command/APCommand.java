package com.alazeprt.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.alazeprt.APResidence.*;

public class APCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player){
            if(config.getStringList("DisabledWorld.worlds").contains(((Player) sender).getWorld().getName())){
                sender.sendMessage(getPrefixW() + message.getString("commands.exception.in_disable_world"));
                return false;
            }
            if(args.length == 0){
                new CommandHelp().executeCommand((Player) sender, args);
            } else if(args.length == 1){
                if(args[0].equals("help")){
                    new CommandHelp().executeCommand((Player) sender, args);
                } else if(args[0].equals("create")){
                    new CommandCreate().executeCommand((Player) sender, args);
                } else if(args[0].equals("delete")){
                    new CommandDelete().executeCommand((Player) sender, args);
                } else if(args[0].equals("list")){
                    new CommandList().executeCommand((Player) sender, args);
                } else{
                    sender.sendMessage(getPrefixW() + message.getString("commands.exception.wrong_usage").replace("&", "§"));
                }
            } else if(args.length == 2) {
                if(args[0].equals("permission") && args[1].equals("list")){
                    new CommandPermission().executeCommand((Player) sender, args);
                } else if(args[0].equals("permission") && args[1].equals("help")){
                    new CommandHelp().executeCommand((Player) sender, args);
                } else {
                    sender.sendMessage(getPrefixW() + message.getString("commands.exception.wrong_usage").replace("&", "§"));
                }
            } else if(args.length == 3){
                if(args[0].equals("permission") && args[1].equals("remove")){
                    new CommandPermission().executeCommand((Player) sender, args);
                } else {
                    sender.sendMessage(getPrefixW() + message.getString("commands.exception.wrong_usage").replace("&", "§"));
                }
            } else if(args.length == 4){
                if(args[0].equals("permission") && args[1].equals("remove")){
                    new CommandPermission().executeCommand((Player) sender, args);
                } else if(args[0].equals("permission") && args[1].equals("add")){
                    new CommandPermission().executeCommand((Player) sender, args);
                } else {
                    sender.sendMessage(getPrefixW() + message.getString("commands.exception.wrong_usage").replace("&", "§"));
                }
            } else{
                sender.sendMessage(getPrefixW() + message.getString("commands.exception.wrong_usage").replace("&", "§"));
            }
        } else {
            sender.sendMessage(getPrefixW() + message.getString("commands.exception.only_player").replace("&", "§"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1){
            list.add("create");
            list.add("delete");
            list.add("help");
            list.add("list");
            list.add("permission");
        } else if(args.length == 2){
            if(args[0].equals("permission")){
                list.add("add");
                list.add("help");
                list.add("list");
                list.add("remove");
            }
        } else if(args.length == 3){
            for(Player player : Bukkit.getOnlinePlayers()){
                list.add(player.getName());
            }
        } else if(args.length == 4){
            if(args[0].equals("permission") && args[1].equals("add") || args[1].equals("remove")){
                list.add("drop");
                list.add("egg");
                list.add("fish");
                list.add("portal");
                list.add("interactentity");
                list.add("interactblock");
                list.add("interactcontainerblock");
                list.add("place");
                list.add("break");
                list.add("pvp");
            }
        }
        return list;
    }
}
