package top.mahua_a.gamecore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.mahua_a.gamecore.arena.Arena;
import top.mahua_a.gamecore.arena.ArenaManager;
import top.mahua_a.gamecore.game.Game;
import top.mahua_a.gamecore.game.GameManager;
import top.mahua_a.gamecore.listener.EntityListener;
import top.mahua_a.gamecore.listener.PlayerListener;
import top.mahua_a.gamecore.util.SpectatorMode;

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
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Destroy all arena
        ArenaManager.clearAllArenas();
        SpectatorMode.removeAll();
    }

    public static MultiverseCore getMultiverseCore() {
        return multiverseCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length == 0) {
            return true;
        }
        if (args[0].equalsIgnoreCase("arena")) {
            Arena arena = ArenaManager.createNewArena(this, Objects.requireNonNull(getServer().getWorld("world")), ((Player) sender).getLocation());

            Game game = GameManager.createGame(this, "测试游戏", arena);
            game.join((Player) sender);
            //传送到竞技场！
            //之所以不自动传送，是为了让插件更灵活的处理
            ((Player) sender).teleport(game.getArena().getWorld().getSpawnLocation());
        }

        if (args[0].equalsIgnoreCase("sp")) {
            if (SpectatorMode.isSpectatorMode((Player) sender)) {
                SpectatorMode.removeSpectatorMode((Player) sender);
            } else {
                SpectatorMode.setSpectatorMode((Player) sender);
            }

        }

        return true;
    }

    public static GameCore getInstance() {
        return instance;
    }
}
