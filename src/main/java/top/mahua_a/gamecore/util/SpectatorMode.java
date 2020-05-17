package top.mahua_a.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import top.mahua_a.gamecore.PlayerStorage;

import java.util.HashMap;
import java.util.Iterator;

public class SpectatorMode {
    private static HashMap<Player, PlayerStorage> spectators = new HashMap<>();

    public static void setSpectatorMode(Player player) {
        spectators.put(player, new PlayerStorage(player));
        //clean up
        PlayerUtil.cleanUpPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        //隐身
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 1, true, false));
        //速度
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2, true, false));
        player.setAllowFlight(true);
        if (!player.isOnGround()) {
            player.setFlying(true);
        }
        player.setCollidable(false);
        player.setCanPickupItems(false);

    }

    public static void removeSpectatorMode(Player player) {
        PlayerStorage playerStorage = spectators.get(player);
        spectators.remove(player);
        if (playerStorage != null) {
            playerStorage.reset();
        }

        player.setCollidable(true);
        player.setCanPickupItems(true);
    }

    public static boolean isSpectatorMode(Player player) {
        return spectators.containsKey(player);
    }

    public static void removeAll() {
        for (Player player : spectators.keySet()) {
            removeSpectatorMode(player);
        }
    }

}
