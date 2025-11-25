package me.tyalternative.matchbox.abilities;

import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.entity.Player;

/**
 * Classe de base pour toutes les capacités
 */
public abstract class Ability {

    protected final GameManager gameManager;

    protected final String id;
    protected final String name;
    protected final String description;
    protected final AbilityType type;
    protected final AbilityTrigger trigger;

    // Limites d'utilisation
    protected UsageLimit perRoundLimit;
    protected UsageLimit perGameLimit;

    public Ability(String id, String name, String description, AbilityType type, AbilityTrigger trigger) {

        this.gameManager = MatchBox.getInstance().getGameManager();

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.trigger = trigger;

        // Par défaut : illimité
        this.perRoundLimit = UsageLimit.unlimited();
        this.perGameLimit = UsageLimit.unlimited();

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

    /**
     * Définit les limites d'utilisation
     */
    protected void setUsageLimits(UsageLimit perRound, UsageLimit perGame) {
        this.perRoundLimit = perRound;
        this.perGameLimit = perGame;
    }
    /**
     * Définit les limites d'utilisation par Round
     */
    protected void setUsageRoundLimits(int perRound) {
        if (perRound >= 0) {
            this.perRoundLimit = UsageLimit.perRound(perRound);
        } else {
            this.perRoundLimit = UsageLimit.unlimited();
        }
        this.perGameLimit = UsageLimit.unlimited();
    }
    /**
     * Définit les limites d'utilisation par Game
     */
    protected void setUsageGameLimits(int perGame) {
        this.perRoundLimit = UsageLimit.unlimited();
        if (perGame >= 0) {
            this.perGameLimit = UsageLimit.perGame(perGame);
        } else {
            this.perGameLimit = UsageLimit.unlimited();
        }
    }


    /**
     * Message d'erreur pour limite d'utilisation
     */
    public String getUsageLimitMessage(int remainingRound, int remainingGame) {
        if (remainingRound == 0 && !perRoundLimit.isUnlimited()) {
            return "§cVous avez épuisé cette capacité pour cette manche ! (0/" + perRoundLimit.getMaxUses() + ")";
        }
        if (remainingGame == 0 && !perGameLimit.isUnlimited()) {
            return "§cVous avez épuisé cette capacité pour cette partie ! (0/" + perGameLimit.getMaxUses() + ")";
        }
        return "§cCapacité indisponible.";
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public AbilityType getType() { return type; }
    public AbilityTrigger getTrigger() { return trigger; }

    public UsageLimit getPerRoundLimit() { return perRoundLimit; }
    public UsageLimit getPerGameLimit() { return perGameLimit; }

    public boolean hasUsageLimit() {
        return (perRoundLimit != null && !perRoundLimit.isUnlimited()) ||
                (perGameLimit != null && !perGameLimit.isUnlimited());
    }
}
