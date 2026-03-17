package com.adrian.advancedhomes.utils;

import org.bukkit.entity.Player;
import java.util.HashMap;

public class CooldownManager {

    private static HashMap<String, Long> cooldowns = new HashMap<>();
    private static final int COOLDOWN_SECONDS = 5;

    public static boolean canTeleport(Player player) {
        return !cooldowns.containsKey(player.getUniqueId().toString()) ||
               (System.currentTimeMillis() - cooldowns.get(player.getUniqueId().toString())) / 1000 >= COOLDOWN_SECONDS;
    }

    public static void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId().toString(), System.currentTimeMillis());
    }

    public static int getRemaining(Player player) {
        if (!cooldowns.containsKey(player.getUniqueId().toString())) return 0;
        long elapsed = (System.currentTimeMillis() - cooldowns.get(player.getUniqueId().toString())) / 1000;
        return (int)Math.max(0, COOLDOWN_SECONDS - elapsed);
    }

    public static void resetCooldown(Player player) {
        cooldowns.remove(player.getUniqueId().toString());
    }
}
