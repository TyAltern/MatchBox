package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.mechanics.protection.ProtectionType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class SouffleAbility extends Ability {

    public static String ID = "souffle";

    public SouffleAbility() {
        super(ID, "§bSouffle",
                "Protège contre l'Embrasement",
                AbilityType.ACTIVE, AbilityTrigger.RIGHT_CLICK_PLAYER);

        setUsageRoundLimits(1);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        if (!context.hasTarget() || !context.isEmptyHand()) return false;

        PlayerData targetData = gameManager.getPlayerManager().get(context.getTarget());

        return targetData != null && targetData.isAlive();
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        boolean success = gameManager.getProtectionManager().protect(context.getTarget().getUniqueId(), ProtectionType.SOUFFLE);

        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);

        if (success) {
            return AbilityResult.success("§b✓ Vous avez protégé " + context.getTarget().getName());
        } else {
            return AbilityResult.failure("§cCe joueur est déjà protégé !");
        }

    }

    @Override
    public String getUsageLimitMessage(Player player, PlayerData data) {
        return "§cVous avez déja utilisé votre Souffle cette phase !";
    }
}
