package com.adrian.advancedhomes;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("AdvancedHomes habilitado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedHomes deshabilitado!");
    }
}
