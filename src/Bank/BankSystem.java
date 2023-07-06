package Bank;

import Model.Account;
import Database.IOFile;
import Model.Transaction;
import Model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BankSystem implements Serializable {

    private List<User> userss;
    private IOFile<User> ioFileUser;
    private List<Account> accountss;
    private IOFile<Account> ioFileAccount;
    private List<Transaction> transactionList;
    private IOFile<Transaction> transactionsFile;

    public BankSystem() {
//        transactionLists = new Stack<>();
        ioFileUser = new IOFile<>();
        this.userss = ioFileUser.readFromFile(IOFile.LISTUSE_FILE);
        if (userss == null) {
            userss = new ArrayList<>();
        } else {
            System.out.println(userss + " Bank");
        }

        ioFileAccount = new IOFile<>();
        this.accountss = ioFileAccount.readFromFile(IOFile.LISTACCOUNT_FILE);
        if (accountss == null) {
            accountss = new ArrayList<>();
        } else {
            System.out.println(accountss + " Account");
        }

        transactionsFile = new IOFile<>();
        transactionList = transactionsFile.readFromFile(IOFile.TRANSACTION_FILE);
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }else {
            System.out.println(transactionList+ " transactions");
        }



    }


    public void processTransaction(Transaction transaction) {
        transactionList.add(transaction);
        transactionsFile.writeToFile(transactionList, IOFile.TRANSACTION_FILE);
    }
    public List<Transaction> getTransaction(){
        return  transactionList;
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

        for (Account account : accountss) {
            if (account.getOwner().getUsername().equals(username)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }

    public Account getAccountByNumber(String accountNumber) {
        for (Account account : accountss) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean withdrawal(Account account, double amount) {
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            ioFileAccount.writeToFile(accountss, IOFile.LISTACCOUNT_FILE);
            return true;
        }
        return false;
    }

    public boolean Transfer(Account fromAccount, Account ToAccount, double amount) {
        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            ToAccount.setBalance(ToAccount.getBalance() + amount);
            ioFileAccount.writeToFile(accountss, IOFile.LISTACCOUNT_FILE);
            return true;

        }
        return false;
    }

    public boolean deposit(Account account, double amount) {
        if (account != null && amount > 0) {
            account.setBalance(account.getBalance() + amount);
            ioFileAccount.writeToFile(accountss, IOFile.LISTACCOUNT_FILE);
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
                ", accounts=" + accountss +
                '}';
    }


}
