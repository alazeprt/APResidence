package com.alazeprt.event;

import org.bukkit.plugin.java.JavaPlugin;

public class RegisterEvent {
    public static void register(JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new AccessEventHandler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BuildEventHandler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ResidenceEventHandler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PvPEventHandler(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InitializeEventHandler(), plugin);
    }
}
