package com.gmail.necnionch.myplugin.mvquickteleport.bukkit.listener;

import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.MVQuickTeleportPlugin;
import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.PlayerLocation;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;

import java.util.logging.Logger;

public class PluginListener implements Listener {

    private final MVQuickTeleportPlugin plugin;

    public PluginListener(MVQuickTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    private Logger getLogger() {
        return plugin.getLogger();
    }

    @EventHandler
    public void onNewWorld(WorldInitEvent event) {
        if (plugin.getMainConfig().isAutoLoadDisable()) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                MultiverseWorld mvWorld = plugin.getWorldManager().getMVWorld(event.getWorld());
                if (mvWorld != null) {
                    mvWorld.setAutoLoad(false);
                    mvWorld.setKeepSpawnInMemory(false);
                    plugin.getWorldManager().saveWorldsConfig();
                }
            }, 0);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayerConfig().putLast(event.getPlayer().getUniqueId(), PlayerLocation.of(event.getPlayer().getLocation()));
        plugin.getPlayerConfig().save();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getMainConfig().isKeepQuitWorld())
            return;

        Player player = event.getPlayer();
        PlayerLocation lastLocation = plugin.getPlayerConfig().getLast(player.getUniqueId());

        if (lastLocation == null || !plugin.getWorldManager().getUnloadedWorlds().contains(lastLocation.getWorld()))
            return;

        Location original = player.getLocation();
        getLogger().info(String.format(
                "Keeping last location: %s : %s,%d,%d,%d to %s,%d,%d,%d", player.getName(),
                player.getWorld().getName(), original.getBlockX(), original.getBlockY(), original.getBlockZ(),
                lastLocation.getWorld(), (long) lastLocation.getX(), (long) lastLocation.getY(), (long) lastLocation.getZ()
        ));

        plugin.loadWorld(lastLocation.getWorld());
        MultiverseWorld mvWorld = plugin.getWorldManager().getMVWorld(lastLocation.getWorld());

        if (mvWorld == null) {
            getLogger().warning("Failed to load last world: " + lastLocation.getWorld());
            return;
        }

        Location fallback = lastLocation.toLocation(mvWorld.getCBWorld());
        player.teleport(fallback);
    }

}
