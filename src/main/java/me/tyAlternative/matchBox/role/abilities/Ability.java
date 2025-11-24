package me.tyalternative.matchbox.role.abilities;

import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

/**
 * Interface de base pour toutes les capacités
 */

public abstract class Ability {

    protected final String id;
    protected final String name;
    protected final String description;
    protected final AbilityType type;
    protected final AbilityTrigger trigger;

    public Ability(String id, String name, String description, AbilityType type, AbilityTrigger trigger) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.trigger = trigger;
    }

    /**
     * Exécute la capacité
     * @return true si la capacité a été exécutée avec succès
     */
    public abstract AbilityResult execute(Player player, PlayerData data, AbilityContext context);

    /**
     * Vérifie si la capacité peut être utilisée
     */
    public abstract boolean canUse(Player player, PlayerData data, AbilityContext context);

    /**
     * Message d'erreur si la capacité ne peut pas être utilisée
     */
    public String getCannotUseMessage(Player player, PlayerData data) {
        return "§cVous ne pouvez pas utiliser cette capacité maintenant.";
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public AbilityType getType() { return type; }
    public AbilityTrigger getTrigger() { return trigger; }
}
