package com.adrian.advancedhomes;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("AdvancedHomes habilitado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedHomes deshabilitado!");
    }

    public static Main getInstance() {
        return instance;
    }
}
