package top.mahua_a.gamecore.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import top.mahua_a.gamecore.util.Useful;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event){
        if(event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR){
            Useful.spawnFloatItem(new ItemStack(Material.DIAMOND_BLOCK,1),event.getClickedBlock().getLocation().add(0,3,0));
        }
    }
}
