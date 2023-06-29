package com.alazeprt.event;

import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.alazeprt.APResidence.getPrefixW;

public class ContainerEventHandler implements Listener {

    @EventHandler
    public void onPlayerInterfact(PlayerInteractEvent event){
        Residence res = Residence.getResidenceByLocation(event.getClickedBlock().getLocation());
        if(res != null){
            if(!res.getSavedPlayer().equals(event.getPlayer().getName())){
                Material clickedBlockMaterial = event.getClickedBlock().getBlockData().getMaterial();
                if (clickedBlockMaterial.equals(Material.CHEST) || clickedBlockMaterial.equals(Material.BARREL) || clickedBlockMaterial.equals(Material.HOPPER) || clickedBlockMaterial.equals(Material.CHEST_MINECART) || clickedBlockMaterial.equals(Material.HOPPER_MINECART) || clickedBlockMaterial.equals(Material.TRAPPED_CHEST) || clickedBlockMaterial.name().endsWith("_SHULKER_BOX")) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地上与容器方块交互!");
                }
            }
        }
    }
}
