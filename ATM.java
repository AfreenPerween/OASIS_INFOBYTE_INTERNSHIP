import java.util.ArrayList;
import java.util.Scanner;

// Interface for ATM operations
interface ATMOperations {
    void viewTransactionHistory();
    void withdraw(double amount);
    void deposit(double amount);
    void transfer(double amount, Customer recipient);
    void quit();
}

// Class representing a Customer
class Customer {
    private String customerId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Customer(String customerId, String pin, double initialBalance) {
        this.customerId = customerId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}

// Class for Bank which handles Customer accounts
class Bank {
    private ArrayList<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
        // Predefined customers
        customers.add(new Customer("cust1", "1234", 1000));
        customers.add(new Customer("cust2", "5678", 500));
    }

    // Method to validate a customer's credentials
    public Customer validateCustomer(String customerId, String pin) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId) && customer.getPin().equals(pin)) {
                return customer;
            }
        }
        return null;
    }

    // Method to find a customer by ID
    public Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}

// ATM class implementing the ATMOperations interface
public class ATM implements ATMOperations {
    private Customer currentCustomer;
    private Bank bank;
    private Scanner scanner;

    public ATM() {
        bank = new Bank();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        currentCustomer = bank.validateCustomer(customerId, pin);

        if (currentCustomer != null) {
            System.out.println("Login successful!");
            showMenu();
        } else {
            System.out.println("Invalid Customer ID or PIN.");
        }
    }

    private void showMenu() {
        int option;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient Customer ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    transfer(transferAmount, bank.findCustomerById(recipientId));
                    break;
                case 5:
                    quit();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

   @Override
    public void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (currentCustomer.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : currentCustomer.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= currentCustomer.getBalance()) {
            currentCustomer.setBalance(currentCustomer.getBalance() - amount);
            currentCustomer.addTransaction("Withdrew Rs." + amount);
            System.out.println("Withdrawal successful. New balance: Rs." + currentCustomer.getBalance());
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            currentCustomer.setBalance(currentCustomer.getBalance() + amount);
            currentCustomer.addTransaction("Deposited Rs." + amount);
            System.out.println("Deposit successful. New balance: Rs." + currentCustomer.getBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }

    @Override
    public void transfer(double amount, Customer recipient) {
        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }
        if (amount > 0 && amount <= currentCustomer.getBalance()) {
            currentCustomer.setBalance(currentCustomer.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            currentCustomer.addTransaction("Transferred Rs." + amount + " to " + recipient.getCustomerId());
            recipient.addTransaction("Received Rs." + amount + " from " + currentCustomer.getCustomerId());
            System.out.println("Transfer successful. New balance: Rs." + currentCustomer.getBalance());
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    @Override
    public void quit() {
        System.out.println("Thank you for using the ATM. HAVE A GOOD DAY!");
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
