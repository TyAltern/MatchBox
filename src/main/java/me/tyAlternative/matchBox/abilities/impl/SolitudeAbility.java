package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class SolitudeAbility extends Ability{
    public static final String ID = "solitude";
    private final double radius = 10.0;

    public SolitudeAbility() {
        super(ID, "Solitude",
                "A la fin de chaque manche vous devez être entouré d'au moins un joueur dans un rayon de §7x blocs, sinon vous disparaissez.",
                AbilityCategory.CURSE, AbilityUseType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return false;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        return null;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String getDescription() {
        return "A la fin de chaque manche vous devez être entouré d'au moins un joueur dans un rayon de §7" + getRadius() + "§r blocs, sinon vous disparaissez.";
    }
}
