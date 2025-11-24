package me.tyalternative.matchbox.role;

import net.kyori.adventure.text.format.TextColor;

public enum RoleType {
    FLAMME("Flamme", TextColor.color(208, 4, 17)),
    BATON("Bâton", TextColor.color(230, 201, 14)),
    NEUTRAL("Neutre", TextColor.color(255, 255, 255));

    private final String displayName;
    private final TextColor color;

    RoleType(String displayName, TextColor color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TextColor getColor() {
        return color;
    }
}
