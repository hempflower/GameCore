package top.mahua_a.gamecore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import top.mahua_a.gamecore.util.SpectatorMode;

public class EntityListener implements Listener {
    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (!(event.getTarget() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getTarget();
        if (SpectatorMode.isSpectatorMode(player)) {
            event.setCancelled(true);
        }
    }
}
