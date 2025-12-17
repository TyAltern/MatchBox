package me.tyalternative.matchbox.abilities;

public enum AbilityTrigger {
    /** Clic droit sur un joueur */
    RIGHT_CLICK_PLAYER,

    /** Clic gauche sur un joueur */
    LEFT_CLICK_PLAYER,

    /** Swap d'item (F par défaut) */
    SWAP_HAND,

    /** Double swap d'item (F par défaut) */
    DOUBLE_SWAP_HAND,

    /** Automatique (passif) */
    AUTOMATIC,

    /** Tous les x ticks*/
    TICKS,

    /** Manuel via commande/interface */
    MANUAL;
}
