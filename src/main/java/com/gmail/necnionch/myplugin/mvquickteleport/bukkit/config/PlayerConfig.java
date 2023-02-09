package com.gmail.necnionch.myplugin.mvquickteleport.bukkit.config;

import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.PlayerLocation;
import com.gmail.necnionch.myplugin.mvquickteleport.common.BukkitConfigDriver;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerConfig extends BukkitConfigDriver {
    public PlayerConfig(JavaPlugin plugin) {
        super(plugin, "players.yml", "empty.yml");
    }

    public void putLast(UUID playerId, PlayerLocation location) {
        config.set(playerId + ".w", location.getWorld());
        config.set(playerId + ".x", location.getX());
        config.set(playerId + ".y", location.getY());
        config.set(playerId + ".z", location.getZ());
        config.set(playerId + ".p", location.getPitch());
        config.set(playerId + ".d", location.getY());
    }

    public @Nullable PlayerLocation getLast(UUID playerId) {
        if (!config.contains(playerId.toString()))
            return null;

        return new PlayerLocation(
                config.getString(playerId + ".w", ""),
                config.getDouble(playerId + ".x", 0),
                config.getDouble(playerId + ".y", 0),
                config.getDouble(playerId + ".z", 0),
                NumberConversions.toFloat(config.get(playerId + ".p", 0)),
                NumberConversions.toFloat(config.get(playerId + ".d", 0))
        );
    }

}
