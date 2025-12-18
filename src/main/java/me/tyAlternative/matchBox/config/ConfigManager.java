package me.tyalternative.matchbox.config;

import me.tyalternative.matchbox.MatchBox;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de configuration
 * Charge les paramètres et les expose via GameSettings
 */
public class ConfigManager {

    private final MatchBox plugin;
    private final GameSettings settings;

    public ConfigManager(MatchBox plugin) {
        this.plugin = plugin;
        this.settings = new GameSettings();
        loadSettings();
    }

    /**
     * Charge tous les paramètres depuis config.yml
     */
    public void loadSettings() {
        FileConfiguration config = plugin.getConfig();

        // Général
        settings.setLanguage(config.getString("general.language", "fr"));
        settings.setDebug(config.getBoolean("general.debug", false));
        settings.setWorldName(config.getString("general.world", "world"));

        // Phases
        settings.setGameplayDuration(config.getInt("phases.gameplay.duration", 360));
        settings.setMinGameplayDuration(config.getInt("phases.gameplay.min_duration", 300));
        settings.setMaxGameplayDuration(config.getInt("phases.gameplay.max_duration", 600));
        settings.setVoteDuration(config.getInt("phases.vote.duration", 120));
        settings.setDiscussionTime(config.getInt("phases.vote.discussion_time", 60));

        // Mécaniques - Flèches
        settings.setDefaultSpectralArrows(config.getInt("mechanics.spectral_arrows.default_count", 1));
        settings.setRevealDuration(config.getInt("mechanics.spectral_arrows.reveal_duration", -1));

        // Mécaniques - Panneaux
        settings.setDefaultSignCount(config.getInt("mechanics.signs.default_count", 16));
        settings.setRefillSigns(config.getBoolean("mechanics.signs.refill_each_phase", true));

        // Mécaniques - Anonymat
        settings.setHideSkins(config.getBoolean("mechanics.anonymity.hide_skins", true));
        settings.setHideNametags(config.getBoolean("mechanics.anonymity.hide_nametags", true));
        settings.setDefaultSkin(config.getString("mechanics.anonymity.default_skin", "steve"));

        // Mécaniques - Vote
        settings.setVoteInteractionRange(config.getDouble("mechanics.vote.interaction_range", 100.0));
        settings.setShowVoteCount(config.getBoolean("mechanics.vote.show_vote_count", false));
        settings.setRandomOnTie(config.getBoolean("mechanics.vote.random_on_tie", true));

        // Mécaniques - Élimination
        settings.setRevealRoleOnDeath(config.getBoolean("mechanics.elimination.reveal_role_on_death", false));
        settings.setTeleportSpectators(config.getBoolean("mechanics.elimination.teleport_spectators", false));

        double specX = config.getDouble("mechanics.elimination.spectator_location.x", 0);
        double specY = config.getDouble("mechanics.elimination.spectator_location.y", 100);
        double specZ = config.getDouble("mechanics.elimination.spectator_location.z", 0);
        settings.setSpectatorLocation(new Location(
                plugin.getServer().getWorld(settings.getWorldName()),
                specX, specY, specZ
        ));

        // Tables de vote
        List<Location> voteTables = new ArrayList<>();
        for (String raw : config.getStringList("vote_tables")) {
            try {
                String[] parts = raw.replace(" ", "").split(",");
                if (parts.length == 3) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    double z = Double.parseDouble(parts[2]);
                    voteTables.add(new Location(
                            plugin.getServer().getWorld(settings.getWorldName()),
                            x, y, z
                    ));
                }
            } catch (NumberFormatException e) {
                plugin.getLogger().warning("Position de table invalide: " + raw);
            }
        }
        settings.setVoteTableLocations(voteTables);

        // Cooldowns
        settings.setPoudreChemineeCooldown(config.getInt("cooldowns.poudre_cheminee", 20));
        settings.setRayonnementToggleCooldown(config.getInt("cooldowns.rayonnement_toggle", 0));
        settings.setFlammeJumelleCooldown(config.getInt("cooldowns.flamme_jumelle", 120));
        settings.setFlammeJumelleDuration(config.getInt("cooldowns.flamme_jumelle_duration", 5));

        // Abilities
        settings.setDoubleSwapEnable(config.getBoolean("abilities.double_swap_detection.enabled", true));
        settings.setDoubleSwapMaxDelayMs(config.getInt("abilities.double_swap_detection.max_delay_ms", 500));
        settings.setDoubleSwapCancelFirstSwap(config.getBoolean("abilities.double_swap_detection.cancel_first_swap", true));

        settings.setAmnesiaRolePickChoices(config.getStringList("abilities.amnesia.targets"));

        // Messages
        settings.setPrefix(config.getString("messages.prefix", "§e[Boite d'Allumettes]"));

        // UI
        settings.setBossBarEnabled(config.getBoolean("ui.bossbar.enabled", true));
        settings.setBossBarColorGameplay(config.getString("ui.bossbar.color_gameplay", "BLUE"));
        settings.setBossBarColorVote(config.getString("ui.bossbar.color_vote", "PINK"));
        settings.setActionBarEnabled(config.getBoolean("ui.actionbar.enabled", true));
        settings.setShowCooldowns(config.getBoolean("ui.actionbar.show_cooldowns", true));
        settings.setSoundsEnabled(config.getBoolean("ui.sounds.enabled", true));

        // Performance
        settings.setDisplayUpdateInterval(config.getInt("performance.display_update_interval", 20));
        settings.setCooldownCheckInterval(config.getInt("performance.cooldown_check_interval", 10));
    }

    /**
     * Recharge la configuration
     */
    public void reload() {
        plugin.reloadConfig();
        loadSettings();
        plugin.getLogger().info("Configuration rechargée.");
    }

    /**
     * Obtient un son depuis la config
     */
    public String getSound(String key) {
        return plugin.getConfig().getString("ui.sounds." + key, "");
    }

    /**
     * Retourne l'objet settings
     */
    public GameSettings getSettings() {
        return settings;
    }

}
