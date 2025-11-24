package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class AllFlammesEliminatedCondition  implements VictoryCondition {

    @Override
    public RoleType check(GameManager gameManager) {
        int flammeCount = gameManager.getPlayerManager().getByType(RoleType.FLAMME).size();

        return flammeCount == 0 ? RoleType.BATON : null;
    }

    @Override
    public String getVictoryMessage(RoleType winner) {
        return "§e§lVICTOIRE DES BÂTONS !\n§7Toutes les Flammes ont été éliminées !";
    }
}
