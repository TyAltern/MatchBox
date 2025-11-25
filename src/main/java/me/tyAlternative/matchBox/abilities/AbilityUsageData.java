package me.tyalternative.matchbox.abilities;

/**
 * Données d'utilisation d'une capacité pour un joueur
 */
public class AbilityUsageData {

    private int usesThisRound;
    private int usesThisGame;

    public AbilityUsageData() {
        this.usesThisRound = 0;
        this.usesThisGame = 0;
    }

    public void recordUse() {
        usesThisRound++;
        usesThisGame++;
    }

    public void resetRound() {
        usesThisRound = 0;
    }
    public void resetGame() {
        usesThisRound = 0;
        usesThisGame = 0;
    }

    public void setUsesThisRound(int uses) {
        usesThisRound = uses;
    }
    public void setUsesThisGame(int uses) {
        usesThisGame = uses;
    }

    public void addUsesThisRound(int uses) {
        usesThisRound += uses;
    }
    public void addUsesThisGame(int uses) {
        usesThisGame += uses;
    }

    public void removeUsesThisRound(int uses) {
        usesThisRound -= uses;
    }
    public void removeUsesThisGame(int uses) {
        usesThisGame -= uses;
    }

    public int getUsesThisRound() {
        return usesThisRound;
    }
    public int getUsesThisGame() {
        return usesThisGame;
    }
}
