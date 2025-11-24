package me.tyalternative.matchbox.mechanics.vote;

import java.util.UUID;

public class VoteData {
    private final UUID voterId;
    private final UUID targetId;
    private final int weight;

    public VoteData(UUID voterId, UUID targetId, int weight) {
        this.voterId = voterId;
        this.targetId = targetId;
        this.weight = weight;
    }

    public UUID getVoterId() {
        return voterId;
    }
    public UUID getTargetId() {
        return targetId;
    }
    public int getWeight() {
        return weight;
    }
}
