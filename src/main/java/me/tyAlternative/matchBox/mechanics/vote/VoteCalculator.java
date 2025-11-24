package me.tyalternative.matchbox.mechanics.vote;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.RoleType;

import java.util.*;
import java.util.stream.Collectors;

public class VoteCalculator {
    public static UUID calculateEliminated(GameManager gameManager, List<VoteData> votes) {

        if (votes.isEmpty()) return null;

        // Compter les votes
        Map<UUID, Integer> voteCount = new HashMap<>();
        for (VoteData vote : votes) {
            voteCount.merge(vote.getTargetId(), vote.getWeight(), Integer::sum);
        }

        // Trouver le max
        int maxVotes = voteCount.values().stream().max(Integer::compareTo).orElse(0);
        return null;
    }
}
