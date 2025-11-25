package me.tyalternative.matchbox.abilities;

import me.tyalternative.matchbox.core.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Gestionnaire centralisé des utilisations de capacités
 */
public class AbilityUsageManager {

    private final GameManager gameManager;
    // Map: PlayerId -> (AbilityId -> UsageData)
    private final Map<UUID, Map<String, AbilityUsageData>> usages;

    public AbilityUsageManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.usages = new HashMap<>();
    }

    /**
     * Vérifie si un joueur peut utiliser une capacité
     */
    public boolean canUse(UUID playerId, String abilityId, UsageLimit perRoundLimit, UsageLimit perGameLimit) {
        // Si aucune limite, toujours utilisable
        if ( (perRoundLimit == null || perRoundLimit.isUnlimited()) && (perGameLimit == null || perGameLimit.isUnlimited()) ) return true;

        AbilityUsageData data = getOrCreateData(playerId, abilityId);

        // vérifier limite par manche
        if (perRoundLimit != null && !perRoundLimit.isUnlimited()) {
            if (data.getUsesThisRound() >= perRoundLimit.getMaxUses()) return false;
        }

        // vérifier limite par partie
        if (perGameLimit != null && !perGameLimit.isUnlimited()) {
            if (data.getUsesThisGame() >= perGameLimit.getMaxUses()) return false;
        }

        return true;
    }

    /**
     * Enregistre une utilisation
     */
    public void recordUsage(UUID playerId, String abilityId) {
        AbilityUsageData data = getOrCreateData(playerId, abilityId);
        data.recordUse();

        Player player = Bukkit.getPlayer(playerId);
        if (player == null) return;
        gameManager.getActionBarManager().updatePlayer(player);

    }

    /**
     * Obtient le nombre d'utilisations restantes pour une limite donnée
     */
    public int getRemainingUses(UUID playerId, String abilityId, UsageLimit limit) {
        if (limit == null || limit.isUnlimited()) return  -1;

        AbilityUsageData data = getOrCreateData(playerId, abilityId);

        int used = switch (limit.getType()) {
            case PER_GAME -> data.getUsesThisGame();
            case PER_ROUND -> data.getUsesThisRound();
            case null, default -> 0;
        };

        return Math.max(0, limit.getMaxUses() - used);
    }

    /**
     * Reset les utilisations de manche pour un joueur
     */
    public void resetRoundForPlayer(UUID playerId) {
        Map<String, AbilityUsageData> playerData = usages.get(playerId);
        if (playerData != null) {
            playerData.values().forEach(AbilityUsageData::resetRound);
        }
    }

    /**
     * Reset les utilisations de manche pour tous les joueurs
     */
    public void resetRoundForAll() {
        usages.values().forEach(playerData ->
                playerData.values().forEach(AbilityUsageData::resetRound));
    }

    /**
     * Reset complet pour un joueur
     */
    public void resetGameForPlayer(UUID playerId) {
        Map<String, AbilityUsageData> playerData = usages.get(playerId);
        if (playerData != null) {
            playerData.values().forEach(AbilityUsageData::resetGame);
        }
    }

    /**
     * Définit manuellement le nombre d'utilisations (pour capacités spéciales)
     */
    public void setUsages(UUID playerId, String abilityId, int roundUses, int gameUses) {
        AbilityUsageData data = getOrCreateData(playerId, abilityId);
        data.setUsesThisRound(roundUses);
        data.setUsesThisGame(gameUses);
    }

    /**
     * Nettoie toutes les données d'un joueur
     */
    public void clearPlayer(UUID playerId) {
        usages.remove(playerId);
    }

    /**
     * Nettoie tout
     */
    public void clearAll() {
        usages.clear();
    }


    private AbilityUsageData getOrCreateData(UUID playerId, String abilityId) {
        return usages.computeIfAbsent(playerId, k -> new HashMap<>())
                .computeIfAbsent(abilityId, k -> new AbilityUsageData());
    }



}
