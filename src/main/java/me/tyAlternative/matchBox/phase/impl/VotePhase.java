package me.tyalternative.matchbox.phase.impl;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.phase.GamePhase;
import me.tyalternative.matchbox.phase.PhaseContext;
import me.tyalternative.matchbox.phase.PhaseType;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class VotePhase implements GamePhase{
    @Override
    public void onStart(GameManager gameManager, PhaseContext context) {
        // Restaurer les Skins/Nametags
        gameManager.getAnonymityManager().showAllSkins();
        gameManager.getAnonymityManager().showAllNametags();

        // Téléportation à la salle de vote
        teleportToTables(gameManager);

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                data.getRole().onVotePhaseStart(data.getPlayer(), data);
            }
        }

        gameManager.broadcastMessage("§dPhase de Vote !");
        gameManager.getSoundManager().playToAll("phase_change");

    }

    @Override
    public void onEnd(GameManager gameManager, PhaseContext context) {

        // Notifier les rôles
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (data.getPlayer() != null && data.hasRole()) {
                data.getRole().onVotePhaseEnd(data.getPlayer(), data);
            }
        }

        // Calculer et traiter le vote
        gameManager.getEliminationManager().processVote();
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
