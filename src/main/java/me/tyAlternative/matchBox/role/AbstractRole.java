package me.tyalternative.matchbox.role;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.role.abilities.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite de base pour tous les rôles
 * Implémente les comportements par défaut
 */
public abstract class AbstractRole implements Role {

    protected final String id;
    protected final String displayName;
    protected final String description;
    protected final RoleType team;
    protected final Material guiIcon;
    protected final List<Ability> abilities;

    public AbstractRole(String id, String displayName, String description,
                        RoleType team, Material guiIcon) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.team = team;
        this.guiIcon = guiIcon;
        this.abilities = new ArrayList<>();
    }

    protected void registerAbility(Ability ability) {
        abilities.add(ability);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public RoleType getTeam() {
        return team;
    }

    @Override
    public Material getGuiIcon() {
        return guiIcon;
    }

    @Override
    public List<Ability> getAbilities() {
        return new ArrayList<>(abilities);
    }

    @Override
    public boolean hasAbility(String abilityId) {
        return abilities.stream().anyMatch(a -> a.getId().equals(abilityId));
    }


    // Hooks par défaut (peuvent être override)

    @Override
    public void onAssigned(Player player, PlayerData data) {
        player.sendMessage("§8" + "=".repeat(50));
        player.sendMessage("§e[Boite d'Allumettes] §7Vous êtes §r" + displayName);
        player.sendMessage("");
        player.sendMessage("§7" + description);
        player.sendMessage("§8" + "=".repeat(50));
    }

    @Override
    public void onGameplayPhaseStart(Player player, PlayerData data) {
        // Override si nécessaire
    }

    @Override
    public void onGameplayPhaseEnd(Player player, PlayerData data) {
        // Override si nécessaire
    }

    @Override
    public void onVotePhaseStart(Player player, PlayerData data) {
        // Override si nécessaire
    }

    @Override
    public void onVotePhaseEnd(Player player, PlayerData data) {
        // Override si nécessaire
    }

    @Override
    public void onEliminated(Player player, PlayerData data, String cause) {
        // Override si nécessaire
    }

    @Override
    public void onOtherEliminated(Player self, Player eliminated, String cause) {
        // Override si nécessaire
    }

    @Override
    public String toString() {
        return id;
    }
}
