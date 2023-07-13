package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

import static com.alazeprt.APResidence.config;

public class UpdateGroupScheduled extends Thread {
    @Override
    public void run() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                for(Player player : players){
                    ResidenceGroup group = new ResidenceGroup(player);
                    group.updateGroup();
                }
            }
        };
        runnable.runTaskTimer(APResidence.getProvidingPlugin(APResidence.class), config.getLong("UpdateGroup") * 20, 0);
    }
}
