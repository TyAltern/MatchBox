package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import org.bukkit.entity.Player;


public class CendreAbility extends Ability{

    public static String ID = "cendre";

    public CendreAbility() {
        super(ID, "Cendre",
                "Vous possédez une protection contre la §6Poudre de cheminée§f de §cl'Etincelle§f.",
                AbilityType.PASSIVE, AbilityTrigger.AUTOMATIC);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        return true;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        return AbilityResult.success();
    }

}
