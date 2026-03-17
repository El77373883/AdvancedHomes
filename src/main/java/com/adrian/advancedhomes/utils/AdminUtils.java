package com.adrian.advancedhomes.utils;

import org.bukkit.entity.Player;

public class AdminUtils {

    public static void givePremium(Player player) {
        player.addAttachment(com.adrian.advancedhomes.Main.getInstance(), "advancedhomes.premium", true);
    }
}
