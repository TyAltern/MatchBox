package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import org.bukkit.entity.Player;

public class EmbrasementAbility extends Ability {
    public EmbrasementAbility() {
        super("embrasement", "§6Embrasement",
                "Embrase un joueur (éliminé fin de phase)",
                AbilityType.ACTIVE, AbilityTrigger.RIGHT_CLICK_PLAYER);


        setUsageRoundLimits(1);

    }

    @Override
    public boolean canUse(Player player, PlayerData data, AbilityContext contexte) {
        if (!contexte.hasTarget() || !contexte.isEmptyHand()) return false;

        PlayerData targetData = gameManager.getPlayerManager().get(contexte.getTarget());

        return targetData != null && targetData.isAlive();
    }


    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext contexte) {
        boolean success = gameManager.getEmbrasementManager()
                .embrase(contexte.getTarget().getUniqueId(), EmbrasementCause.ETINCELLE);

        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);

        if (success) {
            player.sendMessage("§c✓ Vous avez embrasé " + contexte.getTarget().getName());
            return AbilityResult.success();
        } else {
            player.sendMessage("erreur");
            return AbilityResult.failure("§cCe joueur est protégé !");
        }
    }


}
