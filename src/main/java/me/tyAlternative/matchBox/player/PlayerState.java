package me.tyalternative.matchbox.player;


/**
 * États possibles d'un joueur dans le jeu
 */
public enum  PlayerState {
    /**
     * En attente dans le lobby
     */
    LOBBY,

    /**
     * En jeu et vivant
     */
    PLAYING,

    /**
     * Mort, en mode spectateur
     */
    DEAD,

    /**
     * Déconnecté
     */
    DISCONNECTED;

    public boolean isAlive() {
        return this == PLAYING;
    }

    public boolean isInGame() {
        return this == PLAYING || this == DEAD;
    }
}
