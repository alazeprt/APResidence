package com.alazeprt;

import com.alazeprt.bstats.Metrics;
import com.alazeprt.command.RegisterCommmand;
import com.alazeprt.event.RegisterEvent;
import com.alazeprt.util.PreResidence;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class APResidence extends JavaPlugin {
    public static List<PreResidence> preResidence = new ArrayList<>();
    public static FileConfiguration data;
    public static FileConfiguration message;
    public static FileConfiguration config;
    public static Economy econ;
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
    @Override
    public void onEnable() {
        getLogger().info("正在启用APResidence插件...");
        getLogger().info("正在初始化经济系统...");
        if(!setupEconomy()){
            getLogger().warning("Vault 未初始化! 将无法使用经济功能!");
        }
        getLogger().info("正在初始化配置文件...");
        if(!new File(getDataFolder(), "config.yml").exists()){
            saveResource("config.yml", false);
        }
        if(!new File(getDataFolder(), "data.yml").exists()){
            saveResource("data.yml", false);
        }
        if(!new File(getDataFolder(), "message.yml").exists()){
            saveResource("message.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
        data = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml"));
        message = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "message.yml"));
        getLogger().info("正在注册指令...");
        RegisterCommmand.register(this);
        getLogger().info("正在注册监听器...");
        RegisterEvent.register(this);
        if(config.getBoolean("bstats")){
            getLogger().info("正在同步bstats...");
            int pluginid = 18969;
            Metrics metrics = new Metrics(this, pluginid);
        }
        getLogger().info("APResidence 插件加载成功!");
    }

    @Override
    public void onDisable() {
        getLogger().info("APResidence 插件卸载成功!");
    }

    @Deprecated
    public static String getPrefix(){
        return "§x§0§2§4§3§f§bA§x§0§2§5§6§e§eP§x§0§2§6§9§e§1R§x§0§1§7§b§d§4e§x§0§1§8§e§c§7s§x§0§1§a§1§b§bi§x§0§1§b§4§a§ed§x§0§1§c§7§a§1e§x§0§0§d§9§9§4n§x§0§0§e§c§8§7c§x§0§0§f§f§7§ae";
    }

    public static String getPrefixW(){
        return "§x§0§2§4§3§f§b[§x§0§2§5§3§f§0A§x§0§2§6§2§e§6P§x§0§2§7§2§d§bR§x§0§1§8§2§d§0e§x§0§1§9§1§c§5s§x§0§1§a§1§b§bi§x§0§1§b§1§b§0d§x§0§1§c§0§a§5e§x§0§1§d§0§9§an§x§0§0§e§0§9§0c§x§0§0§e§f§8§5e§x§0§0§f§f§7§a] ";
    }

    public static String getPrefixH(){
        return "§x§0§2§4§3§f§bA§x§0§2§5§0§f§2P§x§0§2§5§e§e§9R§x§0§2§6§b§d§fe§x§0§1§7§9§d§6s§x§0§1§8§6§c§di§x§0§1§9§4§c§4d§x§0§1§a§1§b§be§x§0§1§a§e§b§1n§x§0§1§b§c§a§8c§x§0§1§c§9§9§fe §x§0§0§d§7§9§6帮§x§0§0§e§4§8§c助§x§0§0§f§2§8§3文§x§0§0§f§f§7§a档";
    }
}
