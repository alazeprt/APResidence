package com.alazeprt.event;

import com.alazeprt.util.Residence;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.alazeprt.APResidence.getPrefixW;

public class PvPEventHandler implements Listener {
    @EventHandler
    public void onEntityAttackEntity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player1 && event.getEntity() instanceof Player player2){
            // 玩家->玩家
            if(Residence.getResidenceByLocation(player1.getLocation()) != null && Residence.getResidenceByLocation(player2.getLocation()) != null){
                Residence residence1 = Residence.getResidenceByLocation(player1.getLocation());
                Residence residence2 = Residence.getResidenceByLocation(player2.getLocation());
                if(residence1 != null){
                    if(residence1.getSavedPlayer().equals(player1.getName()) || residence1.getSavedPlayer().equals(player2.getName())){
                        event.setCancelled(true);
                        player1.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                        player2.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                    }
                } else if(residence2 != null){
                    if(residence2.getSavedPlayer().equals(player1.getName()) || residence2.getSavedPlayer().equals(player2.getName())){
                        event.setCancelled(true);
                        player1.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                        player2.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                    }
                } else{
                    if(residence1.getSavedPlayer().equals(player1.getName()) || residence1.getSavedPlayer().equals(player2.getName())){
                        event.setCancelled(true);
                        player1.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                        player2.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                    }
                    if(residence2.getSavedPlayer().equals(player1.getName()) || residence2.getSavedPlayer().equals(player2.getName())){
                        event.setCancelled(true);
                        player1.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                        player2.sendMessage(getPrefixW() + "你不能在你或对方的领地与对方内PvP!");
                    }
                }
            }
        } else {
            // 玩家->实体
            if(event.getDamager() instanceof Player player) {
                if(Residence.getResidenceByLocation(player.getLocation()) != null){
                    if(!Residence.getResidenceByLocation(player.getLocation()).getSavedPlayer().equals(player.getName())){
                        event.setCancelled(true);
                        player.sendMessage("你不能在他人的领地上攻击生物!");
                    }
                }
            }
        }
    }
}
