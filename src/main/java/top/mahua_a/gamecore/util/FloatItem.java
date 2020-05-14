package top.mahua_a.gamecore.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import top.mahua_a.gamecore.GameCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Useful tools
 */
public class FloatItem {
    private static Map<Plugin, List<ArmorStand>> floatItems = new HashMap<>();

    public static ArmorStand spawnFloatItem(Plugin plugin, ItemStack itemStack, Location location) {
        //脚手架
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setHelmet(itemStack);
        armorStand.setGravity(false);
        armorStand.setMarker(true);

        List<ArmorStand> armorStands = floatItems.computeIfAbsent(plugin, k -> new ArrayList<>());
        armorStands.add(armorStand);

        Location sameLocation = armorStand.getLocation();
        //旋转
        //https://gist.github.com/Kr1lle/87fc2d1aef26772aedd1044dad8e113b
        new BukkitRunnable() {
            private boolean floatLoop = true;
            private Location lastlocation = armorStand.getLocation();

            @Override
            public void run() {
                armorStand.teleport(lastlocation);
                if (!armorStand.isValid()) {
                    //防止其他插件自行kill掉盔甲架而导致的不同步
                    remove(plugin,armorStand);
                    this.cancel();
                    return;
                }
                if (!floatLoop) {
                    lastlocation.add(0, 0.01, 0);
                    lastlocation.setYaw((lastlocation.getYaw() + 7.5F));
                    if (armorStand.getLocation().getY() > (0.25 + sameLocation.getY()))
                        floatLoop = true;
                } else {
                    lastlocation.subtract(0, 0.01, 0);
                    lastlocation.setYaw((lastlocation.getYaw() - 7.5F));
                    if (armorStand.getLocation().getY() < (-0.25 + sameLocation.getY()))
                        floatLoop = false;
                }

            }
        }.runTaskTimerAsynchronously(GameCore.getInstance(), 0, 1);

        return armorStand;
    }

    public static List<ArmorStand> getFloatItems(Plugin plugin) {
        return floatItems.computeIfAbsent(plugin, k -> new ArrayList<>());
    }

    public static void removeAll(Plugin plugin) {
        List<ArmorStand> armorStands = floatItems.computeIfAbsent(plugin, k -> new ArrayList<>());
        armorStands.clear();
    }

    public static void remove(Plugin plugin, ArmorStand armorStand) {
        List<ArmorStand> armorStands = floatItems.computeIfAbsent(plugin, k -> new ArrayList<>());
        armorStands.remove(armorStand);
    }
}
