package top.mahua_a.gamecore.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import top.mahua_a.gamecore.game.GameManager;
import top.mahua_a.gamecore.util.SpectatorMode;

public class PlayerListener implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        GameManager.playerChat(event);
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        if (GameManager.isInGame(event.getPlayer())) {
            GameManager.playerQuit(event.getPlayer());
        }
        SpectatorMode.removeSpectatorMode(event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (SpectatorMode.isSpectatorMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHungry(FoodLevelChangeEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (SpectatorMode.isSpectatorMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getDamager();
        if (SpectatorMode.isSpectatorMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (SpectatorMode.isSpectatorMode(player)) {
            if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (SpectatorMode.isSpectatorMode(player)) {
            event.setCancelled(true);
        }
    }

}
