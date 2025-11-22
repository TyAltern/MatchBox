package me.tyalternative.matchbox.config;

import me.tyalternative.matchbox.MatchBox;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;


/**
 * Gestionnaire de configuration centralisé
 */
public class ConfigManager {

    private final MatchBox plugin;
    private final FileConfiguration config;

    public ConfigManager(MatchBox plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }



    // ========== GÉNÉRAL ==========

    public boolean isDebugEnabled() {
        return config.getBoolean("general.debug", false);
    }

    public String getLanguage() {
        return config.getString("general.language", "fr");
    }

    public String getWorldName() {
        return config.getString("general.world", "world");
    }


    // ========== PHASES ==========

    public int getGameplayDuration() {
        return config.getInt("phases.gameplay.duration", 360);
    }

    public int getMinGameplayDuration() {
        return config.getInt("phases.gameplay.min_duration", 300);
    }

    public int getMaxGameplayDuration() {
        return config.getInt("phases.gameplay.max_duration", 600);
    }

    public int getVoteDuration() {
        return config.getInt("phases.vote.duration", 120);
    }

    public int getDiscussionTime() {
        return config.getInt("phases.vote.discussion_time", 60);
    }


    // ========== MÉCANIQUES ==========

    public int getDefaultSpectralArrows() {
        return config.getInt("mechanics.spectral_arrows.default_count", 1);
    }

    public int getRevealDuration() {
        return config.getInt("mechanics.spectral_arrows.reveal_duration", -1);
    }

    public int getDefaultSignCount() {
        return config.getInt("mechanics.signs.default_count", 16);
    }

    public boolean shouldRefillSigns() {
        return config.getBoolean("mechanics.signs.refill_each_phase", true);
    }

    public boolean shouldHideSkins() {
        return config.getBoolean("mechanics.anonymity.hide_skins", true);
    }

    public boolean shouldHideNametags() {
        return config.getBoolean("mechanics.anonymity.hide_nametags", true);
    }

    public String getDefaultSkin() {
        return config.getString("mechanics.anonymity.default_skin", "steve");
    }

    public double getVoteInteractionRange() {
        return config.getDouble("mechanics.vote.interaction_range", 100.0);
    }

    public boolean shouldShowVoteCount() {
        return config.getBoolean("mechanics.vote.show_vote_count", false);
    }

    public boolean shouldRandomOnTie() {
        return config.getBoolean("mechanics.vote.random_on_tie", true);
    }

    public boolean shouldRevealRoleOnDeath() {
        return config.getBoolean("mechanics.elimination.reveal_role_on_death", false);
    }

    public boolean shouldTeleportSpectators() {
        return config.getBoolean("mechanics.elimination.teleport_spectators", false);
    }

    public Location getSpectatorLocation() {
        double x = config.getDouble("mechanics.elimination.spectator_location.x", 0);
        double y = config.getDouble("mechanics.elimination.spectator_location.y", 100);
        double z = config.getDouble("mechanics.elimination.spectator_location.z", 0);
        return new Location(plugin.getServer().getWorld(getWorldName()), x, y, z);
    }


    // ========== POSITIONS VOTE ==========

    public List<Location> getVoteTableLocations() {
        List<String> rawLocations = config.getStringList("vote_tables");
        List<Location> locations = new ArrayList<>();

        for (String raw : rawLocations) {
            try {
                String[] parts = raw.replace(" ", "").split(",");
                if (parts.length == 3) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    double z = Double.parseDouble(parts[2]);
                    locations.add(new Location(
                            plugin.getServer().getWorld(getWorldName()),
                            x, y, z
                    ));
                }
            } catch (NumberFormatException e) {
                plugin.getLogger().warning("Position de table invalide: " + raw);
            }
        }

        return locations;
    }


    // ========== COOLDOWNS ==========

    public int getPoudreChemineeCooldown() {
        return config.getInt("cooldowns.poudre_cheminee", 20);
    }

    public int getRayonnementToggleCooldown() {
        return config.getInt("cooldowns.rayonnement_toggle", 0);
    }

    public int getFlammeJumelleCooldown() {
        return config.getInt("cooldowns.flamme_jumelle", 120);
    }

    public int getFlammeJumelleDuration() {
        return config.getInt("cooldowns.flamme_jumelle_duration", 5);
    }


    // ========== MESSAGES ==========

    public String getPrefix() {
        return config.getString("messages.prefix", "§e[Boite d'Allumettes]");
    }

    public String getMessage(String path, String defaultValue) {
        return config.getString("messages." + path, defaultValue);
    }


    // ========== UI ==========

    public boolean isBossBarEnabled() {
        return config.getBoolean("ui.bossbar.enabled", true);
    }

    public String getBossBarColorGameplay() {
        return config.getString("ui.bossbar.color_gameplay", "BLUE");
    }

    public String getBossBarColorVote() {
        return config.getString("ui.bossbar.color_vote", "PINK");
    }

    public boolean isActionBarEnabled() {
        return config.getBoolean("ui.actionbar.enabled", true);
    }

    public boolean shouldShowCooldowns() {
        return config.getBoolean("ui.actionbar.show_cooldowns", true);
    }

    public boolean areSoundsEnabled() {
        return config.getBoolean("ui.sounds.enabled", true);
    }

    public String getSound(String type) {
        return config.getString("ui.sounds." + type, "");
    }


    // ========== PERFORMANCE ==========

    public int getDisplayUpdateInterval() {
        return config.getInt("performance.display_update_interval", 20);
    }

    public int getCooldownCheckInterval() {
        return config.getInt("performance.cooldown_check_interval", 10);
    }


    // ========== RELOAD ==========

    public void reload() {
        plugin.reloadConfig();
        plugin.getLogger().info("Configuration rechargée.");
    }
}
