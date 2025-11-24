package me.tyalternative.matchbox.mechanics.protection;

public enum ProtectionType {
    SOUFFLE("Souffle"),
    OTHER("Autre");

    private final String displayName;

    ProtectionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
