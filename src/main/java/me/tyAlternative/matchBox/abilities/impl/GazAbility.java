package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class GazAbility extends Ability {
    public static final String ID = "gaz";

    public GazAbility() {
        super(ID, "Gaz",
                "Vous ne pouvez pas mourir §cEmbrasé§r§f.",
                AbilityCategory.CAPACITY, AbilityUseType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return false;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        return null;
    }
}
