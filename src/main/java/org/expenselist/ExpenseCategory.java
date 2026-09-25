package org.expenselist;

import java.util.Arrays;
import java.util.Optional;

public enum ExpenseCategory {

    HOUSING,
    GROCERIES,
    RESTAURANTS,
    TRANSPORT,
    UTILITIES,
    SUBSCRIPTIONS,
    HEALTH,
    FITNESS,
    EDUCATION,
    ENTERTAINMENT,
    SHOPPING,
    TRAVEL,
    FAMILY,
    OTHER;

    /**
     * Checks whether the given category text is a valid expense category.
     *
     * Accepted examples:
     * "transport", "TRANSPORT", " Transport "
     *
     * @param input category text entered by the user
     * @return true if the input represents a category in this enum
     */
    public static boolean isValid(String input) {
        return findByName(input).isPresent();
    }

    /**
     * Converts a category string to ExpenseCategory safely.
     *
     * @param input category text entered by the user
     * @return the matching category, or Optional.empty() if invalid
     */
    public static Optional<ExpenseCategory> findByName(String input) {
        if (input == null || input.isBlank()) {
            return Optional.empty();
        }

        String normalizedInput = input.trim().toUpperCase();

        return Optional.of(Arrays.stream(ExpenseCategory.values())
                .filter(category -> category.name().equals(normalizedInput))
                .findFirst().orElseThrow(() ->
                        new IllegalArgumentException("Invalid category: " + input)
                ));

    }

    /**
     * Converts a valid string to ExpenseCategory.
     *
     * @throws IllegalArgumentException when the input is not a valid category
     */
    public static ExpenseCategory fromString(String input) {
        return findByName(input)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid expense category: '" + input + "'. "
                                + "Allowed categories: " + getAllowedCategories()
                ));
    }

    /**
     * Returns all valid category names for messages or UI output.
     */
    public static String getAllowedCategories() {
        return Arrays.stream(ExpenseCategory.values())
                .map(ExpenseCategory::name)
                .reduce((first, second) -> first + ", " + second)
                .orElse("");
    }
}