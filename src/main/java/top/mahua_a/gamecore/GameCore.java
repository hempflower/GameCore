package top.mahua_a.gamecore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.mahua_a.gamecore.arena.ArenaManager;

import java.util.Objects;

public final class GameCore extends JavaPlugin {
    private static MultiverseCore multiverseCore;

    @Override
    public void onEnable() {
        // Plugin startup logic
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
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        ArenaManager.createNewArena(Objects.requireNonNull(getServer().getWorld("world")),((Player) sender).getLocation());
        return true;
    }
}
