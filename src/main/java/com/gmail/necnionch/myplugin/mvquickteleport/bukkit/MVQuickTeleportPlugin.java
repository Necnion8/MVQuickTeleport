package com.gmail.necnionch.myplugin.mvquickteleport.bukkit;

import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.command.PluginCommand;
import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.config.MainConfig;
import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.config.PlayerConfig;
import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.listener.PluginListener;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiversePortals.MultiversePortals;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;

public final class MVQuickTeleportPlugin extends JavaPlugin {

    private MVWorldManager mv;
    private final MainConfig mainConfig = new MainConfig(this);
    private final PlayerConfig playerConfig = new PlayerConfig(this);
    private PortalActivate portalActivate;

    @Override
    public void onEnable() {
        mainConfig.load();
        playerConfig.load();

        MultiverseCore mv = JavaPlugin.getPlugin(MultiverseCore.class);

        this.mv = mv.getMVWorldManager();

        Optional.ofNullable(getCommand("mvquickteleport")).ifPresent(cmd ->
                cmd.setExecutor(new PluginCommand(this)));

        getServer().getPluginManager().registerEvents(new PluginListener(this), this);

        Plugin tmp = getServer().getPluginManager().getPlugin("Multiverse-Portals");
        if (tmp instanceof MultiversePortals && tmp.isEnabled()) {
            portalActivate = new PortalActivate();
        }
    }


    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public MVWorldManager getWorldManager() {
        return mv;
    }

    public void reloadPortals() {
        if (portalActivate != null)
            portalActivate.reloadPortals();
    }

    public boolean loadWorld(String name) {
        if (mv.loadWorld(name)) {
            reloadPortals();
            return true;
        }
        return false;
    }


    public class PortalActivate {
        private final MultiversePortals mvp = JavaPlugin.getPlugin(MultiversePortals.class);
        private Method reloadPortalMethod;

        public PortalActivate() {
            try {
                reloadPortalMethod = MultiversePortals.class.getDeclaredMethod("loadPortals");
                reloadPortalMethod.setAccessible(true);
            } catch (Throwable e) {
                getLogger().warning("Failed to reflection to portals reload method: " + e.getMessage());
            }
        }

        public void reloadPortals() {
            if (reloadPortalMethod == null)
                return;
            mvp.getPortalManager().removeAll(false);
            try {
                reloadPortalMethod.invoke(mvp);
            } catch (Throwable e) {
                getLogger().warning("Failed to reactive portals: " + e.getMessage());
                mvp.reloadConfigs(true);  // restore
            }
        }
    }

}
