package top.mahua_a.gamecore.arena;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import top.mahua_a.gamecore.GameCore;

import java.util.HashSet;
import java.util.Set;

public class Arena {
    private World world;
    private Location exitLocation;

    private Set<Player> players = new HashSet<>();

    public Arena(World world,Location exitLocation) {
        this.world = world;
        this.exitLocation = exitLocation;
    }

    public void join(Player player) {
        players.add(player);
    }

    public void left(Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public boolean isInArena(Player player) {
        return players.contains(player);
    }

    /**
     * Plugin shouldn't call this!
     */
    protected void destroy() {
        //tp all player
        for(Player player : players){
            player.teleport(exitLocation);
        }
        players.clear();
        //Unload and remove arena world.
        MVWorldManager mvWorldManager = GameCore.getMultiverseCore().getMVWorldManager();
        mvWorldManager.deleteWorld(world.getName());
        Bukkit.getLogger().info("Deleted arena world.");
    }
    public World getWorld() {
        return this.world;
    }

}
