package com.alazeprt.event;

import com.alazeprt.util.PreResidence;
import com.alazeprt.util.Residence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import static com.alazeprt.APResidence.getPrefixW;
import static com.alazeprt.APResidence.preResidence;

public class ResidenceEventHandler implements Listener {
    @EventHandler
    public void onPlayerTake(PlayerItemHeldEvent event){
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null){
            if(item.getType().equals(Material.GOLDEN_SHOVEL)){
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "你已切换到领地工具, 你可以通过右键两个点创建一块长方形领地!");
            } else if(item.getType().equals(Material.STICK)){
                preResidence.removeIf(preres -> preres.getPlayer().equals(event.getPlayer()));
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "你已切换到领地探测工具, 你可以通过右键某个方块来探测这里是否有领地!");
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
            if(item != null){
                if(item.getType().equals(Material.GOLDEN_SHOVEL)){
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
                            event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "111成功设置领地的第一个点! 接着请继续右键第二个点以创建领地, 或切换到其他物品上放弃创建");
                        } else {
                            // 设置Location2 & 创建 & 销毁数据
                            preRes.setLocation2(event.getClickedBlock().getLocation());;
                            if(preRes.canCreate()){
                                preRes.create();
                                event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "成功创建领地!");
                                preResidence.remove(preRes);
                            }
                        }
                    } else {
                        // 没有已设置领地, 设置Location1
                        preRes = new PreResidence(event.getPlayer());
                        preRes.setLocation1(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "222成功设置领地的第一个点! 接着请继续右键第二个点以创建领地, 或切换到其他物品上放弃创建");
                        preResidence.add(preRes);
                    }
                } else if(item.getType().equals(Material.STICK)){
                    Residence residence = Residence.getResidenceByLocation(event.getClickedBlock().getLocation());
                    if(residence == null){
                        event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "这里没有领地!你可以在这里创建领地!");
                    } else {
                        event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "这里已经有领地了! 你无法再在这里创建领地!");
                    }
                }
            }
        }
    }
}
