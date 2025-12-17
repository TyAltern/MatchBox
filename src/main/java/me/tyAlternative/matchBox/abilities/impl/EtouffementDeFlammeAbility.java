package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class EtouffementDeFlammeAbility extends Ability {
    public static String ID = "etouffement_de_flamme";

    public EtouffementDeFlammeAbility() {
        super(ID, "§6Étouffement de Flamme",
                "Désactive Rayonnement 1 manche",
                AbilityType.TOGGLE, AbilityTrigger.SWAP_HAND);

        setUsageRoundLimits(1);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        if (Boolean.FALSE.equals(data.getCustomData("rayonnement_actif", Boolean.class))) return false;

        return true;
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        data.setCustomData("rayonnement_actif" , false);

        player.sendMessage("§6Vous avez Étouffé votre Flamme");

        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);

        return AbilityResult.success();
    }

    @Override
    public void onGameplayPhaseStart(Player player, PlayerData data) {
        super.onGameplayPhaseStart(player, data);
        data.setCustomData("rayonnement_actif" , true);

    }
}
