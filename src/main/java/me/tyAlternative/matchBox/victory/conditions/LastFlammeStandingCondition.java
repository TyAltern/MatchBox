package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class LastFlammeStandingCondition implements VictoryCondition {
    @Override
    public RoleType check(GameManager gameManager) {
        int flammeCount = gameManager.getPlayerManager().getByType(RoleType.FLAMME).size();
        int batonCount = gameManager.getPlayerManager().getByType(RoleType.BATON).size();

        if (flammeCount == 1 && batonCount <= 1) {
            return RoleType.FLAMME;
        }

        return null;
    }

    @Override
    public String getVictoryMessage(RoleType winner) {
        return "§c§lVICTOIRE DE LA FLAMME !\n§7La dernière Flamme remporte la partie !";
    }
}
