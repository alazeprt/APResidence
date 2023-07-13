package com.alazeprt;

import com.alazeprt.bstats.Metrics;
import com.alazeprt.command.RegisterCommmand;
import com.alazeprt.event.RegisterEvent;
import com.alazeprt.util.PreResidence;
import com.alazeprt.util.SaveScheduled;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class APResidence extends JavaPlugin {
    public static List<PreResidence> preResidence = new ArrayList<>();
    public static FileConfiguration data;
    public static FileConfiguration message;
    public static FileConfiguration config;
    private static final SaveScheduled scheduled = new SaveScheduled();
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
        if(!new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "config.yml").exists()){
            saveResource("config.yml", false);
        }
        if(!new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml").exists()){
            saveResource("data.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "config.yml"));
        data = YamlConfiguration.loadConfiguration(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml"));
        String lang = config.getString("lang");
        File file = new File(getDataFolder().getPath() + "/lang", lang + ".yml");
        if(!file.exists()){
            if(lang.equals("en_US")){
                saveResource("lang/en_US.yml", false);
            } else if(lang.equals("zh_CN")){
                saveResource("lang/zh_CN.yml", false);
            } else {
                config.set("lang", "en_US");
                saveResource("lang/en_US.yml", false);
            }
        }
        message = YamlConfiguration.loadConfiguration(file);
        if(config.getString("SaveMode").equals("SCHEDULED")){
            getLogger().info("正在启用定时保存策略...");
            scheduled.run();
        }
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
        if(config.getString("SaveMode").equals("SCHEDULED")){
            if(SaveScheduled.saving){
                getLogger().info("检测到领地数据正在保存, 将在2秒后再关闭服务器...");
                try {
                    TimeUnit.SECONDS.sleep(config.getInt("SavingTime"));
                } catch (InterruptedException e) {
                    getLogger().warning("无法启用2秒后关闭进程, 将直接终止保存!");
                    scheduled.stop();
                    return;
                }
                scheduled.stop();
            } else {
                scheduled.stop();
            }
        }
        if(config.getString("SaveMode").equals("ON_DISABLE")){
            getLogger().info("正在保存领地数据中...");
            try {
                data.save(new File(getDataFolder(), "data.yml"));
            } catch (IOException e) {
                getLogger().info("保存失败! 原因: ");
                e.getMessage();
            } finally {
                APResidence.getProvidingPlugin(APResidence.class).getLogger().info("保存成功!");
            }
        }
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
        return message.getString("commands.help.top").replace("&", "§");
    }

    public static void reload(){
        // Disable
        if(config.getString("SaveMode").equals("SCHEDULED")){
            if(!scheduled.saving){
                APResidence.getProvidingPlugin(APResidence.class).getLogger().warning("检测到领地数据正在保存, 将在5秒后再关闭服务器...");
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        scheduled.stop();
                    }
                };
                runnable.runTaskLater(APResidence.getProvidingPlugin(APResidence.class), 100);
            } else {
                scheduled.stop();
            }
        }
        if(config.getString("SaveMode").equals("ON_DISABLE")){
            APResidence.getProvidingPlugin(APResidence.class).getLogger().info("正在保存领地数据中...");
            try {
                data.save(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Enable
        APResidence.getProvidingPlugin(APResidence.class).getLogger().info("正在初始化配置文件...");
        if(!new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "config.yml").exists()){
            APResidence.getProvidingPlugin(APResidence.class).saveResource("config.yml", false);
        }
        if(!new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml").exists()){
            APResidence.getProvidingPlugin(APResidence.class).saveResource("data.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "config.yml"));
        data = YamlConfiguration.loadConfiguration(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml"));
        String lang = config.getString("lang");
        File file = new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder().getPath() + "/lang", lang + ".yml");
        if(!file.exists()){
            if(lang.equals("en_US")){
                APResidence.getProvidingPlugin(APResidence.class).saveResource("lang/en_US.yml", false);
            } else if(lang.equals("zh_CN")){
                APResidence.getProvidingPlugin(APResidence.class).saveResource("lang/zh_CN.yml", false);
            } else {
                config.set("lang", "en_US");
                try {
                    config.save(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "config.yml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                file = new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder().getPath() + "/lang", "en_US.yml");
                APResidence.getProvidingPlugin(APResidence.class).saveResource("lang/en_US.yml", false);
            }
        }
        message = YamlConfiguration.loadConfiguration(file);
        if(config.getString("SaveMode").equals("SCHEDULED")){
            APResidence.getProvidingPlugin(APResidence.class).getLogger().info("正在启用定时保存策略...");
            scheduled.run();
        }
    }
}
