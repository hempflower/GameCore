package top.mahua_a.gamecore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.mahua_a.gamecore.arena.Arena;
import top.mahua_a.gamecore.arena.ArenaManager;

import java.util.Objects;

public final class GameCore extends JavaPlugin {
    private static MultiverseCore multiverseCore;

    private static GameCore instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore == null) {
            getLogger().warning("Unable to get mv-core. Disabling...");
            this.onDisable();
        }
        ArenaManager.removeOldArenaWorld();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Destroy all arena
        ArenaManager.clearAllArenas();
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Arena arena = ArenaManager.createNewArena(this,Objects.requireNonNull(getServer().getWorld("world")),((Player) sender).getLocation());
        return true;
    }

    public static GameCore getInstance() {
        return instance;
    }
}
