package me.tyalternative.matchbox.role.abilities;

public class AbilityResult {
    private final boolean success;
    private final String message;

    private AbilityResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static AbilityResult success() {
        return new AbilityResult(true, null);
    }

    public static AbilityResult success(String message) {
        return new AbilityResult(true, message);
    }

    public static AbilityResult failure(String message) {
        return new AbilityResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public boolean hasMessage() {
        return message != null;
    }
}
