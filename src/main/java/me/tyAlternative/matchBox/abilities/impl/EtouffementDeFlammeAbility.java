package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

public class EtouffementDeFlammeAbility extends Ability {
    public static String ID = "etouffement_de_flamme";

    public EtouffementDeFlammeAbility() {
        super(ID, "Étouffement de Flamme",
                "Vous pouvez décider d'abandonner votre §6Rayonnement §fpour un tour.",
                AbilityCategory.CAPACITY, AbilityUseType.ACTIVE, AbilityTrigger.SWAP_HAND);

        setUsageRoundLimits(1);
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        if (Boolean.FALSE.equals(data.getCustomData("rayonnement_actif", Boolean.class))) return false;

        return true;
    }

    @Override
    protected AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        data.setCustomData("rayonnement_actif" , false);

        player.sendMessage("§6Vous avez Étouffé votre Flamme");

        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);

        return AbilityResult.success();
    }

    @Override
    public boolean canBeDrunk() {
        return true;
    }

    @Override
    protected AbilityResult executeDrunk(Player player, PlayerData data, AbilityContext context) {
        gameManager.getAbilityUsageManager().recordUsage(player.getUniqueId(), id);
        player.sendMessage("§6Vous avez Étouffé votre Flamme");

        return AbilityResult.success();
    }

    @Override
    public void onGameplayPhaseStart(Player player, PlayerData data) {
        super.onGameplayPhaseStart(player, data);
        data.setCustomData("rayonnement_actif" , true);

    }
}
