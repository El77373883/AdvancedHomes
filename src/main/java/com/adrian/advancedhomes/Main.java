package com.adrian.advancedhomes;

import org.bukkit.plugin.java.JavaPlugin;
import com.adrian.advancedhomes.commands.HomeCommand;
import com.adrian.advancedhomes.commands.SetHomeCommand;
import com.adrian.advancedhomes.gui.HomeGUI;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.adrian.advancedhomes.utils.HomeManager;
import com.adrian.advancedhomes.utils.CooldownManager;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getLogger().info("AdvancedHomes Premium habilitado!");

        // Registrar comandos
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());

        // Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvancedHomes Premium deshabilitado!");
    }

    public static Main getInstance() {
        return instance;
    }

    // Evento para manejar clicks en el GUI de homes
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Tus Homes")) {
            event.setCancelled(true); // cancelar movimiento
            if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) return;

            String homeName = event.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "");
            if (!HomeManager.homeExists(player, homeName)) return;

            // Verificar cooldown
            if (!CooldownManager.canTeleport(player)) {
                player.sendMessage("§cEspera " + CooldownManager.getRemaining(player) + "s antes de volver a teletransportarte!");
                return;
            }

            // Teletransportar
            player.teleport(HomeManager.getHome(player, homeName));
            CooldownManager.setCooldown(player);
            player.sendMessage(getConfig().getString("messages.home-teleport").replace("{home}", homeName));
            player.closeInventory();
        }
    }
}
