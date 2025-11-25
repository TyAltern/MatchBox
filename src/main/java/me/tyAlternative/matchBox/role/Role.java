package me.tyalternative.matchbox.role;

import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.Ability;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Interface principale pour tous les rôles
 */
public interface Role {
    // Identité
    String getId();
    String getDisplayName();
    String getDescription();
    RoleType getTeam();
    Material getGuiIcon();

    // Capacités
    List<Ability> getAbilities();
    boolean hasAbility(String abilityId);

    // Hooks d'événements
    void onAssigned(Player player, PlayerData data);
    void onGameplayPhaseStart(Player player, PlayerData data);
    void onGameplayPhaseEnd(Player player, PlayerData data);
    void onVotePhaseStart(Player player, PlayerData data);
    void onVotePhaseEnd(Player player, PlayerData data);
    void onEliminated(Player player, PlayerData data, String cause);
    void onOtherEliminated(Player self, Player eliminated, String cause);

}
