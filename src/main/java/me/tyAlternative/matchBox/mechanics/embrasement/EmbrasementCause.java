package me.tyalternative.matchbox.mechanics.embrasement;

public enum EmbrasementCause {
    ETINCELLE("Étincelle"),
    TORCHE("Torche"),
    OTHER("Autre");

    private final String displayName;

    EmbrasementCause(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
