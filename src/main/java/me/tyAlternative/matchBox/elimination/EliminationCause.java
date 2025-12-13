package me.tyalternative.matchbox.elimination;

public enum EliminationCause {
    EMBRASEMENT_ETINCELLE("Embrasement (Étincelle)"),
    EMBRASEMENT_TORCHE("Embrasement (Torche)"),
    VOTE("Vote"),
    DISCONNECT("Déconnexion"),
    UNKNOWN("Inconnu");

    private final String displayName;

    EliminationCause(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
