package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class PriseDeFeuAbility extends Ability {
    public static final String ID = "prise_de_feu";
    private final double radius = 10.0;

    public PriseDeFeuAbility() {
        super(ID, "Prise de Feu",
                "Si à la fin d'une manche de Gameplay, vous êtes à moins de §7x blocs d'un joueur §cEmbrasé§r§f, vous vous §cEmbrasé §r§f vous aussi.",
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
        return "Si à la fin d'une manche de Gameplay, vous êtes à moins de §7" + getRadius() + "§r blocs d'un joueur §cEmbrasé§r§f, vous disparaissez.";
    }
}
