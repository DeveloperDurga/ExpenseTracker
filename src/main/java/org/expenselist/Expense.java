package org.expenselist;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Expense(
        long id,
        BigDecimal amount,
        ExpenseCategory category,
        String description,
        LocalDate date
) {
    public Expense {
        if (id <= 0) {
            throw new IllegalArgumentException("Expense ID must be positive");
        }

        Objects.requireNonNull(amount, "Amount is required");
        Objects.requireNonNull(category, "Category is required");
        Objects.requireNonNull(description, "Description is required");
        Objects.requireNonNull(date, "Date is required");

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
    }
}

