package me.tyalternative.matchbox.mechanics.anonymity;
import me.tyalternative.matchbox.MatchBox;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestionnaire des nametags personnalisés
 */
public class AnonymityManager {

    private final GameManager gameManager;

    private final List<UUID> hiddenSkins;

    // Maps pour stocker les configurations
    private final Map<UUID, Map<UUID, NameTag>> nameTagConfigs;

    // Map pour stocker les TextDisplay entities : ViewerUUID -> TargetUUID -> TextDisplay
    private final Map<UUID, Map<UUID, TextDisplay>> displayEntities;

    public AnonymityManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.hiddenSkins = new ArrayList<>();
        this.nameTagConfigs = new ConcurrentHashMap<>();
        this.displayEntities = new ConcurrentHashMap<>();

    }



    /**
     * Configuration d'un nametag
     */

    private static class NameTag {
        String customName;
        boolean hidden;
        boolean seeTrough;

        NameTag(String customName, boolean hidden) {
            this.customName = customName;
            this.hidden = hidden;
            this.seeTrough = true;
        }
        NameTag(String customName, boolean hidden, boolean seeTrough) {
            this.customName = customName;
            this.hidden = hidden;
            this.seeTrough = seeTrough;
        }
    }



    public void hide(Player viewer, Player target) {
        if (viewer == null || target == null) return;

        // Stocker la configuration de masquage
        nameTagConfigs.computeIfAbsent(viewer.getUniqueId(), key -> new ConcurrentHashMap<>())
                .put(target.getUniqueId(), new NameTag("", true));

        // Supprimer complètement l'entité
        removeDisplayEntity(viewer, target);

    }


    public void reset(Player viewer, Player target) {
        if (viewer == null || target == null) return;

        // Supprimer la configuration
        Map<UUID, NameTag> viewerConfigs = nameTagConfigs.get(viewer.getUniqueId());
        if (viewerConfigs != null) {
            viewerConfigs.remove(target.getUniqueId());
        }

        // Supprimer l'entity personnalisée
        removeDisplayEntity(viewer, target);

        // Créer une entity avec le nom original
        String originalName = target.getName();
        createDisplayEntity(viewer, target, originalName);

    }

    public void set(Player viewer, Player target, String customName) {
        if (viewer == null || target == null || customName == null) return;


        // Supprimer l'ancienne entity s'il en existe une
        removeDisplayEntity(viewer, target);

        // Stocker la configuration
        nameTagConfigs.computeIfAbsent(viewer.getUniqueId(), k -> new ConcurrentHashMap<>())
                .put(target.getUniqueId(), new NameTag(customName, false));

        // Créer la nouvelle TextDisplay entity
        createDisplayEntity(viewer, target, customName);

    }

    /**
     * Crée une TextDisplay entity qui "ride" sur le joueur target
     */
    private void createDisplayEntity(Player viewer, Player target, String displayText) {
        try {

            // Position initiale au-dessus de la tête du joueur
            Location spawnLoc = target.getLocation().add(0, target.getHeight() + 0.3, 0);


            // Créer la TextDisplay entity
            TextDisplay textDisplay = target.getWorld().spawn(spawnLoc, TextDisplay.class, entity -> {
                // Configuration de l'entity AVANT le spawn

                // 1. Texte à afficher (support couleurs)
                Component textComponent = LegacyComponentSerializer.legacySection().deserialize(displayText);
                entity.text(textComponent);

                // 2. Configuration d'affichage optimisée pour le riding
                entity.setBillboard(Display.Billboard.CENTER); // Centré et face au joueur
                entity.setSeeThrough(false);                    // Transparence à travers les blocs
                entity.setDefaultBackground(false);             // Pas de fond par défaut
                entity.setShadowed(false);                      // Ombre pour meilleure lisibilité

                // 3. Échelle optimisée pour le riding (légèrement plus petite)
                Transformation transformation = new Transformation(
                        new Vector3f(0, 0.3f, 0),         // Translation légèrement vers le haut
                        new AxisAngle4f(0, 0, 0, 1),      // Pas de rotation gauche
                        new Vector3f(1.0f, 1.0f, 1.0f),   // Échelle réduite (80%)
                        new AxisAngle4f(0, 0, 0, 1)       // Pas de rotation droite
                );
                entity.setTransformation(transformation);

                // 4. Propriétés d'entité optimisées pour riding
                entity.setGravity(false);          // Pas de gravité (important pour riding)
                entity.setInvulnerable(true);      // Indestructible
                entity.setSilent(true);            // Silencieux
                entity.setPersistent(false);       // Ne persiste pas après redémarrage
                entity.setVisibleByDefault(false); // INVISIBLE PAR DÉFAUT

                // 5. Métadonnées pour identification
                entity.customName(Component.text("riding_nametag_" + target.getName() + "_for_" + viewer.getName()));
                entity.setCustomNameVisible(false);
            });


            // CRUCIAL : Rendre visible SEULEMENT pour le viewer spécifique
            textDisplay.setVisibleByDefault(false);
            viewer.showEntity(MatchBox.getInstance(), textDisplay);

            // Faire rider l'entity sur le joueur !
            target.addPassenger(textDisplay);

            // Stocker la référence
            displayEntities.computeIfAbsent(viewer.getUniqueId(), k -> new ConcurrentHashMap<>())
                    .put(target.getUniqueId(), textDisplay);

        } catch (Exception e) {
            MatchBox.getInstance().getLogger().warning("Erreur création TextDisplay riding: " + e.getMessage());

        }
    }

    /**
     * Supprime une TextDisplay entity qui ride sur un joueur
     */
    private void removeDisplayEntity(Player viewer, Player target) {
        Map<UUID, TextDisplay> viewerEntities = displayEntities.get(viewer.getUniqueId());
        if (viewerEntities == null) return;

        TextDisplay entity = viewerEntities.remove(target.getUniqueId());
        if (entity == null || !entity.isValid()) return;

        try {
            // Faire descendre l'entity du joueur AVANT de la supprimer
            if (target.isOnline() && target.getPassengers().contains(entity)) {
                target.removePassenger(entity);
            }

            // Cacher l'entity du viewer
            if (viewer.isOnline()) {
                viewer.hideEntity(MatchBox.getInstance(), entity);
            }

            // Puis la supprimer complètement
            entity.remove();

        } catch (Exception e) {
            MatchBox.getInstance().getLogger().warning("Erreur lors de la suppression du TextDisplay de " + target.getName() + ": " + e.getMessage());
        }

    }

    /**
     * Les entities riding suivent automatiquement le joueur
     */
    private void startPositionUpdateTask() {

        // Task allégée pour nettoyer les entities invalides
        Bukkit.getScheduler().runTaskTimer(MatchBox.getInstance(), () -> {
            for (UUID viewerUuid : displayEntities.keySet()) {
                Player viewer = Bukkit.getPlayer(viewerUuid);
                if (viewer == null || !viewer.isOnline()) continue;

                Map<UUID, TextDisplay> viewerEntities = displayEntities.get(viewerUuid);
                Iterator<Map.Entry<UUID, TextDisplay>> iterator = viewerEntities.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<UUID, TextDisplay> entry = iterator.next();
                    Player target = Bukkit.getPlayer(entry.getKey());
                    TextDisplay entity = entry.getValue();

                    if (target == null || !target.isOnline() || entity == null || !entity.isValid()) {
                        // Nettoyer les entities invalides
                        if (entity != null && entity.isValid()) {
                            // Faire descendre l'entity avant suppression
                            if (target != null && target.isOnline()) {
                                target.removePassenger(entity);
                            }
                            entity.remove();
                        }
                        iterator.remove();
                        continue;
                    }

                    // Vérifier que l'entity est toujours en train de rider le target
                    if (!target.getPassengers().contains(entity)) {
                        // Re-faire rider l'entity si elle n'est plus sur le joueur
                        try {
                            target.addPassenger(entity);
                        } catch (Exception e) {
                            MatchBox.getInstance().getLogger().warning("Impossible de re-faire rider l'entity: " + e.getMessage());
                            // Si ça échoue, supprimer l'entity
                            entity.remove();
                            iterator.remove();
                        }
                    }
                }
            }
        }, 20L, 20L); // Toutes les secondes (plus besoin de fréquence élevée)
    }

    /**
     * Vérifie et fix les entities riding après un changement de monde
     */
    private void checkAndFixRidingEntities(Player target) {
        // Vérifie si toutes les entités qui devraient rider ce target le sont.
        for (Map<UUID, TextDisplay> viewerEntities : displayEntities.values()) {
            TextDisplay entity = viewerEntities.get(target.getUniqueId());
            if (entity != null && entity.isValid()) {
                // Si l'entity n'est plus sur le joueur, la remettre
                if (!target.getPassengers().contains(entity)) {
                    try {
                        target.addPassenger(entity);
                    } catch (Exception e) {
                        MatchBox.getInstance().getLogger().warning("Impossible de remettre l'entity riding après changement de monde: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Nettoyage complet d'un joueur avec gestion du riding
     */
    public void cleanupPlayer(Player player) {
        UUID playerId = player.getUniqueId();


        // Nettoyer en tant que viewer (supprimer toutes ses TextDisplay entities)
        Map<UUID, TextDisplay> viewerEntities = displayEntities.remove(playerId);
        if (viewerEntities != null) {

            for (Map.Entry<UUID, TextDisplay> entry : viewerEntities.entrySet()) {
                TextDisplay entity = entry.getValue();

                if (entity != null && entity.isValid()) {
                    Player target = Bukkit.getPlayer(entry.getKey());
                    if (target != null && target.isOnline() && target.getPassengers().contains(entity)) {
                        target.removePassenger(entity);
                    }
                    entity.remove();
                }
            }
        }

        // Nettoyer les configurations
        nameTagConfigs.remove(playerId);

        // Nettoyer en tant que target (faire descendre toutes les entities qui ridaient sur lui)

        if (player.isOnline()) {
            List<TextDisplay> ridingEntities = new ArrayList<>();
            for (Entity passenger : player.getPassengers()) {
                if (passenger instanceof TextDisplay textDisplay) {
                    // Vérifier si c'est une de nos entities nametag
                    textDisplay.getName();
                    if (textDisplay.getName().contains("riding_nametag_" + player.getName())) {
                        ridingEntities.add(textDisplay);
                    }
                }
            }

            // Faire descendre et supprimer toutes nos entities
            for (TextDisplay entity : ridingEntities) {
                player.removePassenger(entity);
                entity.remove();
            }
        }

        // Nettoyer les références dans les autres maps
        for (Map<UUID, TextDisplay> otherViewerEntities : displayEntities.values()) {
            TextDisplay entityToRemove = otherViewerEntities.remove(playerId);
            if (entityToRemove != null && entityToRemove.isValid()) {
                entityToRemove.remove();
            }
        }

        // Nettoyer les configurations où ce joueur est target
        for (Map<UUID, NameTag> viewerConfigs : nameTagConfigs.values()) {
            viewerConfigs.remove(playerId);
        }

    }

    public void hidePlayersDefaultNametag(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("hidden");
        if (team == null) {
            team = scoreboard.registerNewTeam("hidden");
        }
        if (team.hasPlayer(player)) return;
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.addEntry(player.getName());


    }
    public void showPlayersDefaultNametag(Player player) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam("hidden");
        if (team == null) {
            team = scoreboard.registerNewTeam("hidden");
        }
        team.removeEntry(player.getName());

    }
    public void hideAllNametags() {
        for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
            hidePlayersDefaultNametag(player);
            for (Player viewer : gameManager.getPlayerManager().getAlivePlayer()) {
                hide(viewer, player);
            }
        }
    }
    public void showAllNametags() {
        for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
//            hidePlayersDefaultNametag(player);
            for (Player viewer : gameManager.getPlayerManager().getAlivePlayer()) {
                reset(viewer, player);
            }
        }
    }

    public void showToAllNametag(Player target, String customName) {
        for (Player viewer : gameManager.getPlayerManager().getAlivePlayer()) {
            set(viewer, target, customName);
        }
    }

    public void showRealName(Player viewer, Player target) {
        reset(viewer, target);
    }


    public void hideAllPlayersNametag() {
        for (PlayerData playerData : gameManager.getPlayerManager().getAll()) {
            Player viewer = playerData.getPlayer();
            if (viewer == null) continue;

            hidePlayersDefaultNametag(viewer);

            for (Player target : gameManager.getPlayerManager().getAlivePlayer()) {
                hide(viewer, target);
            }

        }
    }

    public void showAllPlayersNametag() {
        for (PlayerData playerData : gameManager.getPlayerManager().getAll()) {
            Player viewer = playerData.getPlayer();
            if (viewer == null) continue;

            showPlayersDefaultNametag(viewer);

            for (Player target : gameManager.getPlayerManager().getAlivePlayer()) {
                hide(viewer, target);
            }

        }
    }












    public void hideSkin(Player player, String skin) {
        if (hiddenSkins.contains(player.getUniqueId())) return;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "skin set " + skin + " " + player.getName());

        hiddenSkins.add(player.getUniqueId());
    }

    public void showSkin(Player player) {
        if (!hiddenSkins.contains(player.getUniqueId())) return;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "skin clear " + player.getName());

        hiddenSkins.remove(player.getUniqueId());
    }

    public void hideAllSkins() {
        List<Player> players = gameManager.getPlayerManager().getAlivePlayer();
        String skinName = gameManager.getSettings().getDefaultSkin();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "skin set " + skinName + " -all");
        for (Player player : players) {
            hideSkin(player, skinName);
        }
    }

    public void showAllSkins() {
        for (Player player : gameManager.getPlayerManager().getAlivePlayer()) {
            showSkin(player);
        }
    }


    public void restoreAll() {
        showAllSkins();
        showAllPlayersNametag();
    }




}
