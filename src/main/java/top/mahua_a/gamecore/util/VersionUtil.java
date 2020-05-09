package top.mahua_a.gamecore.util;

import org.bukkit.Bukkit;

import java.util.Set;

public class VersionUtil {
    private static int versionNum = 0;

    /**
     * Check minecraft version is legacy
     *
     * @return return true, if version older than 1.13.
     */
    public static boolean isLegacy() {
        if (versionNum == 0) {
            String[] bukkitVer = Bukkit.getBukkitVersion().split("-")[0].split("\\.");
            for (int i = 0; i < 2; i++) {
                versionNum += Integer.parseInt(bukkitVer[i]) * (i == 0 ? 100 : 1);
            }
        }
        return versionNum < 113;
    }
}
