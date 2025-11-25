package me.tyalternative.matchbox.abilities;

/**
 * Configuration de limite d'utilisation d'une capacité
 */
public class UsageLimit {

    private final UsageLimitType type;
    private final int maxUses;

    public UsageLimit(UsageLimitType type, int maxUses) {
        this.type = type;
        this.maxUses = maxUses;
    }

    /**
     * Crée une limite par manche
     */
    public static UsageLimit perRound(int maxUses) {
        return new UsageLimit(UsageLimitType.PER_ROUND, maxUses);
    }

    /**
     * Crée une limite par partie
     */
    public static UsageLimit perGame(int maxUses) {
        return new UsageLimit(UsageLimitType.PER_GAME, maxUses);
    }

    /**
     * Capacité illimitée
     */
    public static UsageLimit unlimited() {
        return new UsageLimit(null, -1);
    }

    public boolean isUnlimited() {
        return maxUses == -1 || type == null;
    }

    public UsageLimitType getType() {
        return type;
    }

    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public String toString() {
        if (isUnlimited()) {
            return "Unlimited";
        }
        return maxUses + "/" + type;
    }



}
