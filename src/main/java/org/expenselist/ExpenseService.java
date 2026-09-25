package org.expenselist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class ExpenseService {

    private final List<Expense> expenses = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Expense addExpense(
            BigDecimal amount,
            ExpenseCategory category,
            String description,
            LocalDate date
    ) {
        Expense expense = new Expense(
                nextId.getAndIncrement(),
                amount,
                category,
                description,
                date
        );

        expenses.add(expense);
        return expense;
    }

    public List<Expense> findAll() {
        return expenses.stream()
                .sorted(Comparator.comparing(Expense::date).reversed())
                .toList();
    }

    public Optional<Expense> findById(long id) {
        return expenses.stream()
                .filter(expense -> expense.id() == id)
                .findFirst();
    }

    public boolean deleteById(long id) {
        return expenses.removeIf(expense -> expense.id() == id);
    }

    public BigDecimal getTotal() {
        return expenses.stream()
                .map(Expense::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalByCategory(ExpenseCategory category) {
        return expenses.stream()
                .filter(expense -> expense.category() == category)
                .map(Expense::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<ExpenseCategory, BigDecimal> getTotalsByCategory() {
        Map<ExpenseCategory, BigDecimal> totals =
                new EnumMap<>(ExpenseCategory.class);

        for (ExpenseCategory category : ExpenseCategory.values()) {
            totals.put(category, BigDecimal.ZERO);
        }

        for (Expense expense : expenses) {
            totals.merge(
                    expense.category(),
                    expense.amount(),
                    BigDecimal::add
            );
        }

        return totals;
    }

    public List<Expense> findByMonth(YearMonth month) {
        return expenses.stream()
                .filter(expense ->
                        YearMonth.from(expense.date()).equals(month)
                )
                .sorted(Comparator.comparing(Expense::date).reversed())
                .toList();
    }

    public BigDecimal getTotalForMonth(YearMonth month) {
        return findByMonth(month).stream()
                .map(Expense::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
