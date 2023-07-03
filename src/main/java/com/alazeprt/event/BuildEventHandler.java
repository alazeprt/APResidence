package com.alazeprt.event;

import com.alazeprt.util.Residence;
import com.alazeprt.util.ResidenceManager;
import com.alazeprt.util.ResidencePermission;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static com.alazeprt.APResidence.*;

public class BuildEventHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(config.getStringList("DisabledWorld.worlds").contains(event.getPlayer().getWorld().getName())){
            event.getPlayer().sendMessage(getPrefixW() + message.getString("events.in_disable_world"));
            return;
        }
        Residence res = Residence.getResidenceByLocation(event.getBlock().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Place)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + message.getString("events.place.message").replace("&", "§"));
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(config.getStringList("DisabledWorld.worlds").contains(event.getPlayer().getWorld().getName())){
            event.getPlayer().sendMessage(getPrefixW() + message.getString("events.in_disable_world"));
            return;
        }
        Residence res = Residence.getResidenceByLocation(event.getBlock().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Break)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + message.getString("events.break.message").replace("&", "§"));
            }
        }
    }
}
