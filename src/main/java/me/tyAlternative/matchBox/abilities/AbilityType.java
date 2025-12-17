package me.tyalternative.matchbox.abilities;

public enum AbilityType {

    /** Capacité active nécessitant une action du joueur */
    ACTIVE("Actif"),

    /** Capacité passive toujours active */
    PASSIVE("Passif"),

    /** Capacité pouvant être activée/désactivée */
    TOGGLE("Toggle"),

    /** Capacité utilisable uniquement pendant les votes */
    VOTE("Vote");

    private String displayName;

    AbilityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
