package me.tyalternative.matchbox.abilities;

public enum AbilityType {
    /** Capacité active nécessitant une action du joueur */
    ACTIVE,

    /** Capacité passive toujours active */
    PASSIVE,

    /** Capacité pouvant être activée/désactivée */
    TOGGLE,

    /** Capacité utilisable uniquement pendant les votes */
    VOTE;
}
