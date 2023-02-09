package com.gmail.necnionch.myplugin.mvquickteleport.bukkit.config;

import com.gmail.necnionch.myplugin.mvquickteleport.common.BukkitConfigDriver;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends BukkitConfigDriver {
    public MainConfig(JavaPlugin plugin) {
        super(plugin);
    }

    public boolean isAutoLoadDisable() {
        return config.getBoolean("force-autoload-disable", true);
    }

    public boolean isKeepQuitWorld() {
        return config.getBoolean("keep-quit-world", true);
    }

}
