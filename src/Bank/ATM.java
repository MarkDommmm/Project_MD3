
package Bank;

import Bank.BankSystem;
import Model.Card;
import Config.InputMethods;
import Model.Transaction;
import Model.Users;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;


public class ATM implements Serializable {
    private String atmID;
    private double balanceAtm;
    private BankSystem bankSystem;
    private Users currentUsers;
    private double feeATM = 1100;

    //===========================================HỆ THỐNG ATM====================================================

    public ATM(String atmID, double balanceAtm, BankSystem bankSystem) {
        this.atmID = atmID;
        this.balanceAtm = balanceAtm;
        this.bankSystem = bankSystem;

    }


    public DecimalFormat formatBalance() {
        DecimalFormat decimalFormat;
        return new DecimalFormat("#,##0.##");
    }

    public double getBalanceAtm() {
        return balanceAtm;
    }
    //================================================================================================

    public void setBalanceAtm(double balanceAtm) {
        this.balanceAtm = balanceAtm;
    }
    //================================================================================================

    public String getAtmID() {
        return atmID;
    }

    //================================================================================================


    public void login() {
        boolean check = false;
        while (!check) {
            System.out.println("\n╔════════════════════════════╗");
            System.out.println("║         Đăng nhập          ║");
            System.out.println("╚════════════════════════════╝");
            System.out.print("Xin mời nhập tên đăng nhập: ");
            String cardNumbers = InputMethods.getString();
            System.out.print("Xin mời nhập mật khẩu: ");
            String pin = InputMethods.getString();

            Users isAuthenticated = bankSystem.authenticateUser(cardNumbers, pin);

            if (isAuthenticated != null) {
                currentUsers = isAuthenticated;
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Đăng nhập thành công.    ║");
                System.out.println("╚═════════════════════════════╝");
                check = true;
            } else {
                System.err.println("\n╔═══════════════════════════════════════════╗");
                System.err.println("║ Number Cards hoặc mã PIN không chính xác. ║");
                System.err.println("╚═══════════════════════════════════════════╝");
            }
        }
    }
    //===================================================================ĐĂNG NHẬP=============================

    public void performTransaction() {
        if (currentUsers == null) {
            System.err.println("╔═══════════════════════════════════════════════════╗");
            System.err.println("║ Vui lòng đăng nhập trước khi thực hiện giao dịch. ║");
            System.err.println("╚═══════════════════════════════════════════════════╝");
            return;
        }
        while (true) {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║ Xin chào, " + currentUsers.getUsername());
            System.out.println("║ Chọn một tùy chọn giao dịch:║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║   1. Rút tiền               ║");
            System.out.println("║   2. Nạp tiền               ║");
            System.out.println("║   3. Chuyển tiền            ║");
            System.out.println("║   4. Kiểm tra số dư         ║");
            System.out.println("║   5. Xem lịch sử giao dịch  ║");
            System.out.println("║   6. Quản lý thẻ            ║");
            System.out.println("║   7. Quản lý người dùng     ║");
            System.out.println("║   0. Đăng xuất!!!           ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.print("Mời lựa chọn: ");

            byte option = InputMethods.getByte();
            switch (option) {
                case 1:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║          Rút tiền          ║");
                    System.out.println("╚════════════════════════════╝");
                    Withdrawal();
                    break;
                case 2:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║          Nạp tiền          ║");
                    System.out.println("╚════════════════════════════╝");
                    deposit();
                    break;
                case 3:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║         Chuyển tiền        ║");
                    System.out.println("╚════════════════════════════╝");
                    Transfer();
                    break;
                case 4:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║       Kiểm tra số dư       ║");
                    System.out.println("╚════════════════════════════╝");
                    checkBalance();
                    break;
                case 5:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║    Xem lịch sử giao dịch   ║");
                    System.out.println("╚════════════════════════════╝");
                    viewTransactionHistory();
                    break;
                case 6:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║        Quản lý thẻ         ║");
                    System.out.println("╚════════════════════════════╝");
                    managementCard();
                    break;
                case 7:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║     Quản lý người dùng     ║");
                    System.out.println("╚════════════════════════════╝");
                    managementUser();
                    break;
                case 0:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║        Đã đăng xuất.       ║");
                    System.out.println("╚════════════════════════════╝");
                    return;
                default:
                    System.err.println("╔════════════════════════════╗");
                    System.err.println("║    Lựa chọn không hợp lệ.  ║");
                    System.err.println("╚════════════════════════════╝");
            }
        }
    }
    //======================================================================VIEW ATM==========================

    public void viewTransactionHistory() {
        if (currentUsers == null) {
            System.out.println("Vui lòng đăng nhập để xem lịch sử giao dịch.");
            return;
        }

        List<Card> cards = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cards.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║ Người dùng không có tài khoản ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        Card card = selectAccount(cards);
        if (card == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        System.out.println("\n╔═════════════════════════════════════╗");
        System.out.println("║ Bạn muốn xem lịch sử giao dịch nào?:║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║   1. Xem Tất cả giao dịch           ║");
        System.out.println("║   2. Xem tiền vào tài khoản         ║");
        System.out.println("║   3. Xem tiền ra khỏi tài khoản     ║");
        System.out.println("║   0. Quay lại!!!                    ║");
        System.out.println("╚═════════════════════════════════════╝");
        int choice = InputMethods.getInteger();
        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║           Lịch sử giao dịch của tài khoản           ║");
        System.out.println("╠═════════════════════════════════════════════════════╣");
        switch (choice) {
            case 1:
                viewAllTransactions(card);
                break;
            case 2:
                viewIncomingTransactions(card);
                break;
            case 3:
                viewOutgoingTransactions(card);
                break;
            case 0:
                System.out.println("Quay lại");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        }
    }
    //==================================================================VIEW LỊCH SỬ GIAO DỊCH==========================

    private void viewAllTransactions(Card card) {
        boolean hasTransaction = false;

        for (Transaction transaction : bankSystem.getTransaction()) {
            if (card.getCardNumber().equals(transaction.getFromAccount())) {
                hasTransaction = true;

                System.out.println("║ Mã giao dịch: " + transaction.getTransactionId());
                System.out.println("║ Ngày & Giờ: " + transaction.getTimestamp());
                System.out.println("║ Số tài khoản : " + transaction.getFromAccount());
                System.out.println("║ Giao dịch : " + transaction.getType());
                System.out.println("║ Số tiền giao dịch: " + formatBalance().format(transaction.getAmount()) + " VNĐ");
                System.out.println("║ Số dư còn lại: " + formatBalance().format(transaction.getBalance()) + " VNĐ");
                System.out.println("║ Phí giao dịch: " + formatBalance().format(feeATM));
                System.out.println("║ Trạng thái: " + transaction.getStatus());
                System.out.println("╠═════════════════════════════════════════════════════╣");
            }
        }
        if (!hasTransaction) {
            System.out.println("║              Không có giao dịch nào                 ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
        }

    }
    //====================================================================VIEW TẤT CẢ GIAO DỊCH==========================

    private void viewIncomingTransactions(Card card) {
        boolean hasTransaction = false;
        for (Transaction transaction : bankSystem.getTransaction()) {
            //Tiền vào tài khoản
            if (transaction.getType().equals(Transaction.TransactionType.DEPOSIT) ||
                    transaction.getType().equals(Transaction.TransactionType.TRANSFER) &&
                            card.getCardNumber().equals(transaction.getToAccount())) {
                hasTransaction = true;

                System.out.println("║ Mã giao dịch: " + transaction.getTransactionId());
                System.out.println("║ Ngày & Giờ: " + transaction.getTimestamp());
                if (transaction.getType().equals(Transaction.TransactionType.TRANSFER)) {
                    System.out.println("║ Số tài khoản gửi : " + transaction.getFromAccount());
                    System.out.println("║ Số tài khoản nhận : " + transaction.getToAccount());
                } else {
                    System.out.println("║ Số tài khoản : " + transaction.getFromAccount());
                }
                System.out.println("║ Giao dịch : " + (transaction.getType().equals(Transaction.TransactionType.DEPOSIT) ? "Nạp tiền" : "Chuyển tiền"));
                System.out.println("║ Số tiền giao dịch: " + formatBalance().format(transaction.getAmount()) + " VNĐ");
                System.out.println("║ Số dư còn lại: " + formatBalance().format(transaction.getBalance()) + " VNĐ");
                System.out.println("║ Phí giao dịch: " + formatBalance().format(feeATM));
                System.out.println("║ Trạng thái: " + transaction.getStatus());
                System.out.println("╚═════════════════════════════════════════════════════╝");
            }

        }

        if (!hasTransaction) {
            System.out.println("╔═════════════════════════════════════════════════════╗");
            System.out.println("║              Không có giao dịch nào                 ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
        }
    }
    //===================================================================VIEW GIAO DỊCH TIỀN VÀO==========================

    private void viewOutgoingTransactions(Card card) {
        boolean hasTransaction = false;
        for (Transaction transaction : bankSystem.getTransaction()) {
            //Tiền ra khỏi tài khoản
            if (transaction.getType().equals(Transaction.TransactionType.WITHDRAWAL) ||
                    transaction.getType().equals(Transaction.TransactionType.TRANSFER) &&
                            card.getCardNumber().equals(transaction.getFromAccount())) {
                hasTransaction = true;
                System.out.println("║ Mã giao dịch: " + transaction.getTransactionId());
                System.out.println("║ Ngày & Giờ: " + transaction.getTimestamp());
                if (transaction.getType().equals(Transaction.TransactionType.TRANSFER)) {
                    System.out.println("║ Số tài khoản gửi : " + transaction.getFromAccount());
                    System.out.println("║ Số tài khoản nhận : " + transaction.getToAccount());
                } else {
                    System.out.println("║ Số tài khoản : " + transaction.getFromAccount());
                }
                System.out.println("║ Giao dịch : " + (transaction.getType().equals(Transaction.TransactionType.WITHDRAWAL) ? "Rút tiền" : "Chuyển tiền"));
                System.out.println("║ Số tiền giao dịch: " + formatBalance().format(transaction.getAmount()) + " VNĐ");
                System.out.println("║ Số dư còn lại: " + formatBalance().format(transaction.getBalance()) + " VNĐ");
                System.out.println("║ Phí giao dịch: " + formatBalance().format(feeATM));
                System.out.println("║ Trạng thái: " + transaction.getStatus());
                System.out.println("╚═════════════════════════════════════════════════════╝");

            }
        }

        if (!hasTransaction) {
            System.out.println("╔═════════════════════════════════════════════════════╗");
            System.out.println("║              Không có giao dịch nào                 ║");
            System.out.println("╚═════════════════════════════════════════════════════╝");
        }
    }

    //===================================================================VIEW GIAO DỊCH TIỀN RA==========================

    private void Withdrawal() {
        List<Card> cards = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cards.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║Người dùng không có tài khoản║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        Card card = selectAccount(cards);


        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Nhập số tiền muốn rút!!!  ║");
        System.out.println("╚═════════════════════════════╝");
        double money = InputMethods.getDouble();
        if (money > 5000000) {
            System.err.println("\n╔═════════════════════════════════════════╗");
            System.err.println("║    Số tiền rút quá hạn mức của thẻ      ║");
            System.err.println("╚═════════════════════════════════════════╝");
            return;
        } else if (money < 20000) {
            System.err.println("╔═════════════════════════════════════════╗");
            System.err.println("║    Số tiền rút tối thiểu 20,000 VNĐ     ║");
            System.err.println("╚═════════════════════════════════════════╝");
            return;
        }

        if (card == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");

        }
        if (card.getWitHDrawalCountLimit() >= card.getMaxWithdrawalPerDay()) {
            System.err.println("Hạn mức rút tiền trong ngày là " + formatBalance().format(card.getMaxWithdrawalPerDay()) + " VNĐ");
            return;
        }
        System.out.println(formatBalance().format(card.getWitHDrawalCountLimit()) + "hạn mức của  bạn ");
        if (getBalanceAtm() >= money) {
            setBalanceAtm(getBalanceAtm() - money);
        } else {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║  Số dư ATM không đủ để rút. ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        if (bankSystem.withdrawal(card, money, feeATM)) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║  Rút tiền thành công. Số dư mới " + formatBalance().format(card.getBalance()) + " VNĐ");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("Bạn có muốn in hóa đơn không????");
            System.out.println("Nhấn 1 để in hóa đơn!!!");
            System.out.println("Nhấn 1 số bất kì để ra menu chính");
            //Ghi vào lịch sử giao dịch
            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();
            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    card.getCardNumber(),
                    null,
                    money,
                    card.getBalance(),
                    feeATM,
                    Transaction.TransactionType.WITHDRAWAL,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);


            int check = InputMethods.getInteger();

            if (check == 1) {
                printReceipt(money, card.getBalance(), card.getCardNumber());
            } else {
                System.out.println("╔═════════════════════════════════════════╗");
                System.out.println("║  Cảm ơn quý khách đã sử dụng dịch vụ!   ║");
                System.out.println("╚═════════════════════════════════════════╝");

            }

        } else {
            System.err.println("╔═══════════════════════════════════════╗");
            System.err.println("║   Không thể rút tiền vào tài khoản.   ║");
            System.err.println("╚═══════════════════════════════════════╝");

        }
    }

    //========================================================RÚT TIỀN========================================

    private void checkBalance() {
        List<Card> cards = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cards.isEmpty()) {
            System.err.println("╔═════════════════════════════════════╗");
            System.err.println("║   Người dùng không có tài khoản!!!  ║");
            System.err.println("╚═════════════════════════════════════╝");
            return;
        }
//
        Card card = selectAccount(cards);
        if (card == null) {
            System.err.println("╔══════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ!!!  ║");
            System.err.println("╚══════════════════════════════╝");
            return;
        }
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║                Kiểm tra số dư tài khoản            ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Số dư của tài khoản " + card.getCardNumber() + " là: " + formatBalance().format(card.getBalance()) + " VNĐ");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    //==================================================CHECK SỐ DƯ==============================================
    private void Transfer() {
        List<Card> cards = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cards.isEmpty()) {
            System.err.println("╔════════════════════════════════════╗");
            System.err.println("║   Người dùng không có tài khoản.   ║");
            System.err.println("╚════════════════════════════════════╝");
            return;
        }
        Card sourceCard = selectAccount(cards);
        if (sourceCard == null) {
            System.out.println("Tài khoản không hợp lệ.");
            return;
        }
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║                 Chuyển tiền                ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.print("║ Nhập số tài khoản người nhận:");
        Integer stk = InputMethods.getInteger();
        System.out.print("║ Nhập số tiền muốn chuyển: ");
        double amount = InputMethods.getDouble();
        System.out.print("╚════════════════════════════════════════════╝");

        Card toCard = bankSystem.getAccountByNumber(stk);
        if (toCard == null) {
            System.err.println("\n╔═══════════════════════════════════════════╗");
            System.err.println("║ Tài khoản người nhận không hợp lệ.");
            System.err.println("╚═══════════════════════════════════════════╝");
            return;
        }
        if (sourceCard.equals(toCard)) {
            System.err.println("\n╔══════════════════════════════════════╗");
            System.err.println("║   không thế chuyển trùng tài khoản   ║");
            System.err.println("╚══════════════════════════════════════╝");
            return;
        }

        if (bankSystem.Transfer(sourceCard, toCard, amount)) {
            System.out.println("\n║ Chuyển tiền thành công.");
            System.out.println("║ Số dư mới của tài khoản " + sourceCard.getCardNumber() + " là: " + formatBalance().format(sourceCard.getBalance()) + " VNĐ");

            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();

            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    sourceCard.getCardNumber(),
                    toCard.getCardNumber(),
                    amount,
                    sourceCard.getBalance(),
                    feeATM,
                    Transaction.TransactionType.TRANSFER,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);


        } else {
            System.err.println("║ Không thể chuyển tiền từ tài khoản.");
        }
        System.out.println("╚════════════════════════════════════════════╝");

    }

    //===================================================CHUYỂN TIỀN=============================================
    private void deposit() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║    nhập số tiền muốn nạp   ║");
        System.out.println("╚════════════════════════════╝");
        double amount = InputMethods.getDouble();

        List<Card> cardList = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cardList.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║Người dùng không có tài khoản║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        Card card = selectAccount(cardList);
        if (card == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        if (bankSystem.deposit(card, amount)) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║  Nạp tiền thành công. Số dư mới " + formatBalance().format(card.getBalance()) + " VNĐ");
            System.out.println("╚══════════════════════════════════════════╝");

            //Ghi vào lịch sử giao dịch

            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();
            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    card.getCardNumber(),
                    null,
                    amount,
                    card.getBalance(),
                    feeATM,
                    Transaction.TransactionType.DEPOSIT,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);

        } else {
            System.err.println("╔═══════════════════════════════════════╗");
            System.err.println("║   Không thể nạp tiền vào tài khoản.   ║");
            System.err.println("╚═══════════════════════════════════════╝");
        }
    }
    //====================================================NẠP TIỀN============================================

    public void printReceipt(double money, double balance, Integer card) {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║                    Hóa đơn                ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║ Ngày & Giờ: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("║ Số tài khoản: " + card);
        System.out.println("║ Số tiền rút: " + formatBalance().format(money) + " VNĐ");
        System.out.println("║ Số dư còn lại: " + formatBalance().format(balance) + " VNĐ");
        System.out.println("║ Phí giao dịch: " + formatBalance().format(feeATM)+" VNĐ");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║     Cảm ơn quý khách đã sử dụng dịch vụ!  ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    //===================================================IN HÓA ĐƠN=============================================
    private Card selectAccount(List<Card> cards) {
        if (cards.size() == 0) {
            System.out.println("Không có thẻ");
            return null;
        }

        System.out.println("Danh sách thẻ: ");
        for (int i = 0; i < cards.size(); i++) {
            System.out.println((i + 1) + ". " + cards.get(i).getCardNumber() + " - " + cards.get(i).getStatus());
        }
        System.out.println("0. Quay lại!!!");
        Card selectedCard = null;
        boolean cardSelected = false;

        while (!cardSelected) {
            System.out.println("Chọn thẻ (1-" + cards.size() + "): ");

            int choice = InputMethods.getInteger();
            if (choice == 0) {
                performTransaction();
            }
            if (choice >= 1 && choice <= cards.size()) {
                selectedCard = cards.get(choice - 1);

                if (selectedCard.getStatus().equals(Card.cardStatus.LOCK)) {
                    System.err.println("Thẻ đang bị khóa. Vui lòng chọn thẻ khác.");
                } else {
                    cardSelected = true;
                }
            } else {
                System.out.println("Lựa chọn không hợp lệ.");
            }
        }

        boolean pinCheck = false;

        while (!pinCheck) {
            System.out.println("Hãy nhập mã PIN!!!");
            Integer pin = InputMethods.getInteger();

            if (selectedCard.getPin().equals(pin)) {
                pinCheck = true;
            } else {
                System.out.println("Mã PIN không chính xác. Vui lòng thử lại.");
            }
        }

        return selectedCard;
    }

    //================================================CHỌN THẺ================================================
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //=======================================QUẢN LÝ NGƯỜI DÙNG=========================================================
    private void managementUser() {
        while (true) {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║      Chọn một tùy chọn:     ║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║   1. Đổi mật khẩu           ║");
            System.out.println("║   0. Quay lại               ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.print("Mời lựa chọn: ");

            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    changePassword();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Vui lòng nhập lại lựa chọn");
            }
        }

    }
    //=========================================MENU NGƯỜI DÙNG=======================================================

    private void changePassword() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin Cũ!!!     ║");
        System.out.println("╚════════════════════════════╝");
        String oldPin = InputMethods.getString();

        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin mới!!!    ║");
        System.out.println("╚════════════════════════════╝");
        String newPin = InputMethods.getString();

        if (bankSystem.changePass(currentUsers, oldPin, newPin)) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║        Thay đổi mã PIN thành công.         ║");
            System.out.println("╚════════════════════════════════════════════╝");
        } else {
            System.err.println("╔════════════════════════════════════════════╗");
            System.err.println("║        Không thể thay đổi mã PIN.          ║");
            System.err.println("╚════════════════════════════════════════════╝");
        }
    }
    //=============================================THAY ĐỔI MẬT KHẨU NGƯỜI DÙNG===================================================
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    private void managementCard() {
        while (true) {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║     Chọn một tùy chọn :     ║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║   1. Đổi mã PIN             ║");
            System.out.println("║   2. Tạo thẻ mới            ║");
            System.out.println("║   3. Khóa thẻ               ║");
            System.out.println("║   4. Mở thẻ                 ║");
            System.out.println("║   0. Quay lại               ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.print("Mời lựa chọn: ");

            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    changePin();
                    break;
                case 2:
                    createCard();
                    break;
                case 3:
                    lockCard();
                    break;
                case 4:
                    openCard();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Vui lòng nhập lại lựa chọn");
            }
        }
    }
    //=========================================MENU THẺ NGÂN HÀNG=======================================================

    private void lockCard() {
        List<Card> cardList = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cardList.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Người dùng không có thẻ   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        System.out.println("╔═════════════════╗");
        System.out.println("║    Khóa thẻ     ║");
        System.out.println("╚═════════════════╝");
        Card card = selectAccount(cardList);
        if (card == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║      Thẻ không hợp lệ.      ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        bankSystem.lockCard(card);

    }
    //=========================================KHÓA THẺ =======================================================

    private void openCard() {
        List<Card> cardList = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cardList.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Người dùng không có thẻ   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        System.out.println("╔═════════════════╗");
        System.out.println("║      Mở thẻ     ║");
        System.out.println("╚═════════════════╝");
        System.out.println("Danh sách thẻ: ");
        for (int i = 0; i < cardList.size(); i++) {
            System.out.println((i + 1) + ". " + cardList.get(i).getCardNumber() + " - " + cardList.get(i).getStatus());
        }
        System.out.println("0. Quay lại");

        System.out.println("Chọn thẻ (1-" + cardList.size() + "): ");
        int choice = InputMethods.getInteger();
        if (choice == 0) {
            performTransaction();
        }
        if (choice >= 1 && choice <= cardList.size()) {
            Card selectedCard = cardList.get(choice - 1);
            bankSystem.openCard(selectedCard);
        } else {
            System.out.println("Lựa chọn không hợp lệ.");
        }
    }
//=========================================MỞ THẺ =======================================================

    private void createCard() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║         Tạo thẻ mới        ║");
        System.out.println("╚════════════════════════════╝");
        bankSystem.createCard(currentUsers);

    }

    //=========================================TẠO THẺ MỚI =======================================================
    private void changePin() {
        List<Card> cards = bankSystem.getUserAccounts(currentUsers.getUsername());
        if (cards.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Người dùng không có thẻ   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        Card card = selectAccount(cards);
        if (card == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║      Thẻ không hợp lệ.      ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }


        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin Cũ!!!     ║");
        System.out.println("╚════════════════════════════╝");
        Integer oldPin = InputMethods.getInteger();

        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin mới!!!    ║");
        System.out.println("╚════════════════════════════╝");
        Integer newPin = InputMethods.getInteger();

        if (bankSystem.changePin(card, oldPin, newPin)) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║        Thay đổi mã PIN thành công.         ║");
            System.out.println("╚════════════════════════════════════════════╝");
        } else {
            System.err.println("╔════════════════════════════════════════════╗");
            System.err.println("║        Không thể thay đổi mã PIN.          ║");
            System.err.println("╚════════════════════════════════════════════╝");
        }
    }

    //=========================================THAY ĐÔI MÃ PIN THẺ =======================================================

    @Override
    public String toString() {
        return "ATM{" +
                "atmID='" + atmID + '\'' +
                '}';
    }
}
