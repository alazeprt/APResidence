package com.alazeprt.command;

import com.alazeprt.util.Residence;
import com.alazeprt.util.ResidenceManager;
import com.alazeprt.util.ResidencePermission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class CommandPermission extends ExampleCommand {
    @Override
    public void executeCommand(Player player, String[] args) {
        Residence residence = Residence.getResidenceByLocation(player.getLocation());
        if(residence == null){
            player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_no_exist").replace("&", "§"));
            return;
        }
        if(!Objects.equals(residence.getSavedPlayer(), player.getName())){
            player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_no_me").replace("&", "§"));
            return;
        }
        ResidenceManager manager = new ResidenceManager(residence.getId());
        if(args.length == 2 && args[1].equals("list")){
            Map<String, List<ResidencePermission>> map = manager.listPermission();
            if(map == null){
                player.sendMessage(getPrefixW() + message.getString("commands.permission.list.top").replace("&", "§").replace("$num$", "0"));
                return;
            }
            player.sendMessage(getPrefixW() + message.getString("commands.permission.list.top").replace("&", "§").replace("$num$", String.valueOf(map.size())));
            int j = 1;
            for(String playername : map.keySet()){
                StringBuilder content = new StringBuilder();
                content.append(getPrefixW()).append(message.getString("commands.permission.list.element").replace("&", "§").replace("$index$", String.valueOf(j).replace("$player$", playername)));
                StringBuilder list = new StringBuilder();
                for(int i = 0;i<map.get(playername).size();i++){
                    ResidencePermission permission = map.get(playername).get(i);
                    if(i != map.get(playername).size()-1){
                        list.append(permission.name()).append(", ");
                    } else {
                        list.append(permission.name());
                    }
                }
                content = new StringBuilder(content.toString().replace("$permission_list$", list));
                player.sendMessage(content.toString());
                j++;
            }
            return;
        }
        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[2]);
        if(Objects.equals(player.getName(), player1.getName())){
            player.sendMessage(getPrefixW() + message.getString("commands.exception.give_me_permission").replace("&", "§"));
            return;
        }
        if(args.length == 3 && args[1].equals("remove")){
            boolean end = manager.removeAllPermission(player1);
            if(!end){
                player.sendMessage(getPrefixW() + message.getString("commands.exception.his_permission_no_exist").replace("&", "§"));
            } else {
                player.sendMessage(getPrefixW() + message.getString("commands.success.permission.remove_all").replace("&", "§").replace("$player$", player1.getName()));
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
                player.sendMessage(getPrefixW() + message.getString("commands.exception.permission_no_exist").replace("&", "§"));
                return;
            }
            if(args[1].equals("add")){
                if(manager.hasPermission(player1, permission)){
                    player.sendMessage(getPrefixW() + message.getString("commands.exception.residence_exist").replace("&", "§"));
                    return;
                }
                manager.addPermission(permission, player1);
                player.sendMessage(getPrefixW() + message.getString("commands.success.permission.add").replace("&", "§").replace("$player$", player1.getName()).replace("$permission$", args[3]));
            } else if(args[1].equals("remove")){
                boolean end = manager.removePermission(permission, player1);
                if(!end){
                    player.sendMessage(getPrefixW() + message.getString("commands.exception.his_permission_no_exist").replace("&", "§"));
                } else {
                    player.sendMessage(getPrefixW() + message.getString("commands.success.permission.remove").replace("&", "§").replace("$player$", player1.getName()).replace("$permission$", args[3]));
                }
            }
        }
    }
}
