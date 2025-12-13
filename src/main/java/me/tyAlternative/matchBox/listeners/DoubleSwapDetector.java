package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Détecteur de double clic sur swap hand (touche F)
 */
public class DoubleSwapDetector {

    private final GameManager gameManager;

    // Map: PlayerId -> Timestamp du dernier swap
    private final Map<UUID, Long> lastSwapTimes;

    // Map: PlayerId -> Nombre de swaps en attente
    private final Map<UUID, Integer> pendingSwaps;

    public DoubleSwapDetector(GameManager gameManager) {
        this.gameManager = gameManager;

        this.lastSwapTimes = new HashMap<>();
        this.pendingSwaps = new HashMap<>();
    }

    /**
     * Enregistre un swap et détecte s'il s'agit d'un double clic
     * @return true si c'est un double clic, false si c'est un simple clic
     */
    public boolean detectDoubleSwap(UUID playerId) {
        long now = System.currentTimeMillis();
        Long lastSwap = lastSwapTimes.get(playerId);

        // Récupérer le délai configuré
        int maxDelay = gameManager.getSettings().getDoubleSwapMaxDelayMs();

        if (lastSwap != null && (now - lastSwap) <= maxDelay) {
            // Double clic détecté !
            lastSwapTimes.remove(playerId);
            pendingSwaps.remove(playerId);
            return true;
        } else {
            // Premier clic ou délai expiré
            lastSwapTimes.put(playerId, now);
            pendingSwaps.put(playerId, 1);

            // Planifier l'exécution du simple swap après le délai
            scheduleSimpleSwap(playerId, maxDelay);
            return false;
        }

    }

    /**
     * Planifie l'exécution d'un simple swap après le délai
     */
    private void scheduleSimpleSwap(UUID playerId, int delayMs) {
        Bukkit.getScheduler().runTaskLater(
                MatchBox.getInstance(),
                () -> {
                    // Si toujours en attente et pas transformé en double click
                    if (pendingSwaps.getOrDefault(playerId, 0) > 0) {
                        lastSwapTimes.remove(playerId);
                        pendingSwaps.remove(playerId);
                    }
                },
                (delayMs / 50) + 2 // Conversion de ms en tick (on ajoute 1 pour être sur)
        );
    }

    /**
     * Vérifie si un swap est en attente pour un joueur
     */
    public boolean hasPendingSwap(UUID playerId) {
        return pendingSwaps.containsKey(playerId);
    }

    /**
     * Annule le swap en attente (utilisé lors d'un double clic)
     */
    public void cancelPendingSwap(UUID playerId) {
        pendingSwaps.remove(playerId);
    }

    /**
     * Nettoie les données d'un joueur
     */
    public void clearPlayer(UUID playerId) {
        lastSwapTimes.remove(playerId);
        pendingSwaps.remove(playerId);
    }

    /**
     * Nettoie tout
     */
    public void clearAll() {
        lastSwapTimes.clear();
        pendingSwaps.clear();
    }

}

