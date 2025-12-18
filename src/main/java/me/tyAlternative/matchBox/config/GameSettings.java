package me.tyalternative.matchbox.config;
import org.bukkit.Location;

import java.util.List;

/**
 * POJO contenant tous les paramètres de configuration
 */
public class GameSettings {

    // Général
    private String language;
    private boolean debug;
    private String worldName;

    // Phases
    private int gameplayDuration;
    private int minGameplayDuration;
    private int maxGameplayDuration;
    private int voteDuration;
    private int discussionTime;

    // Mécaniques - Flèches
    private int defaultSpectralArrows;
    private int revealDuration;

    // Mécaniques - Panneaux
    private int defaultSignCount;
    private boolean refillSigns;

    // Mécaniques - Anonymat
    private boolean hideSkins;
    private boolean hideNametags;
    private String defaultSkin;

    // Mécaniques - Vote
    private double voteInteractionRange;
    private boolean showVoteCount;
    private boolean randomOnTie;

    // Mécaniques - Élimination
    private boolean revealRoleOnDeath;
    private boolean teleportSpectators;
    private Location spectatorLocation;

    // Tables de vote
    private List<Location> voteTableLocations;

    // Cooldowns
    private int poudreChemineeCooldown;
    private int rayonnementToggleCooldown;
    private int flammeJumelleCooldown;
    private int flammeJumelleDuration;

    // Abilities
    private boolean doubleSwapEnable;
    private int doubleSwapMaxDelayMs;
    private boolean doubleSwapCancelFirstSwap;

    private List<String> amnesiaRolePickChoices;

    // Messages
    private String prefix;

    // UI
    private boolean bossBarEnabled;
    private String bossBarColorGameplay;
    private String bossBarColorVote;
    private boolean actionBarEnabled;
    private boolean showCooldowns;
    private boolean soundsEnabled;

    // Performance
    private int displayUpdateInterval;
    private int cooldownCheckInterval;

    // ========== GETTERS ==========

    public String getLanguage() { return language; }
    public boolean isDebug() { return debug; }
    public String getWorldName() { return worldName; }

    public int getGameplayDuration() { return gameplayDuration; }
    public int getMinGameplayDuration() { return minGameplayDuration; }
    public int getMaxGameplayDuration() { return maxGameplayDuration; }
    public int getVoteDuration() { return voteDuration; }
    public int getDiscussionTime() { return discussionTime; }

    public int getDefaultSpectralArrows() { return defaultSpectralArrows; }
    public int getRevealDuration() { return revealDuration; }

    public int getDefaultSignCount() { return defaultSignCount; }
    public boolean shouldRefillSigns() { return refillSigns; }

    public boolean shouldHideSkins() { return hideSkins; }
    public boolean shouldHideNametags() { return hideNametags; }
    public String getDefaultSkin() { return defaultSkin; }

    public double getVoteInteractionRange() { return voteInteractionRange; }
    public boolean shouldShowVoteCount() { return showVoteCount; }
    public boolean shouldRandomOnTie() { return randomOnTie; }

    public boolean shouldRevealRoleOnDeath() { return revealRoleOnDeath; }
    public boolean shouldTeleportSpectators() { return teleportSpectators; }
    public Location getSpectatorLocation() { return spectatorLocation; }

    public List<Location> getVoteTableLocations() { return voteTableLocations; }

    public int getPoudreChemineeCooldown() { return poudreChemineeCooldown; }
    public int getRayonnementToggleCooldown() { return rayonnementToggleCooldown; }
    public int getFlammeJumelleCooldown() { return flammeJumelleCooldown; }
    public int getFlammeJumelleDuration() { return flammeJumelleDuration; }

    public boolean getDoubleSwapEnable() { return doubleSwapEnable; }
    public int getDoubleSwapMaxDelayMs() { return doubleSwapMaxDelayMs; }
    public boolean getDoubleSwapCancelFirstSwap() { return doubleSwapCancelFirstSwap; }

    public List<String> getAmnesiaRolePickChoices() {return amnesiaRolePickChoices; }

    public String getPrefix() { return prefix; }

    public boolean isBossBarEnabled() { return bossBarEnabled; }
    public String getBossBarColorGameplay() { return bossBarColorGameplay; }
    public String getBossBarColorVote() { return bossBarColorVote; }
    public boolean isActionBarEnabled() { return actionBarEnabled; }
    public boolean shouldShowCooldowns() { return showCooldowns; }
    public boolean areSoundsEnabled() { return soundsEnabled; }

    public int getDisplayUpdateInterval() { return displayUpdateInterval; }
    public int getCooldownCheckInterval() { return cooldownCheckInterval; }

    // ========== SETTERS ==========

    public void setLanguage(String language) { this.language = language; }
    public void setDebug(boolean debug) { this.debug = debug; }
    public void setWorldName(String worldName) { this.worldName = worldName; }

    public void setGameplayDuration(int gameplayDuration) { this.gameplayDuration = gameplayDuration; }
    public void setMinGameplayDuration(int minGameplayDuration) { this.minGameplayDuration = minGameplayDuration; }
    public void setMaxGameplayDuration(int maxGameplayDuration) { this.maxGameplayDuration = maxGameplayDuration; }
    public void setVoteDuration(int voteDuration) { this.voteDuration = voteDuration; }
    public void setDiscussionTime(int discussionTime) { this.discussionTime = discussionTime; }

    public void setDefaultSpectralArrows(int defaultSpectralArrows) { this.defaultSpectralArrows = defaultSpectralArrows; }
    public void setRevealDuration(int revealDuration) { this.revealDuration = revealDuration; }

    public void setDefaultSignCount(int defaultSignCount) { this.defaultSignCount = defaultSignCount; }
    public void setRefillSigns(boolean refillSigns) { this.refillSigns = refillSigns; }

    public void setHideSkins(boolean hideSkins) { this.hideSkins = hideSkins; }
    public void setHideNametags(boolean hideNametags) { this.hideNametags = hideNametags; }
    public void setDefaultSkin(String defaultSkin) { this.defaultSkin = defaultSkin; }

    public void setVoteInteractionRange(double voteInteractionRange) { this.voteInteractionRange = voteInteractionRange; }
    public void setShowVoteCount(boolean showVoteCount) { this.showVoteCount = showVoteCount; }
    public void setRandomOnTie(boolean randomOnTie) { this.randomOnTie = randomOnTie; }

    public void setRevealRoleOnDeath(boolean revealRoleOnDeath) { this.revealRoleOnDeath = revealRoleOnDeath; }
    public void setTeleportSpectators(boolean teleportSpectators) { this.teleportSpectators = teleportSpectators; }
    public void setSpectatorLocation(Location spectatorLocation) { this.spectatorLocation = spectatorLocation; }

    public void setVoteTableLocations(List<Location> voteTableLocations) { this.voteTableLocations = voteTableLocations; }

    public void setPoudreChemineeCooldown(int poudreChemineeCooldown) { this.poudreChemineeCooldown = poudreChemineeCooldown; }
    public void setRayonnementToggleCooldown(int rayonnementToggleCooldown) { this.rayonnementToggleCooldown = rayonnementToggleCooldown; }
    public void setFlammeJumelleCooldown(int flammeJumelleCooldown) { this.flammeJumelleCooldown = flammeJumelleCooldown; }
    public void setFlammeJumelleDuration(int flammeJumelleDuration) { this.flammeJumelleDuration = flammeJumelleDuration; }


    public void setDoubleSwapEnable(boolean doubleSwapEnable) { this.doubleSwapEnable = doubleSwapEnable; }
    public void setDoubleSwapMaxDelayMs(int doubleSwapMaxDelayMs) { this.doubleSwapMaxDelayMs = doubleSwapMaxDelayMs; }
    public void setDoubleSwapCancelFirstSwap(boolean doubleSwapCancelFirstSwap) { this.doubleSwapCancelFirstSwap = doubleSwapCancelFirstSwap; }

    public void setAmnesiaRolePickChoices(List<String> amnesiaRolePickChoices) {this.amnesiaRolePickChoices = amnesiaRolePickChoices; }


    public void setPrefix(String prefix) { this.prefix = prefix; }

    public void setBossBarEnabled(boolean bossBarEnabled) { this.bossBarEnabled = bossBarEnabled; }
    public void setBossBarColorGameplay(String bossBarColorGameplay) { this.bossBarColorGameplay = bossBarColorGameplay; }
    public void setBossBarColorVote(String bossBarColorVote) { this.bossBarColorVote = bossBarColorVote; }
    public void setActionBarEnabled(boolean actionBarEnabled) { this.actionBarEnabled = actionBarEnabled; }
    public void setShowCooldowns(boolean showCooldowns) { this.showCooldowns = showCooldowns; }
    public void setSoundsEnabled(boolean soundsEnabled) { this.soundsEnabled = soundsEnabled; }

    public void setDisplayUpdateInterval(int displayUpdateInterval) { this.displayUpdateInterval = displayUpdateInterval; }
    public void setCooldownCheckInterval(int cooldownCheckInterval) { this.cooldownCheckInterval = cooldownCheckInterval; }


}
