package run;

import Bank.ATM;
import Bank.BankSystem;
import Database.IOFile;
import Model.Account;
import Model.Transaction;
import Model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class h {
    public static void main(String[] args) {
        IOFile<ATM> atmioFile = new IOFile<>();
        IOFile<User> userFile = new IOFile<>();
        IOFile<BankSystem> bankSystemIOFile = new IOFile<>();
        IOFile<Account> accountIOFile = new IOFile<>();
        IOFile<Transaction> transactionIOFile = new IOFile<>();

        List<ATM> atmList1 = atmioFile.readFromFile(IOFile.LISTATM_FILE);

        BankSystem bankSystem = new BankSystem();
        ATM atm1 = new ATM("ATM111", 1111.0, bankSystem);
        ATM atm2 = new ATM("ATM222", 2222.0, bankSystem);
        ATM atm3 = new ATM("ATM555", 5555.0, bankSystem);
        ATM atm4 = new ATM("ATM666", 6666.0, bankSystem);
        ATM atm5 = new ATM("ATM777", 7777.0, bankSystem);
        List<ATM> atmList = new ArrayList<>();
        atmList.add(atm1);
        atmList.add(atm2);
        atmList.add(atm3);
        atmList.add(atm4);
        atmList.add(atm5);
        atmioFile.writeToFile(atmList, IOFile.LISTATM_FILE);

        User user = new User("dom", "dom");
        Account account = new Account("ACC001", 100.0, user);
        Account account2 = new Account("ACC002", 1000.0, user);

        User user1 = new User("hieu", "hieu");
        Account account3 = new Account("ACC777", 77, user1);
        Account account4 = new Account("ACC7777", 777, user1);

        List<User> users = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        users.add(user);
        accountList.add(account);
        accountList.add(account2);
        users.add(user1);
        accountList.add(account3);
        accountList.add(account4);

        String transactionId = Transaction.generateTransactionId();
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction(
                transactionId,
                timestamp,
                "ACC777",
                "",
                10.1,
                Transaction.TransactionType.WITHDRAWAL,
                Transaction.TransactionStatus.SUCCESS);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionIOFile.writeToFile(transactionList, IOFile.TRANSACTION_FILE);


        List<BankSystem> bankSystemList = new ArrayList<>();
        bankSystemList.add(bankSystem);

        userFile.writeToFile(users, IOFile.LISTUSE_FILE);

        accountIOFile.writeToFile(accountList, IOFile.LISTACCOUNT_FILE);

        bankSystemIOFile.writeToFile(bankSystemList, IOFile.BANKSYSTEM_FILE);

        List<User> userList123 = userFile.readFromFile(IOFile.LISTUSE_FILE);

        for (User user2 : userList123) {
            System.out.println(user2);
        }

        for (ATM atm : atmList1) {
            System.out.println(atm.getAtmID());
        }

    }
}
