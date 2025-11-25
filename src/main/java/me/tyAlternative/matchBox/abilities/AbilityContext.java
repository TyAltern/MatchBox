package me.tyalternative.matchbox.abilities;

import org.bukkit.entity.Player;

/**
 * Contexte d'exécution d'une capacité
 */
public class AbilityContext {
    private final Player target;
    private final boolean emptyHand;
    private final Object extraData;

    public AbilityContext(Player target, boolean emptyHand, Object extraData) {
        this.target = target;
        this.emptyHand = emptyHand;
        this.extraData = extraData;
    }

    public static AbilityContext of(Player target) {
        return new AbilityContext(target, false, null);
    }

    public static AbilityContext ofEmpty(Player target) {
        return new AbilityContext(target, true, null);
    }

    public static AbilityContext noTarget() {
        return new AbilityContext(null, false, null);
    }

    // Getters
    public Player getTarget() {
        return target;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean isEmptyHand() {
        return emptyHand;
    }

    public Object getExtraData() {
        return extraData;
    }
}
