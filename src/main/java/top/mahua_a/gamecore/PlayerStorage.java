package top.mahua_a.gamecore;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

/**
 * Storage player data
 */
public class PlayerStorage {

    private ItemStack[] items;
    private ItemStack[] armors;
    private GameMode gameMode;
    private float exp;
    private int level;
    private Collection<PotionEffect> effects;
    private String displayName;
    private String listName;
    private int foodlevel;

    private boolean isFlying;
    private boolean isAllowFlying;

    private Player player;


    public PlayerStorage(Player player) {
        this.player = player;

        this.items = player.getInventory().getContents();
        this.armors = player.getInventory().getArmorContents();
        this.gameMode = player.getGameMode();
        this.exp = player.getExp();
        this.level = player.getLevel();
        this.effects = player.getActivePotionEffects();
        this.displayName = player.getDisplayName();
        this.listName = player.getPlayerListName();
        this.foodlevel = player.getFoodLevel();
        this.isFlying = player.isFlying();
        this.isAllowFlying = player.getAllowFlight();
    }

    public void reset() {
        this.player.getInventory().setContents(items);
        this.player.getInventory().setArmorContents(armors);
        this.player.updateInventory();
        this.player.setGameMode(gameMode);
        this.player.setExp(exp);
        this.player.setLevel(level);
        for(PotionEffect effect : this.player.getActivePotionEffects()){
            this.player.removePotionEffect(effect.getType());
        }
        this.player.addPotionEffects(effects);
        this.player.setDisplayName(displayName);
        this.player.setPlayerListName(listName);
        this.player.setFoodLevel(foodlevel);

        this.player.setFlying(isFlying);
        this.player.setAllowFlight(isAllowFlying);
    }
}
