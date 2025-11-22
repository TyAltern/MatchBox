package me.tyalternative.matchbox.player;

import me.tyalternative.matchbox.MatchBox;
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

    public PlayerData getOrCreateData(Player player) {
        return players.computeIfAbsent(player.getUniqueId(), PlayerData::new);
    }

    public PlayerData getData(Player player) {
        return players.get(player.getUniqueId());
    }

    public PlayerData getData(UUID uuid) {
        return players.get(uuid);
    }

    public boolean hasData(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public void removeData (Player player) {
        players.remove(player.getUniqueId());
    }


    // ========== COLLECTIONS ==========

    public Collection<PlayerData> getAllPlayers() {
        return new ArrayList<>(players.values());
    }

    public List<PlayerData> getPlayersInState(PlayerState state) {
        return players.values().stream()
                .filter(data -> data.getState() == state)
                .collect(Collectors.toList());
    }

    public List<PlayerData> getAlivePlayers() {
        return players.values().stream()
                .filter(data -> data.isAlive())
                .collect(Collectors.toList());
    }

    public List<Player> getAlivePlayerEntities() {
        return getAlivePlayers().stream()
                .map(PlayerData::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<PlayerData> getDeadPlayers() {
        return getPlayersInState(PlayerState.DEAD);
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
