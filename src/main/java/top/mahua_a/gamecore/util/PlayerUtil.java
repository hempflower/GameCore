package top.mahua_a.gamecore.util;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import javax.swing.text.html.HTML;


public class PlayerUtil {
    public static void cleanUpPlayer(Player player) {
        player.getInventory().clear();
        for(PotionEffect effect : player.getActivePotionEffects()){
            player.removePotionEffect(effect.getType());
        }
        player.setDisplayName(null);
        player.setPlayerListName(null);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
    }
}
