package me.tyalternative.matchbox.abilities;

public enum AbilityCategory {

    /** Capacité active nécessitant une action du joueur */
    CAPACITY("Capacité", "§6"),

    /** Capacité active nécessitant une action du joueur */
    NEUTRAL("Effet", "§e"),

    /** Capacité active nécessitant une action du joueur */
    CURSE("Malédiction", "§d");


    private String displayName;
    private String colorCode;

    AbilityCategory(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getColorCode() {
        return colorCode;
    }


}
