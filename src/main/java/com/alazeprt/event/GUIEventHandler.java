package com.alazeprt.event;

import com.alazeprt.util.ResidencePermissionsInventoryHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIEventHandler implements Listener {
    @EventHandler
    public void onMoveItem(InventoryClickEvent event){
        if (event.getInventory().getHolder() instanceof ResidencePermissionsInventoryHolder) {
            event.setCancelled(true);
        }
    }
}
