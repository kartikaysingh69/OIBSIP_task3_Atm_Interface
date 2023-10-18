import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Transaction {
    private Date date;
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.date = new Date();
        this.description = description;
        this.amount = amount;
    }

    public String toString() {
        return date + "\t" + description + "\t$" + amount;
    }
}

class BankAccount {
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
        transactionHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public void subtractBalance(double amount) {
        balance -= amount;
        transactionHistory.add(new Transaction("Withdrawal", amount));
    }

    public void transfer(double amount, BankAccount recipientAccount) {
        if (amount > 0 && balance >= amount) {
            subtractBalance(amount);
            recipientAccount.addBalance(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipientAccount.hashCode(), amount));
            System.out.println("Transfer successful. Current balance: " + getBalance());
        } else {
            System.out.println("Invalid transfer amount or insufficient balance.");
        }
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private BankAccount bankAccount;
    private Scanner scanner;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        // User Authentication
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.next();

        // Hardcoded user authentication (for simplicity)
        if (!authenticateUser(userId, userPin)) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the ATM!");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter the amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter the amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter the recipient's account ID: ");
                    String recipientAccountId = scanner.next();
                    BankAccount recipientAccount = getRecipientAccount(recipientAccountId);
                    if (recipientAccount != null) {
                        System.out.print("Enter the amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        transfer(transferAmount, recipientAccount);
                    } else {
                        System.out.println("Recipient account not found.");
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the ATM!");
    }

    private boolean authenticateUser(String userId, String userPin) {
        // Hardcoded user credentials (for simplicity)
        return userId.equals("user69") && userPin.equals("69");
    }

    private void displayTransactionHistory() {
        System.out.println("Transaction History:");
        System.out.println("Date\t\tDescription\tAmount");
        for (Transaction transaction : bankAccount.getTransactionHistory()) {
            System.out.println(transaction.toString());
        }
    }

    private void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
        } else if (bankAccount.getBalance() >= amount) {
            bankAccount.subtractBalance(amount);
            System.out.println("Withdrawal successful. Current balance: " + bankAccount.getBalance());
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }

    private void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
        } else {
            bankAccount.addBalance(amount);
            System.out.println("Deposit successful. Current balance: " + bankAccount.getBalance());
        }
    }

    private void transfer(double amount, BankAccount recipientAccount) {
        bankAccount.transfer(amount, recipientAccount);
    }

    private BankAccount getRecipientAccount(String accountId) {
        // Hardcoded recipient account (for simplicity)
        if (accountId.equals("recipient69")) {
            return new BankAccount(0); // Initial balance for recipient account
        }
        return null;
    }
}

public class ATM_INTERFACE {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000.0); // Initial balance
        ATM atm = new ATM(bankAccount);
        atm.run();
    }
}
