package com.alazeprt.util;

import com.alazeprt.APResidence;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

import static com.alazeprt.APResidence.config;
import static com.alazeprt.APResidence.data;

public class SaveScheduled extends Thread {
    public static boolean saving = false;

    @Override
    public void run() {
        saving = true;
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                APResidence.getProvidingPlugin(APResidence.class).getLogger().info("正在保存领地数据...");
                try {
                    data.save(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml"));
                } catch (IOException e) {
                    APResidence.getProvidingPlugin(APResidence.class).getLogger().info("保存失败! 原因: ");
                    e.getMessage();
                } finally {
                    APResidence.getProvidingPlugin(APResidence.class).getLogger().info("保存成功!");
                }
            }
        };
        runnable.runTaskTimer(APResidence.getProvidingPlugin(APResidence.class), config.getLong("SaveInterval"), config.getLong("SaveInterval"));
        saving = false;
    }
}
