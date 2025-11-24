package me.tyalternative.matchbox.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class TextUtil {
    /**
     * Crée une ligne de séparation
     */
    public static String separator(int length) {
        return "§8" + "=".repeat(length);
    }

    /**
     * Centre un texte
     */
    public static String center(String text, int width) {
        int spaces = (width - stripColor(text).length()) / 2;
        return " ".repeat(Math.max(0, spaces)) + text;
    }

    /**
     * Retire les codes couleur
     */
    public static String stripColor(String text) {
        return text.replaceAll("§[0-9a-fk-or]", "");
    }

    /**
     * Crée un Component coloré
     */
    public static Component colored(String text, int r, int g, int b) {
        return Component.text(text).color(TextColor.color(r, g, b));
    }

    /**
     * Créer une barre de progression
     */
    public static String progressBar(double percentage, int length, char symbol) {
        int filled = (int) (percentage * length);
        int empty = length - filled;

        return "§a" + String.valueOf(symbol).repeat(filled) +
                "§7" + String.valueOf(symbol).repeat(empty);
    }
}
