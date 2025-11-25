package me.tyalternative.matchbox.elimination;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.events.PlayerEliminatedEvent;
import me.tyalternative.matchbox.mechanics.embrasement.EmbrasementCause;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.player.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class EliminationManager {

    private final GameManager gameManager;

    public EliminationManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Traite les embrasements de fin de phase Gameplay
     */
    public void processEmbrasements() {
        Map<UUID, EmbrasementCause> embrased = gameManager.getEmbrasementManager().getQueue().getAllWithCauses();

        for (Map.Entry<UUID, EmbrasementCause> entry : embrased.entrySet()) {

            if (gameManager.getProtectionManager().isProtected(entry.getKey())) continue;

            EliminationCause cause = getEliminationCause(entry.getValue());

            PlayerData data = gameManager.getPlayerManager().get(entry.getKey());
            if (data != null && data.hasRole() && data.isAlive()) {

                // Retarde les Calcinés embrasés
                if (data.getRole().hasAbility("calcine")) {
                    data.setCustomData("pending_embrasement", true);
                    data.setCustomData("embrasement_cause", cause);
                    continue;
                }
            }


            eliminate(entry.getKey(), cause);
        }

        // Traiter les Calcinés en attente
        for (PlayerData data : gameManager.getPlayerManager().getAlive()) {
            if (!data.hasRole()) continue;

            Boolean pending = data.getCustomData("pending_embrasement", Boolean.class);
            if (Boolean.TRUE.equals(pending)) {
                EmbrasementCause embrasementCause = data.getCustomData("embrasement_cause", EmbrasementCause.class);
                EliminationCause cause = getEliminationCause(embrasementCause);

                eliminate(data.getPlayerId(), cause);
                data.setCustomData("pending_embrasement", false);
            }
        }

    }

    /**
     * Traite le vote de fin de phase Vote
     */
    public void processVote() {
        UUID eliminated = gameManager.getVoteManager().calculateResults();

        if (eliminated != null) {
            eliminate(eliminated, EliminationCause.VOTE);
        } else {
            gameManager.broadcastMessage("§7Aucun joueur éliminé au vote.");
        }
    }


    /**
     * Élimine un joueur
     */
    public void eliminate(UUID playerId, EliminationCause cause) {
        PlayerData data = gameManager.getPlayerManager().get(playerId);
        if (data == null || !data.isAlive()) return;

        Player player = data.getPlayer();
        if(player == null) return;

        // Hook du rôle
        if (data.hasRole()) {
            data.getRole().onEliminated(player, data, cause.getDisplayName());
        }

        // Notifier les autres rôles
        for (PlayerData otherData : gameManager.getPlayerManager().getAlive()) {
            if (otherData.getPlayer() != null && otherData.hasRole()) {
                otherData.getRole().onOtherEliminated(otherData.getPlayer(), player, cause.getDisplayName());
            }
        }

        // Événement
        gameManager.getEventBus().call(new PlayerEliminatedEvent(player, cause));

        // Changer l'état
        data.setState(PlayerState.DEAD);
        player.setGameMode(GameMode.SPECTATOR);
        gameManager.getBossBarManager().removePlayer(playerId);

        // Message
        String roleName = data.hasRole() ? data.getRole().getDisplayName() : "Inconnu";
        if (gameManager.getSettings().shouldRevealRoleOnDeath()) {
            gameManager.broadcastMessage("§e" + player.getName() + " §7(" + roleName + ") a été éliminé ! §8(" + cause.getDisplayName() + ")");
        } else {
            gameManager.broadcastMessage("§e" + player.getName() + " §7a été éliminé ! §8(" + cause.getDisplayName() + ")");
        }

        gameManager.getSoundManager().playToAll("elimination");

    }

    public EliminationCause getEliminationCause(EmbrasementCause cause) {
        return switch (cause) {
            case ETINCELLE -> EliminationCause.EMBRASEMENT_ETINCELLE;
            case TORCHE -> EliminationCause.EMBRASEMENT_TORCHE;
            case null, default -> EliminationCause.DISCONNECT;
        };
    }
}
