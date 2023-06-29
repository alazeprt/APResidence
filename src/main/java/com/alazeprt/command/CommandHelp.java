package com.alazeprt.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.*;

public class CommandHelp extends PluginCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        player.sendMessage(getPrefixH());
        player.sendMessage(ChatColor.GREEN + "/apres help" + ChatColor.GRAY + "  - 查看APResidence帮助文档");
        player.sendMessage(ChatColor.GREEN + "/apres create" + ChatColor.GRAY + "  - 以你所在的位置作中心, 创建一块9x9的领地");
        player.sendMessage(ChatColor.GREEN + "/apres delete" + ChatColor.GRAY + "  - 删除你所在位置的你自己的领地");
        player.sendMessage(ChatColor.GREEN + "/apres list" + ChatColor.GRAY + "  - 查看你创建的领地列表");
    }

}
