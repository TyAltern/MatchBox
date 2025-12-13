package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class LastSoloStandingCondition implements VictoryCondition {
    @Override
    public RoleTeam check(GameManager gameManager) {
        int solitaireCount = gameManager.getPlayerManager().countAliveInTeam(RoleTeam.SOLITAIRE);
        int batonCount = gameManager.getPlayerManager().countAliveInTeam(RoleTeam.BATONS);

        if (solitaireCount == 1 && batonCount == 0) {
            return RoleTeam.SOLITAIRE;
        }

        return null;
    }

    @Override
    public String getVictoryMessage(RoleTeam winner) {
        return "§c§lVICTOIRE DE LA FLAMME !\n§7La dernière Flamme remporte la partie !";
    }
}
