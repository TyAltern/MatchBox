package me.tyalternative.matchbox.mechanics.glowing;

import fr.skytasul.glowingentities.GlowingEntities;
import me.tyalternative.matchbox.MatchBox;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class GlowingManager {

    private final GlowingEntities glowingEntities = new GlowingEntities(MatchBox.getInstance());
    private final Map<UUID, List<UUID>> glowingPlayerMap = new HashMap<>();

    public void setPlayerGlow(Player viewer, Player target, ChatColor color) {

        try {
            glowingEntities.setGlowing(target, viewer, color);
            if (!glowingPlayerMap.containsKey(viewer.getUniqueId()) || glowingPlayerMap.get(viewer.getUniqueId()) == null) {
                glowingPlayerMap.put(viewer.getUniqueId(), new ArrayList<>());
            }
            glowingPlayerMap.get(viewer.getUniqueId()).add(target.getUniqueId());

        } catch (ReflectiveOperationException e) {
            MatchBox.getInstance().getLogger().warning("§cImpossibilité d'appliquer une effet de surbrillance sur le joueur " + target.getName());
        }
    }

    public void setPlayerGlow(UUID viewer, UUID target, ChatColor color) {
        setPlayerGlow(Bukkit.getPlayer(viewer), Bukkit.getPlayer(target), color);
    }

    public void removePlayerGlow(Player viewer, Player target) {

        try {
            glowingEntities.unsetGlowing(target, viewer);
            if (!glowingPlayerMap.containsKey(viewer.getUniqueId()) || glowingPlayerMap.get(viewer.getUniqueId()) == null) {
                glowingPlayerMap.put(viewer.getUniqueId(), new ArrayList<>());
            }
            glowingPlayerMap.get(viewer.getUniqueId()).remove(target.getUniqueId());

        } catch (ReflectiveOperationException e) {
            MatchBox.getInstance().getLogger().warning("§cImpossibilité de retirer l'effet de surbrillance du joueur " + target.getName());
        }
    }

    public void removePlayerGlow(UUID viewer, UUID target) {
        removePlayerGlow(Bukkit.getPlayer(viewer), Bukkit.getPlayer(target));
    }

    public boolean isPlayerGlowing(Player viewer, Player target) {
        if (glowingPlayerMap.get(viewer.getUniqueId()) == null) return false;
        return glowingPlayerMap.get(viewer.getUniqueId()).contains(target.getUniqueId());
    }

    public List<Player> getGlowingViewers(Player target) {
        List<Player> viewers = new ArrayList<>();
        for (Map.Entry<UUID, List<UUID>> entry : glowingPlayerMap.entrySet()) {
            if (entry.getValue().contains(target.getUniqueId())) {
                viewers.add(Bukkit.getPlayer(entry.getKey()));
            }
        }
        return viewers;
    }

    public List<Player> getGlowingTargets(Player viewer) {
        List<Player> targets = new ArrayList<>();
        for (UUID target : glowingPlayerMap.get(viewer.getUniqueId())) {
            targets.add(Bukkit.getPlayer(target));
        }
        return targets;
    }
}
