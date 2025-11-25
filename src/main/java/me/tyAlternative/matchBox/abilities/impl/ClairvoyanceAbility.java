package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import org.bukkit.entity.Player;

public class ClairvoyanceAbility extends Ability {

    public ClairvoyanceAbility() {
        super("clairvoyance", "§dClairvoyance",
                "Voit le temps restant",
                AbilityType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    public boolean canUse(Player player, PlayerData data, AbilityContext ctx) {
        return true;
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext ctx) {
        return AbilityResult.success();
    }

}
