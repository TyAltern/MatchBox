package me.tyalternative.matchbox.composition;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.Role;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Gestionnaire des clics dans le GUI de composition
 */
public class CompositionClickHandler {
    private final GameManager gameManager;
    private final CompositionGUI gui;

    public CompositionClickHandler(GameManager gameManager, CompositionGUI gui) {
        this.gameManager = gameManager;
        this.gui = gui;
    }



    /**
     * Traite un clic dans le GUI
     */
    public void handleClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        int slot = event.getRawSlot();

        // Bouton reset
        if (slot == 49) {
            handleReset(player);
            return;
        }

        // Bouton infp (ne fait rien)
        if (slot == 53) {
            return;
        }

        // Ignorer bordures
        if (clicked.getType() == Material.GRAY_STAINED_GLASS_PANE) return;

        // Gérer les rôles
        handleRoleClick(player, clicked, event.getClick());
    }



    /**
     * Gère le clic sur un rôle
     */
    private void handleRoleClick(Player player, ItemStack clicked, ClickType clickType) {
        Component componentName = clicked.getItemMeta().displayName();
        if (componentName == null) return;

        String displayName = clicked.getItemMeta().getDisplayName();

        Role role = gui.findRoleByDisplayName(displayName);

        if (role == null) return;

        int change = calculateChange(clickType);

        if (change > 0) {
            gameManager.getCompositionManager().addRole(role, change);
        } else if (change < 0) {
            gameManager.getCompositionManager().removeRole(role, -change);
        }

        // Rafraîchir le GUI
        gui.open(player);
    }

    /**
     * Calcule le changement selon le type de clic
     */
    private int calculateChange(ClickType clickType) {
        return switch (clickType) {
            case LEFT -> -1;
            case RIGHT -> 1;
            case SHIFT_LEFT -> -5;
            case SHIFT_RIGHT -> 5;
            default -> 0;
        };
    }


    /**
     * Gère le reset de la composition
     */
    private void handleReset(Player player) {
        gameManager.getCompositionManager().clear();
        player.sendMessage("§c✓ Composition réinitialisée !");
        gui.open(player);
    }
}
