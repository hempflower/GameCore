package top.mahua_a.gamecore.arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import top.mahua_a.gamecore.WorldPos;
import top.mahua_a.gamecore.util.Math;

import java.util.HashSet;
import java.util.Set;

public class Arena {
    private WorldPos pos0;
    private WorldPos pos1;
    private World world;
    private ARENA_TYPE type;

    private Set<Player> players = new HashSet<>();

    public Arena(WorldPos pos0, WorldPos pos1, World world, ARENA_TYPE type) {
        this.pos0 = pos0;
        this.pos1 = pos1;
        this.world = world;
        this.type = type;
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

    public boolean isArenaMember(Player player){
        return players.contains(player);
    }

    public boolean isInArena(Location location) {
        if (!world.equals(location.getWorld())) {
            return false;
        }
        //compare X
        if (!Math.between(pos0.getX(), pos1.getX(), location.getX())) {
            return false;
        }
        //compare Y
        if (!Math.between(pos0.getY(), pos1.getY(), location.getY())) {
            return false;
        }
        //compare Z
        return Math.between(pos0.getZ(), pos1.getZ(), location.getZ());
    }

    public void movie(PlayerMoveEvent event) {
        if (!isInArena(event.getTo())) {
            event.setCancelled(true);
        }
    }

    public void breakBlock(BlockBreakEvent event) {
        if(!isInArena(event.getBlock().getLocation())){
            return;
        }
        if(type == ARENA_TYPE.FREE){
            return;
        }
    }

    public void placeBlock() {

    }


}
