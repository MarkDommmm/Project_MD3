package Bank;

import Model.Account;
import Database.IOFile;
import Model.Transaction;
import Model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankSystem implements Serializable {

    private List<User> userss;
    private IOFile<User> ioFileUser;
    private List<Account> accounts;
    private IOFile<Account> ioFileAccount;
    private List<Transaction> transactionLists;
    private IOFile<Transaction> transactionsFile;

    public BankSystem() {
        transactionLists = new ArrayList<>();

        ioFileUser = new IOFile<>();
        this.userss = ioFileUser.readFromFile(IOFile.LISTUSE_FILE);
        System.out.println(userss + " Bank");

        ioFileAccount = new IOFile<>();
        this.accounts = ioFileAccount.readFromFile(IOFile.LISTACCOUNT_FILE);
        System.out.println(accounts + " Account");


        transactionsFile = new IOFile<>();
//        this.transactions = transactionsFile.readFromFile(IOFile.TRANSACTION_FILE);
    }


    public void processTransaction(Transaction transaction) {
        transactionLists.add(transaction);
        transactionsFile.writeToFile(transactionLists, IOFile.TRANSACTION_FILE);
    }

    public User authenticateUser(String username, String password) {
        for (User user : userss) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<Account> getUserAccounts(String username) {
        List<Account> userAccounts = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getOwner().getUsername().equals(username)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean withdrawal(Account account, double amount) {
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            ioFileAccount.writeToFile(accounts, IOFile.LISTACCOUNT_FILE);
            return true;
        }
        return false;
    }

    public boolean Transfer(Account fromAccount, Account ToAccount, double amount) {
        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            ToAccount.setBalance(ToAccount.getBalance() + amount);
            ioFileAccount.writeToFile(accounts, IOFile.LISTACCOUNT_FILE);
            return true;

        }
        return false;
    }

    public boolean deposit(Account account, double amount) {
        if (account != null && amount > 0) {
            account.setBalance(account.getBalance() + amount);
            ioFileAccount.writeToFile(accounts, IOFile.LISTACCOUNT_FILE);
            return true;
        }
        return false;
    }

    public boolean changePin(User user, String oldPin, String newPin) {
        if (!user.getPassword().equals(oldPin)) {
            System.out.println("╔═════════════════════════════════════════════════╗");
            System.out.println("║       Mã PIN hiện tại không chính xác.          ║");
            System.out.println("╚═════════════════════════════════════════════════╝");
            return false;
        }
        if (user.getPassword().equals(newPin)) {
            System.out.println("╔═════════════════════════════════════════════════╗");
            System.out.println("║       Mã PIN mới phải khác mã PIN hiện tại.     ║");
            System.out.println("╚═════════════════════════════════════════════════╝");
        }
        System.out.println(user + user.getPassword());
        user.setPassword(newPin);
        ioFileUser.writeToFile(userss, IOFile.LISTUSE_FILE);

        return true;
    }

    @Override
    public String toString() {
        return "BankSystem{" +
                "users=" + userss +
                ", accounts=" + accounts +
                '}';
    }


}
