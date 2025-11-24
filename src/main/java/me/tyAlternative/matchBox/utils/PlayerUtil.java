package me.tyalternative.matchbox.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerUtil {
    /**
     * Obtient un joueur depuis son UUID
     */
    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Vérifie si un joueur est en ligne
     */
    public static boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null;
    }

    /**
     * Obtient une liste de joueurs depuis des UUIDs
     */
    public static List<Player> getPlayers(Collection<UUID> uuids) {
        List<Player> players = new ArrayList<>();
        for (UUID uuid : uuids) {
            Player player = getPlayer(uuid);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }

    /**
     * Trouve un joueur par nom (partiel)
     */
    public static Player findPlayer(String name) {
        String lowerName = name.toLowerCase();

        // Exact match
        Player exact = Bukkit.getPlayer(name);
        if (exact != null) return exact;

        // Partial match
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(lowerName)) {
                return player;
            }
        }

        return null;
    }
}
