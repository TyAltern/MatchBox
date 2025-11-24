package me.tyalternative.matchbox.phase;

import me.tyalternative.matchbox.core.GameManager;

/**
 * Interface pour toutes les phases de jeu
 */

public interface GamePhase {

    /**
     * Appelé au démarrage de la phase
     */
    void onStart(GameManager gameManager, PhaseContext context);

    /**
     * Appelé à la fin de la phase
     */
    void onEnd(GameManager gameManager, PhaseContext context);

    /**
     * Retourne le type de phase
     */
    PhaseType getType();

    /**
     * Durée de la phase en millisecondes
     */
    long getDuration(GameManager gameManager);

}
