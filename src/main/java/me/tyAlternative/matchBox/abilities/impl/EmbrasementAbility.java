package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import org.bukkit.entity.Player;

public class EmbrasementAbility extends Ability {

    public static String ID = "embrasement";

    public EmbrasementAbility() {
        super(ID, "Embrasement",
                "A chaque phase de Gameplay, vous pouvez clic droit sur un joueur avec une main vide. Ce dernier sera éliminé à la fin de cette phase.",
                AbilityType.ACTIVE, AbilityTrigger.RIGHT_CLICK_PLAYER);


        setUsageRoundLimits(1);

    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext contexte) {
        if (!contexte.hasTarget() || !contexte.isEmptyHand()) return false;

        if (gameManager.getAbilityUsageManager().getRemainingUses(player.getUniqueId(),id,perRoundLimit) == 0) return false;

        PlayerData targetData = gameManager.getPlayerManager().get(contexte.getTarget());

        return targetData != null && targetData.isAlive();
    }


    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext contexte) {
        boolean success = gameManager.getEmbrasementManager()
                .embrase(contexte.getTarget().getUniqueId(), EmbrasementCause.ETINCELLE);

        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);

        if (success) {
            player.sendMessage("§c✓ Vous avez embrasé " + contexte.getTarget().getName());
            return AbilityResult.success();
        } else {
            return AbilityResult.failure("§cCe joueur est protégé !");
        }
    }

    @Override
    public String getUsageLimitMessage(Player player, PlayerData data) {
        return "§cVous avez déja utilisé votre Embrasement cette phase !";
    }
}
