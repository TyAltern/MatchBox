package me.tyalternative.matchbox.victory.conditions;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.victory.VictoryCondition;

public class LastFlammeStandingCondition implements VictoryCondition {
    @Override
    public RoleType check(GameManager gameManager) {
        int flammeCount = gameManager.getPlayerManager().getAliveByType(RoleType.FLAMME).size();
        int batonCount = gameManager.getPlayerManager().getAliveByType(RoleType.BATON).size();

        if (flammeCount == 1 && batonCount == 0) {
            return RoleType.FLAMME;
        }

        return null;
    }

    @Override
    public String getVictoryMessage(RoleType winner) {
        return "§c§lVICTOIRE DE LA FLAMME !\n§7La dernière Flamme remporte la partie !";
    }
}
