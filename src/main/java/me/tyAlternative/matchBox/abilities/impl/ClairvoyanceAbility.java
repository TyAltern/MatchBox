package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import org.bukkit.entity.Player;

public class ClairvoyanceAbility extends Ability {

    public static String ID = "clairvoyance";

    public ClairvoyanceAbility() {
        super(ID, "Clairvoyance",
                "Vous connaissez la durée restante de la phase de Gameplay en cours.",
                AbilityCategory.CAPACITY, AbilityUseType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return true;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext ctx) {
        return AbilityResult.success();
    }

}
