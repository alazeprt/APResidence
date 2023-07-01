package com.alazeprt.event;

import com.alazeprt.util.Residence;
import com.alazeprt.util.ResidenceManager;
import com.alazeprt.util.ResidencePermission;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.message;

public class PvPEventHandler implements Listener {
    @EventHandler
    public void onEntityAttackEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {
            Residence attackerResidence = Residence.getResidenceByLocation(attacker.getLocation());
            Residence victimResidence = Residence.getResidenceByLocation(victim.getLocation());
            if (attackerResidence != null || victimResidence != null) {
                Residence residence = attackerResidence != null ? attackerResidence : victimResidence;
                String owner = residence.getSavedPlayer();
                if (!owner.equals(attacker.getName()) && !owner.equals(victim.getName())) {
                    ResidenceManager manager = new ResidenceManager(residence.getId());
                    if (!manager.hasPermission(attacker, ResidencePermission.PVP) || !manager.hasPermission(victim, ResidencePermission.PVP)) {
                        attacker.sendMessage(getPrefixW() + message.getString("events.pvp.message_with_player").replace("&", "§"));
                        victim.sendMessage(getPrefixW() + message.getString("events.pvp.message_with_player").replace("&", "§"));
                        event.setCancelled(true);
                    }
                } else if(owner.equals(attacker.getName())){
                    ResidenceManager manager = new ResidenceManager(residence.getId());
                    if (!manager.hasPermission(victim, ResidencePermission.PVP)) {
                        attacker.sendMessage(getPrefixW() + message.getString("events.pvp.message_with_player").replace("&", "§"));
                        event.setCancelled(true);
                    }
                } else if(owner.equals(victim.getName())){
                    ResidenceManager manager = new ResidenceManager(residence.getId());
                    if (!manager.hasPermission(attacker, ResidencePermission.PVP)) {
                        attacker.sendMessage(getPrefixW() + message.getString("events.pvp.message_with_player").replace("&", "§"));
                        event.setCancelled(true);
                    }
                }
            }
        } else if (event.getDamager() instanceof Player attacker) {
            Residence attackerResidence = Residence.getResidenceByLocation(attacker.getLocation());
            Residence victimResidence = Residence.getResidenceByLocation(event.getEntity().getLocation());
            if (attackerResidence != null || victimResidence != null) {
                Residence residence = attackerResidence != null ? attackerResidence : victimResidence;
                String owner = residence.getSavedPlayer();
                if (!owner.equals(attacker.getName())) {
                    ResidenceManager manager = new ResidenceManager(residence.getId());
                    if (!manager.hasPermission(attacker, ResidencePermission.PVP)) {
                        attacker.sendMessage(getPrefixW() + message.getString("events.pvp.message_with_entity").replace("&", "§"));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
