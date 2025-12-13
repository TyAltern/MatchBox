package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.List;

public class VotePhase implements GamePhase{
    @Override
    public void onStart(GameManager gameManager, PhaseContext context) {

        // Téléportation à la salle de vote
        teleportToTables(gameManager);

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            Player player = data.getPlayer();
            if (player == null || !data.hasRole()) continue;

            data.getRole().onVotePhaseStart(data.getPlayer(), data);
            player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).setBaseValue(100);
            player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(100);

        }

        gameManager.broadcastMessage("§dPhase de Vote !");
        gameManager.getSoundManager().playToAll("phase_change");

    }

    @Override
    public void onEnd(GameManager gameManager, PhaseContext context) {

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            Player player = data.getPlayer();
            if (player == null || !data.hasRole()) continue;

            data.getRole().onVotePhaseEnd(data.getPlayer(), data);
            player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).setBaseValue(4.5);
            player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE).setBaseValue(3);
        }

        // Calculer et traiter le vote
        gameManager.getEliminationManager().processVote();

        gameManager.getVoteManager().clearAll();
    }

    @Override
    public PhaseType getType() {
        return PhaseType.VOTE;
    }

    @Override
    public long getDuration(GameManager gameManager) {
        return gameManager.getSettings().getVoteDuration() * 1000L;
    }
    
    private void teleportToTables(GameManager gameManager) {
        List<Location> tables = gameManager.getSettings().getVoteTableLocations();
        if (tables.isEmpty()) return;
        
        List<Player> players = gameManager.getPlayerManager().getAlivePlayer();

        for (int i = 0; i < players.size(); i++) {
            if (i < tables.size()) {
                players.get(i).teleport(tables.get(i));
            }
        }
    }
}
