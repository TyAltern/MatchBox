package me.tyalternative.matchbox.role.abilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Gestionnaire des cooldowns des capacités
 */
public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns;

    public CooldownManager() {
        this.cooldowns = new HashMap<>();
    }


    /**
     * Définit un cooldown pour un joueur et une capacité
     * @param playerId UUID du joueur
     * @param abilityId ID de la capacité
     * @param durationSeconds Durée en secondes
     */
    public void setCooldown(UUID playerId, String abilityId, int durationSeconds) {
        long expirationTime = System.currentTimeMillis() + (durationSeconds * 1000L);
        cooldowns.computeIfAbsent(playerId, key -> new HashMap<>()).put(abilityId, expirationTime);
    }

    /**
     * Vérifie si une capacité est en cooldown
     */
    public boolean hasCooldown(UUID playerId, String abilityId) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null) return false;

        Long expiration = playerCooldowns.get(abilityId);
        if (expiration == null) return false;

        if (System.currentTimeMillis() >= expiration) {
            playerCooldowns.remove(abilityId);
            return false;
        }
        return true;
    }

    /**
     * Retourne le temps restant en secondes
     */
    public int getRemainingSeconds(UUID playerId, String abilityId) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null) return 0;

        Long expiration = playerCooldowns.get(abilityId);
        if (expiration == null) return 0;

        long remaining = expiration - System.currentTimeMillis();
        return (int) Math.max(0, Math.ceil(remaining / 1000.0));
    }

    /**
     * Retire le cooldown d'une capacité
     */
    public void removeCooldown(UUID playerId, String abilityId) {
        Map<String, Long> playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns != null) playerCooldowns.remove(abilityId);

    }

    /**
     * Nettoie tous les cooldowns d'un joueur
     */
    public void clearPlayer(UUID playerId) {
        cooldowns.remove(playerId);
    }

    /**
     * Nettoie tous les cooldowns
     */
    public void clearAll() {
        cooldowns.clear();
    }
}
