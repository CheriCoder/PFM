import java.util.ArrayList;
import java.util.Scanner;

// Class to represent each transaction (either income or expense)
class Transaction {
    String type;            // Type of transaction (Income/Expense)
    double amount;          // Amount of the transaction
    String description;     // Description for the transaction

    // Constructor to initialize transaction details
    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    // Override toString method to display transaction information
    public String toString() {
        return type + ": Rs. " + amount + " (" + description + ")";
    }
}

// Class to manage finances (income, expenses, transactions)
class FinanceManager {
    private double totalIncome;                 // Store total income
    private double totalExpenses;               // Store total expenses
    private ArrayList<Transaction> transactions;// List of all transactions

    // Constructor to initialize variables
    public FinanceManager() {
        totalIncome = 0;
        totalExpenses = 0;
        transactions = new ArrayList<>();
    }

    // Method to add income transaction
    public void addIncome(double amount, String description) {
        totalIncome += amount; // Add to total income
        transactions.add(new Transaction("Income", amount, description)); // Add to transaction list
    }

    // Method to add expense transaction
    public void addExpense(double amount, String description) {
        totalExpenses += amount; // Add to total expenses
        transactions.add(new Transaction("Expense", amount, description)); // Add to transaction list
    }

    // Method to calculate and display remaining balance
    public double getRemainingBalance() {
        return totalIncome - totalExpenses; // Remaining balance = Income - Expenses
    }

    // Method to display financial summary (total income, expenses, balance) and list of transactions
    public void showSummary() {
        System.out.println("\n--- Financial Summary ---");
        System.out.println("Total Income: Rs. " + totalIncome);   // Display total income
        System.out.println("Total Expenses: Rs. " + totalExpenses); // Display total expenses
        double remainingBalance = getRemainingBalance();
        System.out.println("Remaining Balance: Rs. " + remainingBalance); // Display remaining balance
        double requiredSavings = totalIncome * 0.10; // 10% of income should be saved
        System.out.println("Recommended Savings (10% of Income): Rs. " + requiredSavings);

        // Alert if remaining money is less than the recommended savings
        if (remainingBalance < requiredSavings) {
            System.out.println("Alert: Your remaining balance is less than the recommended 10% savings!");
        }

        System.out.println("\n--- Transactions ---");
        for (Transaction transaction : transactions) {
            System.out.println(transaction); // Display each transaction
        }
    }

    // Method to calculate and recommend monthly savings for a specific goal
    public void recommendGoalSaving(double goalAmount, int months) {
        double monthlySaving = goalAmount / months; // Calculate the monthly saving
        System.out.println("\nTo reach your goal of Rs. " + goalAmount + " in " + months + " months, "
                + "you need to save Rs. " + monthlySaving + " per month.");
    }

    // Method to recommend saving strategy based on current financial situation
    public void suggestSavingPlan(double goalAmount, double userPreferredSaving) {
        double remainingBalance = getRemainingBalance();
        if (remainingBalance <= 0) {
            System.out.println("You do not have any remaining balance to save for your goal right now.");
            return;
        }

        System.out.println("\n--- Goal Savings Advice ---");
        System.out.println("You want to save Rs. " + goalAmount + " for your goal.");
        System.out.println("Your remaining balance is Rs. " + remainingBalance + " after income and expenses.");

        // Use either the remaining balance or user-preferred saving amount
        double monthlySaving;
        if (userPreferredSaving > 0) {
            monthlySaving = Math.min(userPreferredSaving, goalAmount); // Use user-specified amount or less if goal is smaller
        } else {
            monthlySaving = Math.min(remainingBalance * 0.5, goalAmount); // Default to 50% of remaining balance
        }

        int months = (int) Math.ceil(goalAmount / monthlySaving); // Calculate the number of months required
        System.out.println("Based on your choice, you should save Rs. " + monthlySaving + " per month.");
        System.out.println("It will take you approximately " + months + " month(s) to reach your goal.");
    }
}

// Main class to interact with the user
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner for user input
        FinanceManager financeManager = new FinanceManager(); // Create finance manager object
        int choice;

        // Main loop to keep the program running until user exits
        do {
            // Display menu
            System.out.println("\n--- Personal Finance Management ---");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Summary");
            System.out.println("4. Set Saving Goal");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt(); // Read user choice

            switch (choice) {
                case 1:
                    // Add income
                    System.out.print("Enter income amount: ");
                    double income = scanner.nextDouble(); // Get income amount
                    scanner.nextLine();  // Consume newline character
                    System.out.print("Enter description: ");
                    String incomeDesc = scanner.nextLine(); // Get description of income
                    financeManager.addIncome(income, incomeDesc); // Add income to finance manager
                    break;
                case 2:
                    // Add expense
                    System.out.print("Enter expense amount: ");
                    double expense = scanner.nextDouble(); // Get expense amount
                    scanner.nextLine();  // Consume newline character
                    System.out.print("Enter description: ");
                    String expenseDesc = scanner.nextLine(); // Get description of expense
                    financeManager.addExpense(expense, expenseDesc); // Add expense to finance manager
                    break;
                case 3:
                    // Show financial summary
                    financeManager.showSummary();
                    break;
                case 4:
                    // Set a saving goal
                    System.out.print("Enter the goal you are saving for: ");
                    scanner.nextLine(); // Consume newline character
                    String goal = scanner.nextLine(); // Get goal description
                    System.out.print("Enter the amount to save for " + goal + ": ");
                    double goalAmount = scanner.nextDouble(); // Get goal amount

                    // Ask user for saving strategy choice
                    System.out.println("Do you want to:");
                    System.out.println("1. Save for a specific number of months.");
                    System.out.println("2. Take the system's advice on how to save.");
                    int savingChoice = scanner.nextInt();

                    if (savingChoice == 1) {
                        // User chooses to save for a specific time period
                        System.out.print("Enter the number of months you want to save for: ");
                        int months = scanner.nextInt();
                        financeManager.recommendGoalSaving(goalAmount, months); // Recommend based on user input
                    } else if (savingChoice == 2) {
                        // User chooses to take system advice

                        // Ask if they want to use remaining balance or have a specific saving amount in mind
                        System.out.println("Do you want to:");
                        System.out.println("1. Use the remaining balance to calculate savings.");
                        System.out.println("2. Enter a specific monthly saving amount.");
                        int adviceChoice = scanner.nextInt();

                        double userPreferredSaving = 0;
                        if (adviceChoice == 2) {
                            // User wants to enter a specific saving amount
                            System.out.print("Enter the monthly saving amount: ");
                            userPreferredSaving = scanner.nextDouble(); // Get user-specified saving amount
                        }

                        // Recommend based on user choice (remaining balance or specified saving amount)
                        financeManager.suggestSavingPlan(goalAmount, userPreferredSaving);
                    } else {
                        System.out.println("Invalid option. Please choose 1 or 2.");
                    }
                    break;
                case 5:
                    // Exit the program
                    System.out.println("Exiting the program...");
                    break;
                default:
                    // Handle invalid input
                    System.out.println("Invalid option, please try again.");
            }
        } while (choice != 5); // Continue until user selects exit option

        scanner.close(); // Close scanner
    }
}
