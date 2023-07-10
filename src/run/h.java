package run;

import Bank.ATM;
import Bank.BankSystem;
import Database.IOFile;
import Model.Card;
import Model.Transaction;
import Model.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class h {
    public static void main(String[] args) {
        IOFile<ATM> atmioFile = new IOFile<>();
        IOFile<Users> userFile = new IOFile<>();
        IOFile<BankSystem> bankSystemIOFile = new IOFile<>();
        IOFile<Card> accountIOFile = new IOFile<>();
        IOFile<Transaction> transactionIOFile = new IOFile<>();

        Users user = new Users("hieu", "hieu");
        Card card = new Card(7777, 7777, 25000000, user, Card.cardStatus.UNLOCK, Card.cardNember.GOLD,0);
        Card card1 = new Card(6666, 6666, 1000.0, user,Card.cardStatus.UNLOCK, Card.cardNember.SILVER,0);

        Users users1 = new Users("long", "long");
        Card card2 = new Card(111, 111, 77.0, users1,Card.cardStatus.UNLOCK, Card.cardNember.BRONZE,0);
        Card card3 = new Card(8888, 8888, 777.0, users1,Card.cardStatus.UNLOCK, Card.cardNember.SILVER,0);

        List<Users> users = new ArrayList<>();
        List<Card> cardList = new ArrayList<>();
        users.add(user);
        cardList.add(card);
        cardList.add(card1);
        users.add(users1);
        cardList.add(card2);
        cardList.add(card3);

        String transactionId = Transaction.generateTransactionId();
        LocalDateTime timestamp = LocalDateTime.now();
        Transaction transaction = new Transaction(
                transactionId,
                timestamp,
                7777,
                null,
                1000,
                10000,
                1100,
                Transaction.TransactionType.WITHDRAWAL,
                Transaction.TransactionStatus.SUCCESS);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionIOFile.writeToFile(transactionList, IOFile.TRANSACTION_FILE);

        userFile.writeToFile(users, IOFile.LISTUSE_FILE);
        accountIOFile.writeToFile(cardList, IOFile.LISTCARD_FILE);

        BankSystem bankSystem = new BankSystem();
        List<BankSystem> bankSystemList = new ArrayList<>();
        bankSystemList.add(bankSystem);
        bankSystemIOFile.writeToFile(bankSystemList, IOFile.BANKSYSTEM_FILE);

        ATM atm1 = new ATM("ATM111", 100000, bankSystem);
        ATM atm2 = new ATM("ATM222", 2000000, bankSystem);
        ATM atm3 = new ATM("ATM555", 30000000, bankSystem);
        ATM atm4 = new ATM("ATM666", 400000000, bankSystem);
        ATM atm5 = new ATM("ATM777", 500000000, bankSystem);
        List<ATM> atmList = new ArrayList<>();
        atmList.add(atm1);
        atmList.add(atm2);
        atmList.add(atm3);
        atmList.add(atm4);
        atmList.add(atm5);
        atmioFile.writeToFile(atmList, IOFile.LISTATM_FILE);
//
    }
}
