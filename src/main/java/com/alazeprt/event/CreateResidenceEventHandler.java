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

public class CreateResidenceEventHandler implements Listener {
    @EventHandler
    public void onPlayerTake(PlayerItemHeldEvent event){
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null){
            if(item.getType().equals(Material.GOLDEN_SHOVEL)){
                event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "你已切换到领地工具, 你可以通过右键两个点创建一块长方形领地!");
            } else if(item.getType().equals(Material.STICK)){
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
                    for(PreResidence preres : preResidence){
                        if(preres.getPlayer().equals(event.getPlayer())){
                            if(event.getClickedBlock() != null){
                                if(preres.hasLocation1()){
                                    preres.setLocation2(event.getClickedBlock().getLocation());
                                    if(preres.canCreate()){
                                        preres.create();
                                        event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "领地创建成功!");
                                        preResidence.remove(preres);
                                    } else {
                                        event.getPlayer().sendMessage(getPrefixW() + ChatColor.RED + "领地创建失败! 请尝试重新创建或联系管理员!");
                                        preResidence.remove(preres);
                                    }
                                } else {
                                    preres.setLocation1(event.getClickedBlock().getLocation());
                                    event.getPlayer().sendMessage(getPrefixW() + ChatColor.GREEN + "你已成功设置第一个点! 接着请继续使用右键设置第二个点!");
                                }
                            }
                        }
                    }
                } else if(item.getType().equals(Material.STICK)){
                    Residence residence = Residence.getResidenceByLocation(event.getPlayer().getLocation());
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
