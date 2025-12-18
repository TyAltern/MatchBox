package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class FlintAndSteelVictoryCondition implements VictoryCondition {
    @Override
    public RoleTeam check(GameManager gameManager) {
        int solitaireCount = gameManager.getPlayerManager().countAliveInTeam(RoleTeam.SOLITAIRE);
        int batonCount = gameManager.getPlayerManager().countAliveInTeam(RoleTeam.BATONS);
        int flintNSteelCount = gameManager.getPlayerManager().countAliveInTeam(RoleTeam.SILEX_ACIER);

        if (solitaireCount == 0 && batonCount >= 1 && flintNSteelCount == 0) {
            return RoleTeam.SILEX_ACIER;
        }

        return null;
    }

    @Override
    public String getVictoryMessage(RoleTeam winner) {
        return "§c§lVICTOIRE DU SILEX ET DE L'ACIER !\n§7Ces êtres incendiaires remportent la partie";
    }
}
