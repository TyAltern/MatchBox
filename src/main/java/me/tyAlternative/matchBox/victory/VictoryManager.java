package me.tyalternative.matchbox.victory;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.events.GameEndEvent;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.player.PlayerState;
import me.tyalternative.matchbox.role.RoleType;
import me.tyalternative.matchbox.victory.conditions.*;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VictoryManager {

    private final GameManager gameManager;
    private final List<VictoryCondition> conditions;

    public VictoryManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.conditions = new ArrayList<>();

        // Enregistrer les conditions
        registerCondition(new AllFlammesEliminatedCondition());
        registerCondition(new LastFlammeStandingCondition());
    }

    public void registerCondition(VictoryCondition condition) {
        conditions.add(condition);
    }


    /**
     * Vérifie les conditions de victoire
     * @return true si la partie est terminée
     */
    public boolean checkVictory() {
        for (VictoryCondition condition : conditions) {
            RoleType winner = condition.check(gameManager);

            if (winner == null) continue;

            announceVictory(winner, condition.getVictoryMessage(winner));

            return true;
        }
        return false;

    }

    private void announceVictory(RoleType winner, String message) {
        gameManager.broadcastMessage("§a§l=============================");
        gameManager.broadcastMessage(message);
        gameManager.broadcastMessage("§a§l=============================");

        gameManager.getEventBus().call(new GameEndEvent(winner, message));
    }
}
