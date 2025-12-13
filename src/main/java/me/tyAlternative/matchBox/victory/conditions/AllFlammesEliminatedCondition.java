package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleTeam;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class AllFlammesEliminatedCondition  implements VictoryCondition {

    @Override
    public RoleTeam check(GameManager gameManager) {
        int flammeCount = gameManager.getPlayerManager().getAliveByTeam(RoleTeam.SOLITAIRE).size();

        return flammeCount == 0 ? RoleTeam.BATONS : null;
    }

    @Override
    public String getVictoryMessage(RoleTeam winner) {
        return "§e§lVICTOIRE DES BÂTONS !\n§7Toutes les Flammes ont été éliminées !";
    }
}
