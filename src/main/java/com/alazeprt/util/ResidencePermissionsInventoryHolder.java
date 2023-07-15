package com.alazeprt.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static com.alazeprt.APResidence.message;

public class ResidencePermissionsInventoryHolder implements InventoryHolder {
    private Inventory inventory;
    public ResidencePermissionsInventoryHolder(Inventory inventory){
        this.inventory = inventory;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void setInventoryItems(Inventory inventory) {
        ItemStack glassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        setItemMeta(0);
        inventory.setItem(0, glassPane);
        setItemMeta(1);
        inventory.setItem(2, glassPane);
        setItemMeta(2);
        inventory.setItem(4, glassPane);
        setItemMeta(3);
        inventory.setItem(6, glassPane);
        setItemMeta(4);
        inventory.setItem(8, glassPane);
        setItemMeta(5);
        inventory.setItem(9, glassPane);
        setItemMeta(6);
        inventory.setItem(11, glassPane);
        setItemMeta(7);
        inventory.setItem(13, glassPane);
        setItemMeta(8);
        inventory.setItem(15, glassPane);
        setItemMeta(9);
        inventory.setItem(17, glassPane);
    }

    private void setItemMeta(int id) {
        if (inventory.getItem(id) == null || inventory.getItem(id).getType() == Material.AIR) {
            return;
        }
        ItemStack itemStack = inventory.getItem(id);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        int index;
        if (id <= 4) {
            index = id * 2;
        } else {
            index = id * 2 - 1;
        }
        if (index == -1) {
            return;
        }
        itemMeta.setDisplayName("§c" + ResidencePermission.getById(id).name());
        itemMeta.setLore(Arrays.asList("§a" + message.getString("events." + ResidencePermission.getById(id).name().toLowerCase() + ".description")));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(index, itemStack);
    }

}
