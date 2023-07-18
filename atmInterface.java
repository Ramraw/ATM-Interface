import java.util.*;

public class ATM {
    private static final String ATM_USER_ID = "123456";
    private static final String ATM_USER_PIN = "1234";
    private static User currentUser;
    private static List<Transaction> transactionHistory;

    public static void main(String[] args) {
        initialize();

        // Prompt for user ID and PIN
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user PIN: ");
        String userPin = scanner.nextLine();

        if (authenticateUser(userId, userPin)) {
            System.out.println("Login successful!");
            displayMainMenu();
        } else {
            System.out.println("Invalid user ID or PIN. Exiting the system...");
        }
    }

    private static void initialize() {
        transactionHistory = new ArrayList<>();
        // Create sample user and account
        Account account = new Account("1234567890", 1000);
        currentUser = new User("Ramvijay Yadav", "123456", "1234", account);
    }

    private static boolean authenticateUser(String userId, String userPin) {
        return userId.equals(ATM_USER_ID) && userPin.equals(ATM_USER_PIN);
    }

    private static void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== ATM MAIN MENU ====");
            System.out.println("1. View Account Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAccountBalance();
                    break;
                case 2:
                    withdrawMoney();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    System.out.println("Exiting the system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    private static void viewAccountBalance() {
        double balance = currentUser.getAccount().getBalance();
        System.out.println("Current Account Balance: $" + balance);
    }

    private static void withdrawMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();

        Account account = currentUser.getAccount();
        if (account.withdraw(amount)) {
            Transaction transaction = new Transaction(Transaction.Type.WITHDRAW, amount);
            transactionHistory.add(transaction);
            System.out.println("Withdrawal successful. Please collect your cash.");
        } else {
            System.out.println("Insufficient balance. Withdrawal failed.");
        }
    }

    private static void depositMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();

        Account account = currentUser.getAccount();
        account.deposit(amount);

        Transaction transaction = new Transaction(Transaction.Type.DEPOSIT, amount);
        transactionHistory.add(transaction);
        System.out.println("Deposit successful. Thank you for banking with us.");
    }

    private static void transferMoney() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the recipient's account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        Account senderAccount = currentUser.getAccount();
        Account recipientAccount = getRecipientAccount(accountNumber);

        if (recipientAccount != null) {
            if (senderAccount.transfer(recipientAccount, amount)) {
                Transaction transaction = new Transaction(Transaction.Type.TRANSFER, amount);
                transaction.setRecipientAccountNumber(accountNumber);
                transactionHistory.add(transaction);
                System.out.println("Transfer successful. Amount transferred to " + accountNumber);
            } else {
                System.out.println("Insufficient balance. Transfer failed.");
            }
        } else {
            System.out.println("Recipient account not found. Transfer failed.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n==== TRANSACTION HISTORY ====");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.toString());
        }
    }

    private static Account getRecipientAccount(String accountNumber) {
        // In this example, we assume the recipient's account is pre-defined
        Account recipientAccount = new Account("0987654321", 0);
        return recipientAccount.getAccountNumber().equals(accountNumber) ? recipientAccount : null;
    }
}

class User {
    private String name;
    private String userId;
    private String userPin;
    private Account account;

    public User(String name, String userId, String userPin, Account account) {
        this.name = name;
        this.userId = userId;
        this.userPin = userPin;
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean transfer(Account recipientAccount, double amount) {
        if (balance >= amount) {
            balance -= amount;
            recipientAccount.deposit(amount);
            return true;
        }
        return false;
    }
}

class Transaction {
    enum Type {
        WITHDRAW,
        DEPOSIT,
        TRANSFER
    }

    private Type type;
    private double amount;
    private String recipientAccountNumber;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public void setRecipientAccountNumber(String recipientAccountNumber) {
        this.recipientAccountNumber = recipientAccountNumber;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Type: ").append(type.toString());
        builder.append(", Amount: $").append(amount);

        if (type == Type.TRANSFER) {
            builder.append(", Recipient Account: ").append(recipientAccountNumber);
        }

        return builder.toString();
    }
}
