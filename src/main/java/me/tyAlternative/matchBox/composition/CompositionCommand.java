package me.tyalternative.matchbox.composition;

import me.tyalternative.matchbox.composition.CompositionGUI;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Commande de gestion de la composition
 */
public class CompositionCommand implements CommandExecutor, TabCompleter {

    private final GameManager gameManager;
    private final CompositionGUI gui;

    public CompositionCommand(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gui = new CompositionGUI(gameManager);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd,
                             @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cCommande réservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("matchbox.composition")) {
            player.sendMessage("§cPermission manquante.");
            return true;
        }

        // Aucun argument : ouvrir le GUI
        if (args.length == 0) {
            gui.open(player);
            return true;
        }

        // Sous-commandes
        switch (args[0].toLowerCase()) {
            case "display", "show", "list" -> handleDisplay(player);
            default -> gui.open(player);
        }
        return true;
    }


    /**
     * Affiche la composition actuelle
     */
    private void handleDisplay(Player player) {
        player.sendMessage("§8========== §eComposition §8==========");

        Map<Role, Integer> composition = gameManager.getCompositionManager().getComposition();

        if (composition.isEmpty()) {
            player.sendMessage("§7Aucun rôle défini.");
            player.sendMessage("§7Utilisez §e/compo §7pour ouvrir le GUI.");
        } else {
            for (Map.Entry<Role, Integer> entry : composition.entrySet()) {
                player.sendMessage("§7 - §e" + entry.getValue() + "x §r" + entry.getKey().getDisplayName());
            }
            player.sendMessage("");
            player.sendMessage("§7Total : §e" + gameManager.getCompositionManager().getTotalRoles() + " §7joueurs");
        }

        player.sendMessage("§8" + "=".repeat(35));
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
                                                @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return filterMatching(args[0], Arrays.asList("display", "show", "list"));
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


    /**
     * Retourne le GUI pour enregistrement du listener
     */
    public CompositionGUI getGui() {
        return gui;
    }

}
