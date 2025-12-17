package me.tyalternative.matchbox.abilities.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.*;
import me.tyalternative.matchbox.role.Role;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoudreChemineeAbility extends Ability{

    public static String ID = "poudre_cheminee";
    private final CooldownManager cooldownManager;

    public PoudreChemineeAbility() {
        super(ID, "§dPoudre de cheminée",
                "Échange de position (CD: 20s)",
                AbilityType.ACTIVE, AbilityTrigger.SWAP_HAND);

        hasCooldown = true;
        this.cooldownManager = new CooldownManager();
    }

    @Override
    protected boolean canUse(Player player, PlayerData data, AbilityContext context) {

        if (cooldownManager.hasCooldown(player.getUniqueId(), id)) {
            return false;
        }


        if (getPossibleVictims(player).isEmpty()) return false;

        return true;
    }

    @Override
    public AbilityResult execute(Player player, PlayerData data, AbilityContext context) {
        List<PlayerData> victims = getPossibleVictims(player);
        if (victims.isEmpty()) return AbilityResult.success("Pas de cible possible pour le tp");

        Collections.shuffle(victims);

        PlayerData victimData = victims.getFirst();

        Location playerLoc = player.getLocation().clone();
        Location victimLoc = victimData.getPlayer().getLocation().clone();

        player.teleport(victimLoc);
        victimData.getPlayer().teleport(playerLoc);


        int cooldown = gameManager.getSettings().getPoudreChemineeCooldown();
        cooldownManager.setCooldown(player.getUniqueId(), id, cooldown);

        return AbilityResult.success("§d✓ Téléportation effectuée !");
    }

    private List<PlayerData> getPossibleVictims(Player player) {

        List<PlayerData> possibleVictims = new ArrayList<>();
        for (PlayerData playerData : gameManager.getPlayerManager().getAlive()) {
            if (playerData.getPlayer() == player) continue;

            Role role = playerData.getRole();
            if (role == null) continue;

            Ability ability = role.getAbility(CendreAbility.ID);
            if (ability != null && ability.canUseAbility(playerData.getPlayer(), playerData, AbilityContext.noTarget())) continue;

            possibleVictims.add(playerData);
        }
        return possibleVictims;
    }


}
