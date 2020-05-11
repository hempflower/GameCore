package top.mahua_a.gamecore.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import top.mahua_a.gamecore.GameCore;

/**
 * Useful tools
 */
public class Useful {
    public static Entity spawnFloatItem(ItemStack itemStack, Location location) {
        //脚手架
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setHelmet(itemStack);
        armorStand.setGravity(false);

        Location sameLocation = armorStand.getLocation();
        //旋转
        //https://gist.github.com/Kr1lle/87fc2d1aef26772aedd1044dad8e113b
        new BukkitRunnable() {
            private boolean floatLoop = true;
            private Location lastlocation = armorStand.getLocation();
            @Override
            public void run() {
                armorStand.teleport(lastlocation);
                if(!armorStand.isValid()){
                    this.cancel();
                    return;
                }
                if (!floatLoop) {
                    lastlocation.add(0, 0.01, 0);
                    lastlocation.setYaw((lastlocation.getYaw() + 7.5F));
                    if (armorStand.getLocation().getY() > (0.25 + sameLocation.getY()))
                        floatLoop = true;
                }
                else {
                    lastlocation.subtract(0, 0.01, 0);
                    lastlocation.setYaw((lastlocation.getYaw() - 7.5F));
                    if (armorStand.getLocation().getY() < (-0.25 + sameLocation.getY()))
                        floatLoop = false;
                }

            }
        }.runTaskTimerAsynchronously(GameCore.getInstance(),0,1);

        return armorStand;
    }
}
