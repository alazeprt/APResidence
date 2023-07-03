package com.alazeprt.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

import static com.alazeprt.APResidence.config;
import static com.alazeprt.APResidence.data;

public class SaveScheduled extends Thread {
    public static boolean saving = false;
    private final JavaPlugin plugin;
    public SaveScheduled(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        saving = true;
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    data.save(new File(plugin.getDataFolder(), "data.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        runnable.runTaskTimer(plugin, config.getLong("SaveInterval"), config.getLong("SaveInterval"));
        saving = false;
    }
}
