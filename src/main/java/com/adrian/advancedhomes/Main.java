package com.adrian.advancedhomes.utils;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.HashMap;
import java.util.Map;

public class HomeManager {

    private static HashMap<String, Map<String, Location>> homes = new HashMap<>();

    public static boolean homeExists(Player player, String name) {
        return homes.containsKey(player.getUniqueId().toString()) &&
               homes.get(player.getUniqueId().toString()).containsKey(name);
    }

    public static void setHome(Player player, String name, Location loc) {
        homes.putIfAbsent(player.getUniqueId().toString(), new HashMap<>());
        if (loc == null) {
            homes.get(player.getUniqueId().toString()).remove(name);
        } else {
            homes.get(player.getUniqueId().toString()).put(name, loc);
        }
    }

    public static Location getHome(Player player, String name) {
        if (!homeExists(player, name)) return null;
        return homes.get(player.getUniqueId().toString()).get(name);
    }

    public static boolean canSetHome(Player player) {
        return homes.getOrDefault(player.getUniqueId().toString(), new HashMap<>()).size() < 3;
    }

    public static Map<String, Location> getAllHomes(Player player) {
        return homes.getOrDefault(player.getUniqueId().toString(), new HashMap<>());
    }
}
