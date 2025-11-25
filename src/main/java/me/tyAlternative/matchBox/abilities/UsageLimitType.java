package me.tyalternative.matchbox.abilities;

/**
 * Type de limitation d'utilisation d'une capacité
 */
public enum UsageLimitType {
    /**
     * Limite par manche (reset à chaque nouvelle manche)
     */
    PER_ROUND,

    /**
     * Limite pour toute la partie (ne reset jamais)
     */
    PER_GAME;
}
