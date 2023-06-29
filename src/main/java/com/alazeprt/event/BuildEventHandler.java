package com.alazeprt.event;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static com.alazeprt.APResidence.getPrefixW;

public class BuildEventHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Residence res = Residence.getResidenceByLocation(event.getPlayer().getLocation());
        if(res != null){
            if(!res.getPlayer().equals(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中建造!");
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Residence res = Residence.getResidenceByLocation(event.getPlayer().getLocation());
        if(res != null){
            if(!res.getPlayer().equals(event.getPlayer())){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中建造!");
            }
        }
    }
}
