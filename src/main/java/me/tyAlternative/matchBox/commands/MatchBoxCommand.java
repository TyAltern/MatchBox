package me.tyalternative.matchbox.commands;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.player.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchBoxCommand implements CommandExecutor, TabCompleter {
    private final GameManager gameManager;

    public MatchBoxCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd,
                             @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cCommande réservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("matchbox.admin")) {
            player.sendMessage("§cPermission manquante.");
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start" -> handleStart(player);
            case "stop" -> handleStop(player);
            case "skip" -> handleSkip(player);
            case "role" -> handleRole(player, args);
            case "debug" -> handleDebug(player);
            case "reload" -> handleReload(player);
            default -> sendHelp(player);
        }

        return true;

    }

    private void handleStart(Player player) {
        if (gameManager.isGameRunning()) {
            player.sendMessage("§cPartie déjà en cours !");
            return;
        }

        if (gameManager.startGame()) {
            player.sendMessage("§a✓ Partie démarrée !");
        } else {
            player.sendMessage("§c✗ Impossible de démarrer.");
        }
    }

    private void handleStop(Player player) {
        if (!gameManager.isGameRunning()) {
            player.sendMessage("§cAucune partie en cours.");
            return;
        }

        gameManager.stopGame("Arrêt manuel");
        player.sendMessage("§a✓ Partie arrêtée.");
    }


    private void handleSkip(Player player) {
        if (!gameManager.isGameRunning()) {
            player.sendMessage("§cAucune partie en cours.");
            return;
        }

        gameManager.getPhaseManager().skipPhase();
        player.sendMessage("§a✓ Phase passée.");
    }


    private void handleRole(Player player, String[] args) {
        if (args.length < 2) {
            PlayerData data = gameManager.getPlayerManager().get(player);
            if (data != null && data.hasRole()) {
                player.sendMessage("§7Votre rôle : " + data.getRole().getDisplayName());
            } else {
                player.sendMessage("§cVous n'avez pas de rôle.");
            }
            return;
        }


        Player target = player.getServer().getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§cJoueur introuvable.");
            return;
        }

        PlayerData data = gameManager.getPlayerManager().get(target);
        if (data != null && data.hasRole()) {
            player.sendMessage("§7Rôle de §e" + target.getName() + " §7: " + data.getRole().getDisplayName());
        } else {
            player.sendMessage("§cCe joueur n'a pas de rôle.");
        }
    }

    private void handleDebug(Player player) {
        player.sendMessage("§8========== DEBUG ==========");
        player.sendMessage("§7Partie : " + (gameManager.isGameRunning() ? "§aOui" : "§cNon"));
        player.sendMessage("§7Phase : §e" + gameManager.getCurrentPhase());
        player.sendMessage("§7Manche : §e" + gameManager.getRoundNumber());
        player.sendMessage("§7Vivants : §e" + gameManager.getPlayerManager().getAliveCount());
        player.sendMessage("§8===========================");
    }

    private void handleReload(Player player) {
        gameManager.getPlugin().getConfigManager().reload();
        player.sendMessage("§a✓ Configuration rechargée !");
    }

    private void sendHelp(Player player) {
        player.sendMessage("§8========== MatchBox ==========");
        player.sendMessage("§e/matchbox start §7- Démarrer");
        player.sendMessage("§e/matchbox stop §7- Arrêter");
        player.sendMessage("§e/matchbox skip §7- Passer phase");
        player.sendMessage("§e/matchbox role [joueur] §7- Voir rôle");
        player.sendMessage("§e/matchbox debug §7- Debug");
        player.sendMessage("§e/matchbox reload §7- Recharger config");
        player.sendMessage("§8==============================");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
                                                @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 1) {
            return filterMatching(args[0], Arrays.asList("start", "stop", "skip", "role", "debug", "reload"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("role")) {
            return new ArrayList<>(); //TODO: Liste des joueurs
        }
        return new ArrayList<>();
    }

    private List<String> filterMatching(String input, List<String> options) {
        List<String> result = new ArrayList<>();
        String lower = input.toLowerCase();
        for (String option : options) {
            if (option.toLowerCase().startsWith(lower)) {
                result.add(option);
            }
        }

        return result;
    }
}
