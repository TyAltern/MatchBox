package me.tyalternative.matchbox.mechanics.vote;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;

import java.util.*;

public class VoteManager {

    private final GameManager gameManager;

    public VoteManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void castVote(UUID voterId, UUID targetId) {
        PlayerData voterData = gameManager.getPlayerManager().get(voterId);
        if (voterData == null || !voterData.canVote()) return;

        voterData.voteFor(targetId);
    }

    public void removeVote(UUID voterId) {
        PlayerData voterData = gameManager.getPlayerManager().get(voterId);
        if (voterData != null ) voterData.clearVote();
    }

    public UUID calculateResults() {
        List<VoteData> votes = collectVotes();
        return VoteCalculator.calculateEliminated(gameManager, votes);
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
            data.clearVote();
        }
    }

}
