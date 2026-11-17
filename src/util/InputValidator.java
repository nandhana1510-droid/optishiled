package util;

public class InputValidator {

    private InputValidator() {

        // Prevents object creation
    }

    public static boolean
    isValidDuration(int duration) {

        return duration > 0;
    }

    public static boolean
    isValidPriority(int priority) {

        return priority >= 1
                && priority <= 10;
    }

    public static boolean
    isValidDeadline(int deadline) {

        return deadline > 0;
    }

    public static boolean
    isValidTaskName(String name) {

        return name != null
                && !name.trim().isEmpty();
    }
}