package me.tyalternative.matchbox.mechanics.sign;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SignManager {

    private final GameManager gameManager;
    private final List<Location> placedSigns;

    public SignManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.placedSigns = new ArrayList<>();
    }

    public void add(Location location) {
        placedSigns.add(location.clone());
    }

    public void remove(Location location) {
        placedSigns.remove(location);
    }

    public boolean isPlaced(Location location) {
        return placedSigns.stream().anyMatch(loc -> LocationUtil.isSameBlock(loc, location));
    }

    public void clearAll() {
        for (Location location : new ArrayList<>(placedSigns)) {
            if (location.getBlock().getType().toString().contains("SIGN")) {
                location.getBlock().setType(Material.AIR);
            }
        }
        placedSigns.clear();
    }

    public List<Location> getAll() {
        return new ArrayList<>(placedSigns);
    }
}
