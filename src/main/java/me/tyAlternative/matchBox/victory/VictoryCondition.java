package me.tyalternative.matchbox.victory;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;

/**
 * Interface pour les conditions de victoire
 */
public interface VictoryCondition {

    /**
     * Vérifie si la condition de victoire est remplie
     * @return Le gagnant ou null si aucun
     */
    RoleTeam check(GameManager gameManager);

    /**
     * Message de victoire
     */
    String getVictoryMessage(RoleTeam winner);
}
