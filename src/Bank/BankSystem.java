package Bank;

import Model.Card;
import Database.IOFile;
import Model.Transaction;
import Model.Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankSystem implements Serializable {

    private List<Users> users;
    private IOFile<Users> ioFileUser;
    private List<Card> cards;
    private IOFile<Card> ioFileCard;
    private List<Transaction> transactionList;
    private IOFile<Transaction> transactionsFile;

    public BankSystem() {
        ioFileUser = new IOFile<>();
        this.users = ioFileUser.readFromFile(IOFile.LISTUSE_FILE);
        if (users == null) {
            users = new ArrayList<>();
        } else {
            System.out.println(users + " Bank");
        }

        ioFileCard = new IOFile<>();
        this.cards = ioFileCard.readFromFile(IOFile.LISTCARD_FILE);
        if (cards == null) {
            cards = new ArrayList<>();
        } else {
            System.out.println(cards + " Account");
        }

        transactionsFile = new IOFile<>();
        transactionList = transactionsFile.readFromFile(IOFile.TRANSACTION_FILE);
        if (transactionList == null) {
            transactionList = new ArrayList<>();
        } else {
            System.out.println(transactionList + " transactions");
        }


    }
    //===========================================HỆ THỐNG NGÂN HÀNG=====================================================


    public void processTransaction(Transaction transaction) {
        transactionList.add(transaction);
        transactionsFile.writeToFile(transactionList, IOFile.TRANSACTION_FILE);
    }
    //=============================================GIAO DỊCH NGÂN HÀNG===================================================

    public List<Transaction> getTransaction() {
        return transactionList;
    }

    //===========================================LẤY TẤT CẢ GIAO DỊCH=====================================================

    public Users authenticateUser(String cardNumbers, String pin) {
        this.users = ioFileUser.readFromFile(IOFile.LISTUSE_FILE);
        for (Users u : users) {
            if (u.getUsername().equals(cardNumbers) && u.getPassword().equals(pin)) {
                return u;
            }
        }
        return null;
    }
    //==============================================XÁC THỰC TÀI KHOẢN NGƯỜI DÙNG==================================================

    public List<Card> getUserAccounts(String cardnumbers) {
        List<Card> userCards = new ArrayList<>();
        this.transactionList = transactionsFile.readFromFile(IOFile.TRANSACTION_FILE);
        this.cards = ioFileCard.readFromFile(IOFile.LISTCARD_FILE);
        for (Card card : cards) {
            if (card.getOwner().getUsername().equals(cardnumbers)) {
                userCards.add(card);
            }
        }
        return userCards;
    }
    //======================================LẤY TÀI KHOẢN NGƯỜI DÙNG BAO GÒM CÁC THẺ NGÂN HÀNG===============================================

    public Card getAccountByNumber(Integer accountNumber) {
        for (Card card : cards) {
            if (card.getCardNumber().equals(accountNumber)) {
                return card;
            }
        }
        return null;
    }
    //==============================================LẤY SỐ THẺ==================================================

    public boolean withdrawal(Card card, double amount, double fee) {
        if (card.getBalance() >= amount) {
            card.setBalance(card.getBalance() - (amount + fee));
            card.setWitHDrawalCountLimit(card.getWitHDrawalCountLimit() + amount);
            ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
            return true;
        }
        return false;
    }
    //===========================================PHƯƠNG THỨC RÚT TIỀN=====================================================

    public boolean Transfer(Card fromCard, Card toCard, double amount) {
        if (fromCard.getBalance() >= amount) {
            fromCard.setBalance(fromCard.getBalance() - amount);
            toCard.setBalance(toCard.getBalance() + amount);
            ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
            return true;

        }
        return false;
    }
    //===============================================CHUYỂN TIỀN=================================================

    public boolean deposit(Card card, double amount) {
        if (card != null && amount > 0) {
            card.setBalance(card.getBalance() + amount);
            ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
            this.cards = ioFileCard.readFromFile(IOFile.LISTCARD_FILE);
            return true;
        }
        return false;
    }
    //============================================NẠP TIỀN====================================================

    public boolean changePass(Users user, String oldPin, String newPin) {
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

        user.setPassword(newPin);
        ioFileUser.writeToFile(users, IOFile.LISTUSE_FILE);
        return true;
    }
    //===========================================THAY ĐỔI MẬT KHẨU NGƯỜI DÙNG===================================================

    public boolean changePin(Card card, Integer oldPin, Integer newPin) {
        if (!card.getPin().equals(oldPin)) {
            System.err.println("╔═════════════════════════════════════════════════╗");
            System.err.println("║       Mã PIN hiện tại không chính xác.          ║");
            System.err.println("╚═════════════════════════════════════════════════╝");
            return false;
        }
        if (card.getPin().equals(newPin)) {
            System.err.println("╔═════════════════════════════════════════════════╗");
            System.err.println("║       Mã PIN mới phải khác mã PIN hiện tại.     ║");
            System.err.println("╚═════════════════════════════════════════════════╝");
            return false;
        }
        if (newPin.toString().trim().length() != 4) {
            System.err.println("╔═════════════════════════════════════════════════╗");
            System.err.println("║         Mã PIN mới phải có đúng 4 kí tự.        ║");
            System.err.println("╚═════════════════════════════════════════════════╝");
            return false;
        }

        card.setPin(newPin);
        ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
        return true;
    }
    //============================================THAY ĐỔI MÃ PIN CỦA THẺ====================================================

    public void createCard(Users user) {
        double random = Math.random() * 10000;
        Integer newCardNumber = Integer.valueOf((int) Math.floor(random));

        double randomPin = Math.random() * 10000;
        Integer newPin = Integer.valueOf((int) Math.floor(randomPin));

        Card newcard = new Card(newCardNumber, newPin, 0.0, user, Card.cardStatus.UNLOCK, Card.cardNember.BRONZE, 0);
        cards.add(newcard);
        ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║               Tạo thẻ thành công               ║");
        System.out.println("║               Số thẻ của bạn là: " + newCardNumber);
        System.out.println("║               Mã Pin: " + newPin);
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║   Xin vui lòng đến ATM gần nhất để đổi mã PIN  ║");
        System.out.println("╚════════════════════════════════════════════════╝");

    }
    //==========================================TẠO THẺ MỚI===================================================

    public void lockCard(Card card) {
        if (card.getStatus().equals(Card.cardStatus.LOCK)) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║      Thẻ đã được khóa.      ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        card.setStatus(Card.cardStatus.LOCK);
        ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║  Bạn đã khóa thẻ thành công ║");
        System.out.println("╚═════════════════════════════╝");
    }
    //============================================KHÓA THẺ====================================================

    public void openCard(Card card) {
        if (card.getStatus().equals(Card.cardStatus.UNLOCK)) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║    Thẻ đã được mở khóa      ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        card.setStatus(Card.cardStatus.UNLOCK);
        ioFileCard.writeToFile(cards, IOFile.LISTCARD_FILE);
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Bạn đã mở thẻ thành công  ║");
        System.out.println("╚═════════════════════════════╝");
    }

    //=============================================MỞ THẺ===================================================

    @Override
    public String toString() {
        return "BankSystem{" +
                "users=" + users +
                ", accounts=" + cards +
                '}';
    }


}
