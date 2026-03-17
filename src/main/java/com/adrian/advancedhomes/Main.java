package com.adrian.advancedhomes;

import org.bukkit.plugin.java.JavaPlugin;
import com.adrian.advancedhomes.commands.AHomeCommand;
import com.adrian.advancedhomes.commands.AdvancedHomeCommand;
import org.bukkit.event.Listener;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getCommand("ahome").setExecutor(new AHomeCommand());
        getCommand("advancedhome").setExecutor(new AdvancedHomeCommand());

        getServer().getPluginManager().registerEvents(new AHomeCommand(), this);

        getLogger().info("AdvancedHomes PRO habilitado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedHomes PRO deshabilitado!");
    }

    public static Main getInstance() {
        return instance;
    }
}
