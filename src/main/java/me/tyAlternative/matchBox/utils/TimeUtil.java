package me.tyalternative.matchbox.utils;

public class TimeUtil {

    /**
     * Formate des millisecondes en MM:SS
     */
    public static String formatMillis(long millis) {
        long seconds = millis / 1000;
        return formatSeconds((int) seconds);
    }

    /**
     * Formate des secondes en MM:SS
     */
    public static String formatSeconds(int totalSeconds) {
        if (totalSeconds <= 0) {
            return "00:00";
        }

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Formate une durée lisible
     */
    public static String formatDuration(int seconds) {
        if (seconds < 60) {
            return seconds + "s";
        }

        int minutes = seconds / 60;
        int secs = seconds % 60;

        if (secs == 0) {
            return minutes + "m";
        }

        return minutes + "m " + secs + "s";
    }
}
