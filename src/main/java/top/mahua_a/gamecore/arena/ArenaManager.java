package top.mahua_a.gamecore.arena;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import top.mahua_a.gamecore.GameCore;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ArenaManager {
    private static Set<Arena> arenas = new HashSet<>();

    /**
     * @param world source world
     * @return arena instance
     */
    public static Arena createNewArena(World world, Location exitLocation) {
        MVWorldManager mvWorldManager = GameCore.getMultiverseCore().getMVWorldManager();
        String newWorldName = "game-core-arena-" + System.currentTimeMillis();
        boolean success = GameCore.getMultiverseCore().getMVWorldManager().cloneWorld(world.getName(), newWorldName);
        if (!success) {
            Bukkit.getLogger().warning("Unable to create arena world.");
            return null;
        }
        Arena arena = new Arena(mvWorldManager.getMVWorld(newWorldName).getCBWorld(), exitLocation);
        arenas.add(arena);
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

    public static void removeArena(Arena arena) {
        arena.destroy();
        arenas.remove(arena);
    }

    public static void clearArena() {
        Iterator<Arena> arenaIterator = arenas.iterator();
        while (arenaIterator.hasNext()){
            removeArena(arenaIterator.next());
        }
    }

}
