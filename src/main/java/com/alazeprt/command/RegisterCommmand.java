package com.alazeprt.command;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RegisterCommmand {
    public static void register(JavaPlugin plugin){
        Objects.requireNonNull(plugin.getCommand("apres")).setExecutor(new APCommand());
        Objects.requireNonNull(plugin.getCommand("apres")).setTabCompleter(new APCommand());
    }
}
