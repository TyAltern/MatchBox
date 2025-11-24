package me.tyalternative.matchbox.mechanics.embrasement;
import java.util.*;

public class EmbrasementQueue {

    private final Map<UUID, EmbrasementCause> queue;

    public EmbrasementQueue() {
        this.queue = new HashMap<>();
    }

    public void add(UUID playerId, EmbrasementCause cause) {
        queue.put(playerId, cause);
    }

    public boolean contains(UUID playerId) {
        return queue.containsKey(playerId);
    }

    public EmbrasementCause getCause(UUID playerId) {
        return queue.get(playerId);
    }

    public Set<UUID> getAll() {
        return new HashSet<>(queue.keySet());
    }

    public Map<UUID, EmbrasementCause> getAllWithCauses() {
        return new HashMap<>(queue);
    }

    public void remove(UUID playerId) {
        queue.remove(playerId);
    }

    public void clear() {
        queue.clear();
    }

    public int size() {
        return queue.size();
    }
}
