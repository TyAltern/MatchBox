package me.tyalternative.matchbox.ui;

import me.tyalternative.matchbox.abilities.Ability;
import me.tyalternative.matchbox.abilities.AbilityCategory;
import me.tyalternative.matchbox.abilities.AbilityTrigger;
import me.tyalternative.matchbox.core.GameManager;
import me.tyalternative.matchbox.role.Role;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private final GameManager gameManager;

    public MessageManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    public void sendRoleDescription(Player player, String displayName, String description, List<Ability> abilities) {

        List<Ability> capacities = new ArrayList<>();
        List<Ability> effects = new ArrayList<>();
        List<Ability> curses = new ArrayList<>();
        for (Ability ability : abilities) {
            if (ability.isInvisible()) continue;
            switch (ability.getCategory()) {
                case CAPACITY -> capacities.add(ability);
                case NEUTRAL -> effects.add(ability);
                case CURSE -> curses.add(ability);
            }
        }

        Component rolePresentation = Component.text("§8§m§l----------§r§8§l / Role / §m----------").appendNewline()
                .appendNewline()
                .append(Component.text("§r§8§l- §r§7Vous êtes " + displayName)).appendNewline()
                .append(Component.text("§r§8§l- §r§7Objectif : §r" + description)).appendNewline().appendNewline();

        if (!capacities.isEmpty()){
            rolePresentation = rolePresentation.append(Component.text("§r§8§l- §r§7Pour ce faire vous disposez de " + AbilityCategory.CAPACITY.getColorCode() + "§l" + capacities.size() + " " + AbilityCategory.CAPACITY.getDisplayName() + (capacities.size() > 1 ? "s" : "") + "§r§7:"));

            for (Ability ability : capacities) {
                if (ability.isInvisible()) continue;
                rolePresentation = rolePresentation.appendNewline().append(Component.text(AbilityCategory.CAPACITY.getColorCode() + "§n" + ability.getName() + ":§r§f " + ability.getDescription() + " §8(" + ability.getType().getDisplayName() + ") "));
                if (ability.getTrigger() == AbilityTrigger.SWAP_HAND)
                    rolePresentation = rolePresentation.append(getSpecialAbilityButton());
                if (ability.getTrigger() == AbilityTrigger.DOUBLE_SWAP_HAND)
                    rolePresentation = rolePresentation.append(getSpecialAbilityButton2());
            }
            rolePresentation = rolePresentation.appendNewline().appendNewline();
        }

        if (!curses.isEmpty()){
            rolePresentation = rolePresentation.append(Component.text("§r§8§l- §r§7Cependant vous portez" + (capacities.isEmpty()? " ":" aussi ") + AbilityCategory.CURSE.getColorCode() + "§l" + curses.size() + " " + AbilityCategory.CURSE.getDisplayName() + (curses.size() > 1 ? "s" : "") + "§r§7:"));

            for (Ability ability : curses) {
                if (ability.isInvisible()) continue;
                rolePresentation = rolePresentation.appendNewline().append(Component.text(AbilityCategory.CURSE.getColorCode() + "§n" + ability.getName() + ":§r§f " + ability.getDescription()));
            }
            rolePresentation = rolePresentation.appendNewline().appendNewline();
        }
        rolePresentation = rolePresentation.appendNewline();

        player.sendMessage(rolePresentation);


    }

    protected Component getSpecialAbilityButton() {
        return Component.text("").append(Component.text("[").append(Component.keybind("key.swapOffhand").append(Component.text("]"))).style(Style.style(TextColor.color(255,85,255))));
    }
    protected Component getSpecialAbilityButton2() {
        return Component.text("").append(Component.text("[2x ").append(Component.keybind("key.swapOffhand").append(Component.text("]"))).style(Style.style(TextColor.color(255,85,255))));
    }
    protected Component getSpecialAbilityButton(Style style) {
        return Component.text("").append(Component.text("[").append(Component.keybind("key.swapOffhand").append(Component.text("]"))).style(style.color(TextColor.color(255,85,255))));
    }
    protected Component getSpecialAbilityButton(Style style, TextColor color) {
        return Component.text("").append(Component.text("[").append(Component.keybind("key.swapOffhand").append(Component.text("]"))).style(style.color(TextColor.color(color))));
    }
}

