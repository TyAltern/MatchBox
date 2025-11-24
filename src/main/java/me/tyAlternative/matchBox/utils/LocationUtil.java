package me.tyalternative.matchbox.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

    /**
     * Vérifie si deux locations sont identiques (bloc)
     */
    public static boolean isSameBlock(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return false;
        if (!loc1.getWorld().equals(loc2.getWorld())) return false;

        return loc1.getBlockX() == loc2.getBlockX() &&
                loc1.getBlockY() == loc2.getBlockY() &&
                loc1.getBlockZ() == loc2.getBlockZ();
    }

    /**
     * Distance 2D (ignore Y)
     */
    public static double distance2D(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null) return Double.MAX_VALUE;
        if (!loc1.getWorld().equals(loc2.getWorld())) return Double.MAX_VALUE;

        double dx = loc1.getX() - loc2.getX();
        double dz = loc1.getZ() - loc2.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * Parse une location depuis string "x,y,z"
     */
    public static Location parseLocation(World world, String str) {
        try {
            String[] parts = str.replace(" ", "").split(",");
            if (parts.length != 3) return null;

            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);

            return new Location(world, x, y, z);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Convertit une location en string
     */
    public static String toString(Location loc) {
        if (loc == null) return "null";
        return String.format("%.1f, %.1f, %.1f", loc.getX(), loc.getY(), loc.getZ());
    }

}
