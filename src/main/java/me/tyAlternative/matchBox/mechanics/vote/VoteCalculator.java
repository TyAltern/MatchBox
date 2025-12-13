package me.tyalternative.matchbox.mechanics.vote;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.RoleType;

import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class VoteCalculator {

    private final GameManager gameManager;

    public VoteCalculator(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public UUID calculateEliminated(List<VoteData> votes) {

        if (votes.isEmpty()) return null;

        // Compter les votes
        Map<UUID, Integer> voteCount = new HashMap<>();
        for (VoteData vote : votes) {
            voteCount.merge(vote.getTargetId(), vote.getWeight(), Integer::sum);
        }

        UUID flammeVoted = getMaxVotedByType(RoleType.FLAMME, voteCount);
        UUID batonVoted = getMaxVotedByType(RoleType.BATON, voteCount);

        if (flammeVoted == null && batonVoted == null) return null;
        if (flammeVoted == null) return batonVoted;
        if (batonVoted == null) return flammeVoted;

        if (voteCount.get(flammeVoted) > voteCount.get(batonVoted)) {
            return flammeVoted;
        } else {
            return batonVoted;
        }

    }

    private UUID getMaxVotedByType(RoleType type, Map<UUID, Integer> voteCount) {
        List<UUID> votedPlayers = new ArrayList<>();
        int maxVote = 0;
        return null;

//        for (UUID playerId : voteCount.keySet()) {
//
//            PlayerData data = gameManager.getPlayerManager().get(playerId);
//            if (!data.hasRole()) continue;
//            if (data.getRole().getType() != type) continue;
//
//            int vote = voteCount.get(playerId);
//            if (maxVote < vote) {
//                maxVote = vote;
//                votedPlayers.clear();
//                votedPlayers.add(playerId);
//            } else if (maxVote == vote && vote > 0) {
//                votedPlayers.add(playerId);
//            }
//
//        }
//
//        MatchBox.getInstance().getLogger().info("vote: " + type + " -> " + votedPlayers.toString());
//
//        if (votedPlayers.isEmpty()) return null;
//
//        Random random = new Random();
//        return votedPlayers.get(random.nextInt(votedPlayers.size()));
    }
}
