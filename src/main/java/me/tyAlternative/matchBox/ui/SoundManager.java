package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.core.GameManager;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {
    private final GameManager gameManager;

    public SoundManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void playToAll(String soundKey) {
        if (!gameManager.getSettings().areSoundsEnabled()) return;

        String soundName = gameManager.getPlugin().getConfigManager().getSound(soundKey);
        if (soundName == null || soundName.isEmpty()) {
            return;
        }

        try {
            NamespacedKey key = new NamespacedKey("minecraft", soundName.toLowerCase());
            Sound sound = Registry.SOUNDS.get(key);
            for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
                assert sound != null;
                player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
            }
        } catch (IllegalArgumentException e) {
            gameManager.getPlugin().getLogger().warning("Son invalide: " + soundName);
        }

    }

    public void play(Player player, String soundKey) {
        if (!gameManager.getSettings().areSoundsEnabled()) {
            return;
        }

        String soundName = gameManager.getPlugin().getConfigManager().getSound(soundKey);
        if (soundName == null || soundName.isEmpty()) {
            return;
        }

        try {
            NamespacedKey key = new NamespacedKey("minecraft", soundName.toLowerCase());
            Sound sound = Registry.SOUNDS.get(key);
            assert sound != null;
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
            gameManager.getPlugin().getLogger().warning("Son invalide: " + soundName);
        }
    }
}

