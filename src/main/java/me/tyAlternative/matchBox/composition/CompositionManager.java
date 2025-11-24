package me.tyalternative.matchbox.composition;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.role.Role;

import java.util.*;

public class CompositionManager {

    private final MatchBox plugin;
    private final Map<Role, Integer> composition;

    public CompositionManager(MatchBox plugin) {
        this.plugin = plugin;
        this.composition = new HashMap<>();
    }

    public void setRoleCount(Role role, int count) {
        if (count <= 0) {
            composition.remove(role);
        } else {
            composition.put(role, count);
        }
    }

    public void addRole(Role role, int count) {
        composition.merge(role, count, Integer::sum);
    }

    public void removeRole(Role role, int count) {
        composition.computeIfPresent(role, (r, current) -> {
            int newCount = current - count;
            return newCount <= 0 ? null : newCount;
        });
    }

    public int getRoleCount(Role role) {
        return composition.getOrDefault(role, 0);
    }

    public Map<Role, Integer> getComposition() {
        return new HashMap<>(composition);
    }

    public int getTotalRoles() {
        return composition.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clear() {
        composition.clear();
    }

    /**
     * Crée une liste de rôles à partir de la composition
     */
    public List<Role> createRoleList(Map<Role, Integer> comp) {
        List<Role> roles = new ArrayList<>();
        for (Map.Entry<Role, Integer> entry : comp.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                roles.add(entry.getKey());
            }
        }
        return roles;
    }
}
