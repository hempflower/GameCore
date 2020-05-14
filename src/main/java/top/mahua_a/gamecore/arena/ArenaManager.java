package top.mahua_a.gamecore.arena;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import top.mahua_a.gamecore.GameCore;


import java.util.*;

public class ArenaManager {
    private static Map<Plugin, Set<Arena>> arenas = new HashMap<>();

    /**
     *
     * @param plugin
     * @param world source world
     * @return arena instance
     */
    public static Arena createNewArena(Plugin plugin, World world, Location exitLocation) {
        MVWorldManager mvWorldManager = GameCore.getMultiverseCore().getMVWorldManager();
        String newWorldName = "game-core-arena-" + System.currentTimeMillis();
        boolean success = GameCore.getMultiverseCore().getMVWorldManager().cloneWorld(world.getName(), newWorldName);
        if (!success) {
            Bukkit.getLogger().warning("Unable to create arena world.");
            return null;
        }
        Arena arena = new Arena(mvWorldManager.getMVWorld(newWorldName).getCBWorld(), exitLocation);
        arenas.computeIfAbsent(plugin, k -> new HashSet<>()).add(arena);
        Bukkit.getLogger().info("Create arena successfully");
        return arena;
    }

    public static void removeOldArenaWorld() {
        for (MultiverseWorld multiverseWorld : GameCore.getMultiverseCore().getMVWorldManager().getMVWorlds()) {
            if (multiverseWorld.getName().startsWith("game-core-arena-")) {
                Bukkit.getLogger().info("Deleting world:" + multiverseWorld.getName());
                GameCore.getMultiverseCore().getMVWorldManager().deleteWorld(multiverseWorld.getName());
            }
        }
    }

    public static void removeArena(Plugin plugin, Arena arena) {
        arena.destroy();
        getArenas(plugin).remove(arena);
    }

    public static void clearArena(Plugin plugin) {
        Iterator<Arena> arenaIterator = getArenas(plugin).iterator();
        while (arenaIterator.hasNext()) {
            removeArena(plugin, arenaIterator.next());
        }
    }

    public static Set<Arena> getArenas(Plugin plugin) {
        return arenas.computeIfAbsent(plugin, k -> new HashSet<>());
    }

    /**
     * DO NOT USE THIS METHOD!!
     */
    public static void clearAllArenas() {
        Iterator<Map.Entry<Plugin, Set<Arena>>> iterator = arenas.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Plugin, Set<Arena>> setEntry = iterator.next();
            clearArena(setEntry.getKey());
            arenas.remove(setEntry.getKey());
        }
    }

}
