package com.adrian.advancedhomes.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import com.adrian.advancedhomes.gui.UserHomeGUI;
import com.adrian.advancedhomes.utils.HomeManager;
import com.adrian.advancedhomes.utils.CooldownManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Particle;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import java.util.HashMap;
import java.util.UUID;

public class AHomeCommand implements CommandExecutor, Listener {

    private static final HashMap<UUID, Boolean> awaitingName = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        // Abrir menú si no hay args
        if (args.length == 0) {
            UserHomeGUI.open(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                if (!HomeManager.canSetHome(player)) {
                    player.sendMessage("§cHas alcanzado tu límite de homes!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    return true;
                }
                awaitingName.put(player.getUniqueId(), true);
                player.sendMessage("§aEscribe el nombre de tu home en el chat...");
                break;

            case "remove":
                if (args.length < 2) return false;
                String homeRemove = args[1];
                if (!HomeManager.homeExists(player, homeRemove)) {
                    player.sendMessage("§cEse home no existe!");
                    return true;
                }
                HomeManager.setHome(player, homeRemove, null);
                player.sendMessage("§aHome '" + homeRemove + "' eliminado!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                player.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20, 0.5, 1, 0.5);
                break;

            case "tp":
                if (args.length < 2) return false;
                String homeTp = args[1];
                if (!HomeManager.homeExists(player, homeTp)) {
                    player.sendMessage("§cEse home no existe!");
                    return true;
                }
                if (!CooldownManager.canTeleport(player)) {
                    player.sendMessage("§cEspera " + CooldownManager.getRemaining(player) + "s antes de teletransportarte!");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    return true;
                }
                Location origen = player.getLocation();
                Location destino = HomeManager.getHome(player, homeTp);
                player.getWorld().spawnParticle(Particle.PORTAL, origen, 30, 0.5, 1, 0.5);
                player.playSound(origen, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                player.teleport(destino);
                player.getWorld().spawnParticle(Particle.PORTAL, destino, 30, 0.5, 1, 0.5);
                player.playSound(destino, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                CooldownManager.setCooldown(player);
                player.sendMessage("§aTeletransportado a '" + homeTp + "'!");
                break;

            case "info":
                if (args.length < 2) return false;
                String homeInfo = args[1];
                if (!HomeManager.homeExists(player, homeInfo)) {
                    player.sendMessage("§cEse home no existe!");
                    return true;
                }
                Location loc = HomeManager.getHome(player, homeInfo);
                player.sendMessage("§aInformación de tu home '" + homeInfo + "':");
                player.sendMessage("§7Mundo: " + loc.getWorld().getName());
                player.sendMessage("§7Coordenadas: X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ());
                break;

            default:
                player.sendMessage("§cSubcomando desconocido!");
                break;
        }

        return true;
    }

    @EventHandler
    public void onChatName(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!awaitingName.containsKey(player.getUniqueId())) return;

        event.setCancelled(true);
        String homeName = event.getMessage();

        HomeManager.setHome(player, homeName, player.getLocation());
        player.sendMessage("§aHome '" + homeName + "' guardado correctamente!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        player.spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 20, 0.5, 1, 0.5);

        awaitingName.remove(player.getUniqueId());
    }
}
