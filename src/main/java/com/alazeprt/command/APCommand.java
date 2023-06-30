package com.alazeprt.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.getPrefixW;

public class APCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
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
                    sender.sendMessage(getPrefixW() + ChatColor.RED + "错误的指令用法! 请输入/apres查看帮助文档!");
                }
            } else if(args.length == 3){
                if(args[0].equals("permission") && args[1].equals("remove")){
                    new CommandPermission().executeCommand((Player) sender, args);
                }
            } else if(args.length == 4){
                if(args[0].equals("permission") && args[1].equals("remove")){
                    new CommandPermission().executeCommand((Player) sender, args);
                } else if(args[0].equals("permission") && args[1].equals("add")){
                    new CommandPermission().executeCommand((Player) sender, args);
                }
            } else{
                sender.sendMessage(getPrefixW() + ChatColor.RED + "错误的指令用法! 请输入/apres查看帮助文档!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "此指令只能由玩家执行!");
        }
        return false;
    }
}
