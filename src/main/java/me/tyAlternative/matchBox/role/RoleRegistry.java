package me.tyalternative.matchbox.role;

import me.tyalternative.matchbox.MatchBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Registre centralisé de tous les rôles
 */
public class RoleRegistry {

    private final Map<String, Role> roles;

    public RoleRegistry() {
        this.roles = new HashMap<>();
    }

    /**
     * Enregistre un nouveau rôle
     */
    public void register(Role role) {
        if (roles.containsKey(role.getId())) {
            throw new IllegalArgumentException("Rôle déjà enregistré: " + role.getId());
        }
        MatchBox.getInstance().getLogger().info(" -> " + role.getId() + " enregistré");

        roles.put(role.getId(), role);
    }

    /**
     * Récupère un rôle par son ID
     */
    public Role get(String id) {
        return roles.get(id);
    }

    /**
     * Vérifie si un rôle existe
     */
    public boolean exists(String id) {
        return roles.containsKey(id);
    }

    /**
     * Retourne tous les rôles enregistrés
     */
    public Collection<Role> getAll() {
        return roles.values();
    }

    /**
     * Nombre de rôles enregistrés
     */
    public int count() {
        return roles.size();
    }
}
