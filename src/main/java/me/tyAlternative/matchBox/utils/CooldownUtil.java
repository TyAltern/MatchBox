package me.tyalternative.matchbox.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utilitaire simple pour gérer des cooldowns
 */
public class CooldownUtil {

    private final Map<String, Long> cooldowns = new HashMap<>();

    /**
     * Définit un cooldown
     * @param key Clé unique (ex: "player_uuid:ability_id")
     * @param seconds Durée en secondes
     */
    public void setCooldown(String key, int seconds) {
        long expireTime = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(key, expireTime);
    }

    /**
     * Vérifie si un cooldown est actif
     */
    public boolean hasCooldown(String key) {
        Long expireTime = cooldowns.get(key);
        if (expireTime == null) return false;

        if (System.currentTimeMillis() >= expireTime) {
            cooldowns.remove(key);
            return false;
        }

        return true;
    }

    /**
     * Obtient le temps restant en secondes
     */
    public int getRemainingSeconds(String key) {
        Long expireTime = cooldowns.get(key);
        if (expireTime == null) return 0;

        long remaining = expireTime - System.currentTimeMillis();
        return (int) Math.max(0, Math.ceil(remaining / 1000.0));
    }

    /**
     * Retire un cooldown
     */
    public void removeCooldown(String key) {
        cooldowns.remove(key);
    }

    /**
     * Nettoie tous les cooldowns
     */
    public void clear() {
        cooldowns.clear();
    }

    /**
     * Crée une clé unique pour un joueur et une capacité
     */
    public static String makeKey(UUID playerId, String abilityId) {
        return playerId.toString() + ":" + abilityId;
    }
}
