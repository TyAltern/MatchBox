package me.tyalternative.matchbox.listeners;

import me.tyalternative.matchbox.composition.CompositionClickHandler;
import me.tyalternative.matchbox.composition.CompositionGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * Listener pour les interactions avec le GUI de composition
 */
public class CompositionListener implements Listener {

    private final CompositionClickHandler clickHandler;

    public CompositionListener(CompositionClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        if (!event.getView().title().contains(CompositionGUI.GUI_TITLE)) return;

        clickHandler.handleClick(event);
    }
}
