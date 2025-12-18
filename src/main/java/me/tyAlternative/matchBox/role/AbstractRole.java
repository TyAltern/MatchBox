package me.tyalternative.matchbox.role;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.abilities.AbilityTrigger;
import me.tyalternative.matchbox.abilities.impl.AmnesieAbility;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import me.tyalternative.matchbox.abilities.Ability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite de base pour tous les rôles
 * Implémente les comportements par défaut
 */
public abstract class AbstractRole implements Role {
    protected final GameManager gameManager;

    protected final String id;
    protected final String displayName;
    protected final String description;
    protected final RoleType type;
    protected RoleTeam team;
    protected final Material guiIcon;
    protected final List<Ability> abilities;

    public AbstractRole(String id, String displayName, String description,
                        RoleType type, RoleTeam team, Material guiIcon) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.type = type;
        this.team = team;
        this.guiIcon = guiIcon;
        this.abilities = new ArrayList<>();

        this.gameManager = MatchBox.getInstance().getGameManager();
    }

    @Override
    public Ability registerAbility(Ability ability) {
        abilities.add(ability);
        return ability;
    }

    @Override
    public Ability registerHiddenAbility(Ability ability) {
        ability.setInvisible(true);
        abilities.add(ability);
        return ability;
    }

    @Override
    public Ability registerDrunkAbility(Ability ability) {
        ability.setDrunk(true);
        abilities.add(ability);
        return ability;
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
    public RoleType getType() {
        return type;
    }

    @Override
    public RoleTeam getTeam() {
        return team;
    }

    @Override
    public void setTeam(RoleTeam team) {
        this.team = team;
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
    public Ability getAbility(String id) {
        for (Ability ability : getAbilities()) {
            if (ability.getId().equals(id)) {
                return ability;
            }
        }
        return null;
    }

    @Override
    public boolean hasAbility(String abilityId) {
        return abilities.stream().anyMatch(a -> a.getId().equals(abilityId));
    }


    // Hooks par défaut (peuvent être override)

    @Override
    public void onAssigned(Player player, PlayerData data) {
        boolean amnesia = false;

        List<Ability> abilityList = new ArrayList<>(abilities);

        for (Ability ability : abilityList) {
            ability.onAssigned(player, data);
            if (ability instanceof AmnesieAbility amnesieAbility) {
                amnesia = true;
            }
        }

        if (!amnesia) sendRoleDescription(player);

    }

    @Override
    public void onGameplayPhaseStart(Player player, PlayerData data) {
        for (Ability ability : abilities) {
            ability.onGameplayPhaseStart(player, data);
        }
        // Override si nécessaire
    }

    @Override
    public void onGameplayPhaseEnd(Player player, PlayerData data) {
        for (Ability ability : abilities) {
            ability.onGameplayPhaseEnd(player, data);
        }
        // Override si nécessaire
    }

    @Override
    public void onVotePhaseStart(Player player, PlayerData data) {
        for (Ability ability : abilities) {
            ability.onVotePhaseStart(player, data);
        }
        // Override si nécessaire
    }

    @Override
    public void onVotePhaseEnd(Player player, PlayerData data) {
        for (Ability ability : abilities) {
            ability.onVotePhaseEnd(player, data);
        }
        // Override si nécessaire
    }

    @Override
    public void onEliminated(Player player, PlayerData data, String cause) {
        for (Ability ability : abilities) {
            ability.onEliminated(player, data, cause);
        }
        // Override si nécessaire
    }

    @Override
    public void onOtherEliminated(Player self, Player eliminated, String cause) {
        for (Ability ability : abilities) {
            ability.onOtherEliminated(self, eliminated, cause);
        }
        // Override si nécessaire
    }



    @Override
    public String toString() {
        return id;
    }


    protected void sendRoleDescription(Player player) {
        gameManager.getMessageManager().sendRoleDescription(player, getDisplayName(), getDescription(), getAbilities());
    }


}
