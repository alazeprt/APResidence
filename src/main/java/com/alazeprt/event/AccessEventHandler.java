package com.alazeprt.event;

import com.alazeprt.util.Residence;
import com.alazeprt.util.ResidenceManager;
import com.alazeprt.util.ResidencePermission;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import static com.alazeprt.APResidence.getPrefixW;

public class AccessEventHandler implements Listener {
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event){
        Residence res = Residence.getResidenceByLocation(event.getItemDrop().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Drop)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中丢弃物品!");
            }
        }
    }

    @EventHandler
    public void onPlayerEgg(PlayerEggThrowEvent event){
        Residence res = Residence.getResidenceByLocation(event.getEgg().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Egg)){
                event.getEgg().remove();
                event.setHatching(false);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中扔鸡蛋!");
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){
        Residence res = Residence.getResidenceByLocation(event.getHook().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Fish)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中钓鱼!");
            }
        }
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event){
        Residence res = Residence.getResidenceByLocation(event.getPlayer().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.Portal)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中穿过传送门!");
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        Residence res = Residence.getResidenceByLocation(event.getRightClicked().getLocation());
        if(res != null){
            ResidenceManager manager = new ResidenceManager(res.getId());
            if(!res.getSavedPlayer().equals(event.getPlayer().getName()) && !manager.hasPermission(event.getPlayer(), ResidencePermission.InteractEntity)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地中与生物交互!");
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getClickedBlock() != null) {
            Residence res = Residence.getResidenceByLocation(event.getClickedBlock().getLocation());
            if(res != null){
                ResidenceManager manager = new ResidenceManager(res.getId());
                if(!res.getSavedPlayer().equals(event.getPlayer().getName())){
                    if(event.getClickedBlock() != null){
                        if(event.getAction() == Action.PHYSICAL){
                            if(!manager.hasPermission(event.getPlayer(), ResidencePermission.InteractBlock)){
                                if(event.getClickedBlock().getType().equals(Material.FARMLAND) || event.getClickedBlock().getType().equals(Material.TURTLE_EGG)){
                                    event.setCancelled(true);
                                    event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地上与方块交互!");
                                }
                            }
                        } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                            Material clickedBlockMaterial = event.getClickedBlock().getBlockData().getMaterial();
                            if(!manager.hasPermission(event.getPlayer(), ResidencePermission.InteractBlock)){
                                if (clickedBlockMaterial.equals(Material.ANVIL) || clickedBlockMaterial.equals(Material.ARMOR_STAND) || clickedBlockMaterial.equals(Material.BEACON) || clickedBlockMaterial.name().endsWith("_BED") || clickedBlockMaterial.equals(Material.BEE_NEST) || clickedBlockMaterial.equals(Material.BEEHIVE) || clickedBlockMaterial.equals(Material.BELL) || clickedBlockMaterial.equals(Material.BONE_MEAL) || clickedBlockMaterial.equals(Material.CAKE) || clickedBlockMaterial.equals(Material.CARTOGRAPHY_TABLE) || clickedBlockMaterial.equals(Material.CAULDRON) || clickedBlockMaterial.equals(Material.CAVE_VINES) || clickedBlockMaterial.equals(Material.CAVE_VINES_PLANT) || clickedBlockMaterial.equals(Material.CHIPPED_ANVIL) || clickedBlockMaterial.equals(Material.COMPARATOR) || clickedBlockMaterial.equals(Material.COMMAND_BLOCK) || clickedBlockMaterial.equals(Material.COMPOSTER) || clickedBlockMaterial.equals(Material.CRAFTING_TABLE) || clickedBlockMaterial.equals(Material.DAMAGED_ANVIL) || clickedBlockMaterial.equals(Material.DAYLIGHT_DETECTOR) || clickedBlockMaterial.equals(Material.DRAGON_EGG) || clickedBlockMaterial.name().endsWith("_DOOR") || clickedBlockMaterial.equals(Material.END_CRYSTAL) || clickedBlockMaterial.equals(Material.ENCHANTING_TABLE) || clickedBlockMaterial.equals(Material.END_PORTAL_FRAME) || clickedBlockMaterial.equals(Material.FLINT_AND_STEEL) || clickedBlockMaterial.equals(Material.FURNACE_MINECART) || clickedBlockMaterial.equals(Material.FURNACE) || clickedBlockMaterial.name().endsWith("_FENCE_GATE") || clickedBlockMaterial.equals(Material.GLOW_INK_SAC) || clickedBlockMaterial.equals(Material.GLOW_ITEM_FRAME) || clickedBlockMaterial.equals(Material.GRINDSTONE) || clickedBlockMaterial.equals(Material.HOPPER_MINECART) || clickedBlockMaterial.equals(Material.ITEM_FRAME) || clickedBlockMaterial.equals(Material.JUKEBOX) || clickedBlockMaterial.equals(Material.LAVA_CAULDRON) || clickedBlockMaterial.equals(Material.LECTERN) || clickedBlockMaterial.equals(Material.LEVER) || clickedBlockMaterial.equals(Material.LODESTONE) || clickedBlockMaterial.equals(Material.LOOM) || clickedBlockMaterial.equals(Material.MINECART) || clickedBlockMaterial.equals(Material.NOTE_BLOCK) || clickedBlockMaterial.name().endsWith("_PUMPKIN") || clickedBlockMaterial.name().endsWith("_PRESSURE_PLATE") || clickedBlockMaterial.equals(Material.REDSTONE_WIRE) || clickedBlockMaterial.equals(Material.REPEATER) || clickedBlockMaterial.equals(Material.RESPAWN_ANCHOR) || clickedBlockMaterial.equals(Material.ROOTED_DIRT) || clickedBlockMaterial.equals(Material.STONECUTTER) || clickedBlockMaterial.equals(Material.SMITHING_TABLE) || clickedBlockMaterial.equals(Material.SMOKER) || clickedBlockMaterial.equals(Material.SWEET_BERRY_BUSH) || clickedBlockMaterial.equals(Material.TNT_MINECART) || clickedBlockMaterial.name().endsWith("_TRAPDOOR") || clickedBlockMaterial.equals(Material.WATER_CAULDRON)) {
                                    event.setCancelled(true);
                                    event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地上与方块交互!");
                                }
                            }
                            if(!manager.hasPermission(event.getPlayer(), ResidencePermission.InteractContainerBlock)){
                                if (clickedBlockMaterial.equals(Material.CHEST) || clickedBlockMaterial.equals(Material.BARREL) || clickedBlockMaterial.equals(Material.HOPPER) || clickedBlockMaterial.equals(Material.CHEST_MINECART) || clickedBlockMaterial.equals(Material.HOPPER_MINECART) || clickedBlockMaterial.equals(Material.TRAPPED_CHEST) || clickedBlockMaterial.name().endsWith("_SHULKER_BOX")) {
                                    event.setCancelled(true);
                                    event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "你没有权限在他人领地上与容器方块交互!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
