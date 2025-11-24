package me.tyalternative.matchbox.phase;

/**
 * Types de phases du jeu
 */
public enum PhaseType {
        /**
         * Phase d'attente avant le début
         */
    LOBBY("§7Lobby", "§7En attente de joueurs..."),

    /**
     * Phase de gameplay anonyme
     */
    GAMEPLAY("§bGameplay", "§7Phase de jeu en cours"),

    /**
     * Phase de vote et discussion
     */
    VOTE("§dVote", "§7Phase de vote en cours"),

    /**
     * Fin de partie
     */
    END("§aFin", "§7Partie terminée");

    private final String displayName;
    private final String description;

    PhaseType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isInGame() {
        return this == GAMEPLAY || this == VOTE;
    }
}
