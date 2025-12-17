package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RayonnementAbility extends Ability {
    public static String ID = "rayonnement";

    private final Map<PlayerData, Integer> rayonnementTarget;
    private final double rayonnementRadius = 10.0;
    private final int minimumTime = 5;

    public RayonnementAbility() {
        super(ID, "§6Rayonnement",
                "Embrase le joueur résté le plus proche du round",
                AbilityType.PASSIVE, AbilityTrigger.TICKS, 20);

        rayonnementTarget = new HashMap<>();
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {
        if (Boolean.FALSE.equals(data.getCustomData("rayonnement_actif", Boolean.class))) return false;

        return true;
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext context) {

        Location playerLocation = player.getLocation();

        for (Player targetPlayer : gameManager.getPlayerManager().getAlivePlayer()) {
            if (targetPlayer == player) continue;
            double distance = playerLocation.distance(targetPlayer.getLocation());

            if (distance <= rayonnementRadius) {
                PlayerData targetData = gameManager.getPlayerManager().get(targetPlayer);

                int count = rayonnementTarget.getOrDefault(targetData, 0) + 1;
                rayonnementTarget.put(targetData, count);
            }

        }

        return AbilityResult.success();
    }

    @Override
    public void onGameplayPhaseEnd(Player player, PlayerData data) {
        super.onGameplayPhaseEnd(player, data);

        // Si rayonnement abandonné
        if (Boolean.FALSE.equals(data.getCustomData("rayonnement_actif", Boolean.class))) return;

        PlayerData embrasedPlayerData = null;
         int maxRayonnement = minimumTime;

        for (PlayerData targetData : rayonnementTarget.keySet()) {
            if (maxRayonnement <= rayonnementTarget.get(targetData)) {
                maxRayonnement = rayonnementTarget.get(targetData);
                embrasedPlayerData = targetData;
            }
        }

        if (embrasedPlayerData == null || embrasedPlayerData == data) return;

        gameManager.getEmbrasementManager().embrase(embrasedPlayerData.getPlayerId(), EmbrasementCause.TORCHE);


    }

    public double getRayonnementRadius() {
        return rayonnementRadius;
    }
}
