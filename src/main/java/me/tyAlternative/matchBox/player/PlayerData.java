package me.tyalternative.matchbox.player;

import me.tyalternative.matchbox.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Données d'un joueur pendant la partie
 */
public class PlayerData {
    private final UUID playerId;
    private PlayerState state;
    private Role role;

    // Mécaniques de jeu
    private int spectralArrowsRemaining;

    // Système de vote
    private UUID votedPlayer;
    private int voteWeight;
    private boolean canVote;

    // Données personnalisées (pour les capacités complexes)
    private final Map<String, Object> customData;

    public PlayerData(UUID playerId) {
        this.playerId = playerId;
        this.state = PlayerState.LOBBY;
        this.customData = new HashMap<>();
        this.voteWeight = 1;
        this.canVote = true;
    }


    // ========== GETTERS/SETTERS DE BASE ==========

    public UUID getPlayerId() {
        return playerId;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerId);
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public boolean isAlive() {
        return state.isAlive();
    }


    // ========== RÔLE ==========

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasRole() {
        return role != null;
    }


    // ========== FLÈCHES SPECTRALES ==========

    public int getSpectralArrowsRemaining() {
        return spectralArrowsRemaining;
    }

    public void setSpectralArrowsRemaining(int count) {
        this.spectralArrowsRemaining = Math.max(0,count);
    }

    public void addSpectralArrows(int count) {
        this.spectralArrowsRemaining += count;
    }

    public boolean useSpectralArrow() {
        if (hasSpectralArrows()) {
            spectralArrowsRemaining--;
            return true;
        }
        return false;
    }

    public boolean hasSpectralArrows() {
        return spectralArrowsRemaining > 0;
    }




    // ========== SYSTÈME DE VOTE ==========


    public UUID getVotedPlayer() {
        return votedPlayer;
    }

    public int getVoteWeight() {
        return voteWeight;
    }

    public boolean canVote() {
        return canVote;
    }

    public void voteFor(UUID targetId) {
        this.votedPlayer = targetId;
    }

    public void setVoteWeight(int weight) {
        this.voteWeight = Math.max(0, weight);
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public boolean hasVoted() {
        return votedPlayer != null;
    }

    public void clearVote() {
        this.votedPlayer = null;
    }


    // ========== DONNÉES PERSONNALISÉES ==========

    public void setCustomData(String key, Object value) {
        customData.put(key, value);
    }

    public Object getCustomData(String key) {
        return  customData.get(key);
    }

    public <T> T getCustomData(String key, Class<T> type) {
        Object value = customData.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }

    public boolean hasCustomData(String key) {
        return customData.containsKey(key);
    }

    public void removeCustomData(String key) {
        customData.remove(key);
    }

    public void clearCustomData() {
        customData.clear();
    }


    // ========== RÉINITIALISATION ==========

    /**
     * Réinitialise les données pour une nouvelle manche
     */
    public void resetForNewRound() {
        clearVote();
        // Les flèches et panneaux seront gérés par le GameManager
    }

    /**
     * Réinitialisation complète
     */
    public void reset() {
        state = PlayerState.LOBBY;
        role = null;
        spectralArrowsRemaining = 0;
        votedPlayer = null;
        voteWeight = 1;
        canVote = true;
        customData.clear();
    }

    @Override
    public String toString() {
        Player player = getPlayer();
        String playerName = player != null ? player.getName() : "Unknown";
        String roleName = role != null ? role.getDisplayName() : "None";
        return String.format("PlayerData[%s, %s, %s]", playerName, state, roleName);
    }
}
