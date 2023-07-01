package com.alazeprt.command;

import com.alazeprt.util.Residence;
import com.alazeprt.util.ResidenceManager;
import com.alazeprt.util.ResidencePermission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

import static com.alazeprt.APResidence.getPrefixW;

public class CommandPermission extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Residence residence = Residence.getResidenceByLocation(player.getLocation());
        if(residence == null){
            player.sendMessage(getPrefixW() + ChatColor.RED + "你所站的地方没有领地!");
            return;
        }
        if(!Objects.equals(residence.getSavedPlayer(), player.getName())){
            player.sendMessage(getPrefixW() + ChatColor.RED + "你所站的地方不是你的领地!");
            return;
        }
        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[2]);
        ResidenceManager manager = new ResidenceManager(residence.getId());
        if(args.length == 3 && args[1].equals("remove")){
            boolean end = manager.removeAllPermission(player1);
            if(!end){
                player.sendMessage(getPrefixW() + ChatColor.RED + "你还没有设置过他在你领地中的权限!");
            }
        } else if(args.length == 4){
            boolean isNum = true;
            int id = -1;
            try {
                id = Integer.parseInt(args[3]);
            } catch (NumberFormatException e){
                isNum = false;
            }
            ResidencePermission permission;
            if(isNum){
                permission = ResidencePermission.getById(id);
            } else {
                permission = ResidencePermission.getByName(args[3]);
            }
            if(permission == null){
                player.sendMessage(getPrefixW() + ChatColor.RED + "你填写的领地权限不存在!");
                return;
            }
            if(args[1].equals("add")){
                manager.addPermission(permission, player1);
            } else if(args[1].equals("remove")){
                boolean end = manager.removePermission(permission, player1);
                if(!end){
                    player.sendMessage(getPrefixW() + ChatColor.RED + "你还没有设置过他在你领地中的权限!");
                }
            }
        }
    }
}
