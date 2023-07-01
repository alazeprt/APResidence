package com.alazeprt.event;

import com.alazeprt.util.PreResidence;
import com.alazeprt.util.Residence;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import static com.alazeprt.APResidence.*;

public class ResidenceEventHandler implements Listener {
    @EventHandler
    public void onPlayerTake(PlayerItemHeldEvent event){
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null){
            if(item.getType().equals(Material.GOLDEN_SHOVEL)){
                event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.create_residence.take_tool"));
            } else if(item.getType().equals(Material.STICK)){
                preResidence.removeIf(preres -> preres.getPlayer().equals(event.getPlayer()));
                event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.check_residence.take_tool"));
            } else {
                preResidence.removeIf(preres -> preres.getPlayer().equals(event.getPlayer()));
            }
        } else {
            preResidence.removeIf(preres -> preres.getPlayer().equals(event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(item != null && event.getClickedBlock() != null){
                if(item.getType().equals(Material.GOLDEN_SHOVEL)){
                    event.setCancelled(true);
                    boolean hasPreResidence = false;
                    PreResidence preRes = null;
                    for(PreResidence preres : preResidence){
                        if(preres.getPlayer().equals(event.getPlayer())){
                            hasPreResidence = true;
                            preRes = preres;
                        }
                    }
                    if(hasPreResidence){
                        if(!preRes.hasLocation1()){
                            // 设置Location1
                            preRes.setLocation1(event.getClickedBlock().getLocation());
                            event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.create_residence.set_location1"));
                        } else {
                            // 设置Location2 & 创建 & 销毁数据
                            preRes.setLocation2(event.getClickedBlock().getLocation());
                            if(preRes.canCreate()){
                                preRes.create();
                                event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.create_residence.set_location2"));
                                preResidence.remove(preRes);
                            } else {
                                event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.create_residence.has_residence"));
                            }
                        }
                    } else {
                        // 没有已设置领地, 设置Location1
                        preRes = new PreResidence(event.getPlayer());
                        preRes.setLocation1(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.create_residence.set_location1"));
                        preResidence.add(preRes);
                    }
                } else if(item.getType().equals(Material.STICK)){
                    Residence residence = Residence.getResidenceByLocation(event.getClickedBlock().getLocation());
                    if(residence == null){
                        event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.check_residence.no_residence"));
                    } else {
                        event.getPlayer().sendMessage(getPrefixW() + message.getString("tool.check_residence.has_residence"));
                    }
                }
            }
        }
    }
}
