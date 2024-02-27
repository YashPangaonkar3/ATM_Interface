package atm_interface;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

class BankAccount {
    private double balance;
    private Map<String, Double> transactionHistory;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
        transactionHistory = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Double> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addToTransactionHistory("Deposit", amount);
            System.out.println("Money Deposited: " + amount);
        } else {
            System.out.println("Invalid amount for deposit!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addToTransactionHistory("Withdrawal", amount);
            System.out.println("Money Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount for withdrawal!");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            addToTransactionHistory("Transfer to " + recipient, amount);
            System.out.println("Money Transferred: " + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount for transfer!");
        }
    }

    private void addToTransactionHistory(String description, double amount) {
        transactionHistory.put(description, amount);
    }
}

class ATM {
    private User currentUser;
    private BankAccount currentAccount;
    private Scanner scanner;

    public ATM() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        // Assuming user authentication logic is in a method like authenticateUser()
        if (authenticateUser(userId, pin)) {
            System.out.println("User authenticated. Welcome to the ATM!");

            currentAccount = new BankAccount(1000.0); // Initial balance for demonstration purposes

            int choice;
            do {
                showMenu();
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        showTransactionHistory();
                        break;
                    case 6:
                        System.out.println("Exiting ATM. Thank You!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option!");
                }
            } while (choice != 6);
        } else {
            System.out.println("Authentication failed. Exiting ATM.");
        }
    }

    private boolean authenticateUser(String userId, String pin) {
        // Add your authentication logic here (e.g., check against a database)
        // For demonstration purposes, a simple hard-coded user is used
        User user = new User("123456", "7890");
        return user.getUserId().equals(userId) && user.getPin().equals(pin);
    }

    private void showMenu() {
        System.out.println("******** WELCOME TO ATM ********");
        System.out.println("ATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Cash");
        System.out.println("3. Withdraw Cash");
        System.out.println("4. Transfer Funds");
        System.out.println("5. View Transaction History");
        System.out.println("6. Quit");
    }

    private void checkBalance() {
        System.out.println("Current Balance: " + currentAccount.getBalance());
    }

    private void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        currentAccount.deposit(amount);
    }

    private void withdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        currentAccount.withdraw(amount);
    }

    private void transfer() {
        System.out.print("Enter recipient's User ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        // For demonstration, assuming recipient's account is the same as the current account
        currentAccount.transfer(currentAccount, amount);
    }

    private void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Map.Entry<String, Double> entry : currentAccount.getTransactionHistory().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}


public class Main {
	public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }

}
