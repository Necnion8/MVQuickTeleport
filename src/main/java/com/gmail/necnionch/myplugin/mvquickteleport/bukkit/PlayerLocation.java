package com.gmail.necnionch.myplugin.mvquickteleport.bukkit;

import org.bukkit.Location;
import org.bukkit.World;

public class PlayerLocation {

    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;

    public PlayerLocation(String world, double x, double y, double z, float pitch, float yaw) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public static PlayerLocation of(Location location) {
        return new PlayerLocation(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

}
