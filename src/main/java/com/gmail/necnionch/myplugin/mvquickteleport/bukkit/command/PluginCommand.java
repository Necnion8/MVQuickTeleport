package com.gmail.necnionch.myplugin.mvquickteleport.bukkit.command;

import com.gmail.necnionch.myplugin.mvquickteleport.bukkit.MVQuickTeleportPlugin;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiversePortals.MultiversePortals;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PluginCommand implements TabExecutor {

    private final MVQuickTeleportPlugin plugin;
    private final MVWorldManager mgr;

    public PluginCommand(MVQuickTeleportPlugin plugin) {
        this.plugin = plugin;
        this.mgr = plugin.getWorldManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String worldName = null;
        if (args.length == 1) {
            worldName = args[0];
        } else if (args.length >= 2) {
            worldName = args[1];
        }

        if (worldName != null && mgr.getUnloadedWorlds().contains(worldName)) {
            if (!sender.hasPermission("multiverse.core.load")) {
                sender.sendMessage(ChatColor.RED + "ワールドをロードする権限がありません");
                return true;
            }
            plugin.loadWorld(worldName);
            Command.broadcastCommandMessage(sender, "Loaded world '" + worldName + "'!");
        }

        plugin.getServer().dispatchCommand(sender, "mvtp " + String.join(" ", args));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        String worldName = (args.length == 1) ? args[0].toLowerCase(Locale.ROOT) : (args.length >= 2) ? args[1].toLowerCase(Locale.ROOT) : null;

        if (worldName != null) {
            List<String> names = mgr.getMVWorlds()
                    .stream()
                    .map(MultiverseWorld::getName)
                    .collect(Collectors.toList());
            names.addAll(mgr.getUnloadedWorlds());

            return names.stream()
                    .filter(n -> n.toLowerCase(Locale.ROOT).startsWith(worldName))
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
