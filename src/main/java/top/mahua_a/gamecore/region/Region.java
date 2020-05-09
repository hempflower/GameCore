package top.mahua_a.gamecore.region;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

public interface Region {
    boolean isBlockAddedDuringGame(Location loc);
    void putOriginalBlock(Location loc);
    void addBuiltDuringGame(Location loc);
    void removeBlockBuiltDuringGame(Location loc);
    void regen();
    boolean isLiquid(Material material);
    boolean isChunkUsed(Chunk chunk);
}
