package me.tyalternative.matchbox.role;

public enum RoleTeam {
    /**
     * Chaque joueur pour soi (Flammes solitaires)
     */
    SOLITAIRE("Solitaire", "Vous devez gagner seul"),


    /**
     * Duo de Flammes (gagnent ensemble)
     */
    SILEX_ACIER("Silex et Acier", "Un duo prêt à tout enflammer"),

    /**
     * Team des Bâtons (gagnent ensemble)
     */
    BATONS("Bâtons", "Éliminez toutes les Flammes ensemble");

    private final String displayName;
    private final String description;

    RoleTeam(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si c'est une team solitaire
     */
    public boolean isSolo() {
        return this == SOLITAIRE;
    }
}
