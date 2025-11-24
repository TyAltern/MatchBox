package me.tyalternative.matchbox.player;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.role.RoleType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Gestionnaire centralisé des joueurs
 */
public class PlayerManager {

    private final MatchBox plugin;
    private final Map<UUID, PlayerData> players;

    public PlayerManager(MatchBox plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }


    // ========== GESTION DES DONNÉES ==========

    public PlayerData getOrCreate(Player player) {
        return players.computeIfAbsent(player.getUniqueId(), PlayerData::new);
    }

    public PlayerData get(Player player) {
        return players.get(player.getUniqueId());
    }

    public PlayerData get(UUID uuid) {
        return players.get(uuid);
    }

    public boolean has(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public void remove(Player player) {
        players.remove(player.getUniqueId());
    }


    // ========== COLLECTIONS ==========

    public Collection<PlayerData> getAll() {
        return new ArrayList<>(players.values());
    }

    public List<PlayerData> getByState(PlayerState state) {
        return players.values().stream()
                .filter(data -> data.getState() == state)
                .collect(Collectors.toList());
    }

    public List<PlayerData> getAlive() {
        return players.values().stream()
                .filter(data -> data.isAlive())
                .collect(Collectors.toList());
    }

    public List<Player> getAlivePlayer() {
        return getAlive().stream()
                .map(PlayerData::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<PlayerData> getByType(RoleType type) {
        return players.values().stream()
                .filter(data -> data.hasRole() && data.getRole().getTeam() == type)
                .collect(Collectors.toList());
    }

    public List<PlayerData> getDead() {
        return getByState(PlayerState.DEAD);
    }


    // ========== STATISTIQUES ==========

    public int getAliveCount() {
        return (int) players.values().stream()
                .filter(PlayerData::isAlive)
                .count();
    }

    public int getTotalCount() {
        return players.size();
    }


    // ========== RÉINITIALISATION ==========

    public void resetAllForNewRound() {
        players.values().forEach(PlayerData::resetForNewRound);
    }

    public void resetAll() {
        players.values().forEach(PlayerData::reset);
    }

    public void clear() {
        players.clear();
    }
}
