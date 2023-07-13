package com.alazeprt.event;

import com.alazeprt.APResidence;
import com.alazeprt.util.ResidenceGroup;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

import static com.alazeprt.APResidence.*;

public class InitializeEventHandler implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        data.set("players." + event.getPlayer() + ".block", groups.getInt(new ResidenceGroup(event.getPlayer()).getGroup() + ".Residence.initial"));
        if(config.getString("SaveMode").equals("REAL_TIME")){
            try {
                data.save(new File(APResidence.getProvidingPlugin(APResidence.class).getDataFolder(), "data.yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
