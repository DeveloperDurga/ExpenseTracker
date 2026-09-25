package org.expenselist;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ExpenseService expenseService = new ExpenseService();
        //ExpenseCategory category;
        Scanner scanner = new Scanner(System.in);

        String choice;

        do {

            System.out.print("Do you want to add an expense? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "yes", "y" -> {
                    System.out.println("Enter your amount: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();
                    ExpenseCategory category = null;
                    try {
                        System.out.println("Available categories:");

                        for (ExpenseCategory expenseCategory : ExpenseCategory.values()) {
                            System.out.println("- " + expenseCategory.name());
                        }
                        System.out.println("Enter your category: ");
                        String categoryname = scanner.nextLine().trim().toUpperCase();


                        if (ExpenseCategory.isValid(categoryname)) {
                            category = ExpenseCategory.fromString(categoryname);
                            System.out.println("Description: ");
                            String description = scanner.nextLine();
                            System.out.println("Expense date (yyyy-mm-dd): ");
                            LocalDate expensedate = LocalDate.parse(scanner.nextLine());

                            expenseService.addExpense(amount, category, description, expensedate);
                            System.out.println("Expense added successfully.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Could not add expense: " + e.getMessage());
                    }


                }

                case "no", "n" -> System.out.println("No more expenses will be added.");

                default -> System.out.println("Invalid choice. Please enter yes or no.");
            }
        }
        while (!choice.equals("no") && !choice.equals("n"));

        scanner.close();


        System.out.println("All expenses:");
        expenseService.findAll().forEach(System.out::println);

        System.out.println("\nTotal: €" + expenseService.getTotal());

        System.out.println("\nTotals by category:");
        expenseService.getTotalsByCategory().forEach((category, total) ->
                System.out.println(category + ": €" + total)
        );

        YearMonth september = YearMonth.of(2026, 9);

        System.out.println(
                "\nSeptember total: €" +
                        expenseService.getTotalForMonth(september)
        );
    }


}