package me.tyalternative.matchbox.mechanics.vote;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.*;

public class VoteManager {

    private final GameManager gameManager;
    private final VoteCalculator voteCalculator;

    public VoteManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.voteCalculator = new VoteCalculator(gameManager);
    }

    public void castVote(UUID voterId, UUID targetId) {
        PlayerData voterData = gameManager.getPlayerManager().get(voterId);
        if (voterData == null || !voterData.canVote()) return;

        voterData.voteFor(targetId);
        gameManager.getGlowingManager().setPlayerGlow(voterId, targetId, ChatColor.GOLD);
    }

    public void removeVote(UUID voterId) {
        PlayerData voterData = gameManager.getPlayerManager().get(voterId);
        if (voterData != null ) {
            UUID targetId = voterData.getVotedPlayer();
            voterData.clearVote();
            if (targetId == null || Bukkit.getPlayer(targetId) == null) return;
            gameManager.getGlowingManager().removePlayerGlow(voterId, targetId);
        }
    }

    public UUID calculateResults() {
        List<VoteData> votes = collectVotes();
        return voteCalculator.calculateEliminated(votes);
    }

    private List<VoteData> collectVotes() {
        List<VoteData> votes = new ArrayList<>();

        for (PlayerData voter : gameManager.getPlayerManager().getAlive()) {
            if (!voter.canVote() || !voter.hasVoted()) continue;

            votes.add(new VoteData(
                    voter.getPlayerId(),
                    voter.getVotedPlayer(),
                    voter.getVoteWeight()
            ));

        }
        return votes;
    }

    public int getVoteCount(UUID targetId) {
        int count = 0;
        for (PlayerData voter : gameManager.getPlayerManager().getAlive()) {
            if (voter.hasVoted() && voter.getVotedPlayer().equals(targetId)) {
                count += voter.getVoteWeight();
            }
        }
        return count;
    }

    public void clearAll() {
        for (PlayerData data : gameManager.getPlayerManager().getAll()) {
            removeVote(data.getPlayerId());
        }
    }

}
