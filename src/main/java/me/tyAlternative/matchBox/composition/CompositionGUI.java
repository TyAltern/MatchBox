package me.tyalternative.matchbox.composition;

import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.Role;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Gestionnaire de l'interface graphique de composition
 */
public class CompositionGUI {

    public static final Component GUI_TITLE = Component.text("§8Composition de la partie");
    private final GameManager gameManager;

    public CompositionGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    /**
     * Ouvre le GUI pour un joueur
     */
    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, GUI_TITLE);

        fillBorders(inv);
        fillRoles(inv);
        addControlButtons(inv);

        player.openInventory(inv);

    }

    /**
     * Remplit les bordures du GUI
     */
    private void fillBorders(Inventory inv) {
        ItemStack border = createBorderItem();

        // Première ligne
        for (int i = 0; i <= 8; i++) {
            inv.setItem(i, border);
        }

        // Dernière ligne
        for (int i = 45; i <= 53; i++) {
            inv.setItem(i, border);
        }

        // Colonnes gauche et droite
        inv.setItem(9, border);
        inv.setItem(18, border);
        inv.setItem(27, border);
        inv.setItem(36, border);

        inv.setItem(17, border);
        inv.setItem(26, border);
        inv.setItem(35, border);
        inv.setItem(44, border);
    }


    /**
     * Remplit le GUI avec les rôles disponibles
     */
    private void fillRoles(Inventory inv) {
        int slot = 10;

        for (Role role : gameManager.getPlugin().getRoleRegistry().getAll()) {
            // Sauter les bordures
            if (slot == 17 || slot == 18) slot = 19;
            if (slot == 26 || slot == 27) slot = 28;
            if (slot == 35 || slot == 36) slot = 37;
            if (slot >= 44) break;

            int count = gameManager.getCompositionManager().getRoleCount(role);
            ItemStack item = createRoleItem(role, count);
            inv.setItem(slot, item);

            slot++;
        }
    }


    /**
     * Ajoute les boutons de contrôle
     */
    private void addControlButtons(Inventory inv) {
        // Bouton reset
        ItemStack reset = new ItemStack(Material.BARRIER);
        ItemMeta resetMeta = reset.getItemMeta();
        if (resetMeta != null) {
            resetMeta.displayName(Component.text("§c§lRéinitialiser"));
            resetMeta.lore(Arrays.asList(
                    Component.text("§7Efface toute la composition"),
                    Component.empty(),
                    Component.text("§c⚠ Action irréversible")
            ));
            reset.setItemMeta(resetMeta);
        }
        inv.setItem(49, reset);

        // Bouton info
        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = info.getItemMeta();
        if (infoMeta != null) {
            infoMeta.displayName(Component.text("§e§lAide"));
            infoMeta.lore(Arrays.asList(
                    Component.text("§7Clic gauche : §c-1"),
                    Component.text("§7Clic droit : §a+1"),
                    Component.text("§7Shift + gauche : §c-5"),
                    Component.text("§7Shift + droit : §a+5"),
                    Component.empty(),
                    Component.text("§7Total : §e" + gameManager.getCompositionManager().getTotalRoles())
            ));
            info.setItemMeta(infoMeta);
        }
        inv.setItem(53, info);
    }


    /**
     * Crée un item de bordure
     */
    private ItemStack createBorderItem() {
        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = border.getItemMeta();
        if (meta == null) return border;

        meta.displayName(Component.empty());
        meta.setHideTooltip(true);
        border.setItemMeta(meta);

        return border;
    }


    /**
     * Crée un item pour un rôle
     */
    private ItemStack createRoleItem(Role role, int count) {
        ItemStack item = new ItemStack(role.getGuiIcon(), Math.max(1, count));
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        meta.customName(Component.text(role.getDisplayName()));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7Quantité : §e" + count));
        lore.add(Component.empty());

        if (count > 0) {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            lore.add(Component.text("§a✓ §7Actif dans la composition"));
            lore.add(Component.empty());
        } else {
            meta.removeEnchantments();
        }

        lore.add(Component.text("§7Clic gauche : §c-1"));
        lore.add(Component.text("§7Clic droit : §a+1"));
        lore.add(Component.text("§7Shift + gauche : §c-5"));
        lore.add(Component.text("§7Shift + droit : §a+5"));

        meta.lore(lore);
        item.setItemMeta(meta);

        return item;


    }



    /**
     * Trouve un rôle par son nom d'affichage
     */
    public Role findRoleByDisplayName(String displayName) {
        for (Role role : gameManager.getPlugin().getRoleRegistry().getAll()) {
            if (role.getDisplayName().equals(displayName)) return role;
        }
        return null;
    }

    /**
     * Vérifie si un inventaire est le GUI de composition
     */
    public static boolean isCompositionGUI(String title) {
        return GUI_TITLE.equals(title);
    }
}
