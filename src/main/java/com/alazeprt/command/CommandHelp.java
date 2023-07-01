package com.alazeprt.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.alazeprt.APResidence.*;

public class CommandHelp extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        if(args[0].equals("permission")){
            player.sendMessage(getPrefixH());
            player.sendMessage(ChatColor.YELLOW + "此帮助为领地权限板块帮助, 其中<>为必填项, []为选填项");
            player.sendMessage(ChatColor.GREEN + "/apres permission add <玩家名> <权限ID/权限名称>" + ChatColor.GRAY + "  - 给予玩家此领地的给定权限");
            player.sendMessage(ChatColor.GREEN + "/apres permission remove <玩家名> [权限ID/权限名称]" + ChatColor.GRAY + "  - 移除玩家此领地的给定权限(若未给定权限, 则移除所有)");
            player.sendMessage(ChatColor.GREEN + "/apres permission list" + ChatColor.GRAY + "  - 查看此领地每位玩家所有的权限");
        }
        player.sendMessage(getPrefixH());
        player.sendMessage(ChatColor.GREEN + "/apres help" + ChatColor.GRAY + "  - 查看APResidence帮助文档");
        player.sendMessage(ChatColor.GREEN + "/apres create" + ChatColor.GRAY + "  - 以你所在的位置作中心, 创建一块9x9的领地");
        player.sendMessage(ChatColor.GREEN + "/apres delete" + ChatColor.GRAY + "  - 删除你所在位置的你自己的领地");
        player.sendMessage(ChatColor.GREEN + "/apres list" + ChatColor.GRAY + "  - 查看你创建的领地列表");
        player.sendMessage(ChatColor.GREEN + "/apres permission help" + ChatColor.GRAY + "  - 查看有关领地权限的帮助");
    }

}
