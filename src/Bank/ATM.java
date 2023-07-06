
package Bank;

import Database.IOFile;
import Model.Account;
import Config.InputMethods;
import Model.Transaction;
import Model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ATM implements Serializable {
    private String atmID;
    private double balanceAtm;
    private BankSystem bankSystem;
    private User currentUser;


    public ATM(String atmID, double balanceAtm, BankSystem bankSystem) {
        this.atmID = atmID;
        this.balanceAtm = balanceAtm;
        this.bankSystem = bankSystem;

    }

    public ATM() {
    }

    public double getBalanceAtm() {
        return balanceAtm;
    }

    public void setBalanceAtm(double balanceAtm) {
        this.balanceAtm = balanceAtm;
    }

    public String getAtmID() {
        return atmID;
    }

    public void viewTransactionHistory() {
        if (currentUser == null) {
            System.out.println("Vui lòng đăng nhập để xem lịch sử giao dịch.");
            return;
        }
        List<Account> accounts = bankSystem.getUserAccounts(currentUser.getUsername());
        if (accounts.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║Người dùng không có tài khoản║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        Account account = selectAccount(accounts);
        if (account == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");

        }


        System.out.println("╔═════════════════════════════════════════════════════╗");
        System.out.println("║           Lịch sử giao dịch của tài khoản           ║");
        System.out.println("╠═════════════════════════════════════════════════════╣");

        for (Transaction transaction : bankSystem.getTransaction()) {
            if (account.getAccountNumber().equals(transaction.getFromAccount())) {
                System.out.println("║ Mã giao dịch: " + transaction.getTransactionId());
                System.out.println("║ Ngày & Giờ: " + transaction.getTimestamp());
                System.out.println("║ Số tài khoản : " + transaction.getFromAccount());
                System.out.println("║ Giao dịch : " + transaction.getType());
                System.out.println("║ Số tiền giao dịch: " + transaction.getAmount());
                System.out.println("║ Trạng thái: " + transaction.getStatus());
                System.out.println("╚═══════════════════════════════════════════╝");
            }
        }
    }


    public void login() {
        boolean check = false;
        while (!check) {
            System.out.println("╔════════════════════════════╗");
            System.out.println("║         Đăng nhập          ║");
            System.out.println("╚════════════════════════════╝");
            System.out.print("Number Cards: ");
            String username = InputMethods.getString();
            System.out.print("PIN: ");
            String password = InputMethods.getString();

            User isAuthenticated = bankSystem.authenticateUser(username, password);

            if (isAuthenticated != null) {
                currentUser = isAuthenticated;
                System.out.println("╔═════════════════════════════╗");
                System.out.println("║    Đăng nhập thành công.    ║");
                System.out.println("╚═════════════════════════════╝");
                check = true;
            } else {
                System.err.println("╔═══════════════════════════════════════════╗");
                System.err.println("║ Number Cards hoặc mã PIN không chính xác. ║");
                System.err.println("╚═══════════════════════════════════════════╝");
            }
        }
    }

    public void performTransaction() {
        if (currentUser == null) {
            System.err.println("╔═══════════════════════════════════════════════════╗");
            System.err.println("║ Vui lòng đăng nhập trước khi thực hiện giao dịch. ║");
            System.err.println("╚═══════════════════════════════════════════════════╝");
            return;
        }
        while (true) {
            System.out.println("╔═════════════════════════════╗");
            System.out.println("║ Xin chào, " + currentUser.getUsername());
            System.out.println("║ Chọn một tùy chọn giao dịch:║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║   1. Rút tiền               ║");
            System.out.println("║   2. Nạp tiền               ║");
            System.out.println("║   3. Chuyển tiền            ║");
            System.out.println("║   4. Kiểm tra số dư         ║");
            System.out.println("║   5. Thay đỗi mã PIN        ║");
            System.out.println("║   6. Xem lịch sử giao dịch  ║");
            System.out.println("║   0. Thoát                  ║");
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
                    System.out.println("║       Thay đổi mã PIN      ║");
                    System.out.println("╚════════════════════════════╝");
                    changePing();
                    break;
                case 6:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║    Xem lịch sử giao dịch   ║");
                    System.out.println("╚════════════════════════════╝");
                    viewTransactionHistory();
                    break;
                case 0:
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║          Đã thoát.         ║");
                    System.out.println("╚════════════════════════════╝");
                    return;
                default:
                    System.err.println("╔════════════════════════════╗");
                    System.err.println("║    Lựa chọn không hợp lệ.  ║");
                    System.err.println("╚════════════════════════════╝");
            }
        }
    }

    private void Withdrawal() {
        List<Account> accounts = bankSystem.getUserAccounts(currentUser.getUsername());
        if (accounts.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║Người dùng không có tài khoản║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }

        Account account = selectAccount(accounts);
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   Nhập số tiền muốn rút!!!  ║");
        System.out.println("╚═════════════════════════════╝");
        double money = InputMethods.getDouble();

        if (account == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");

        }

        if (getBalanceAtm() >= money) {
            setBalanceAtm(getBalanceAtm() - money);
        } else {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║  Số dư ATM không đủ để rút. ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        System.out.println("ATM: " + getAtmID() + "Balance: " + getBalanceAtm() + "");
        if (bankSystem.withdrawal(account, money)) {


            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║  Rút tiền thành công. Số dư mới " + account.getBalance());
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("Bạn có muốn in hóa đơn không?(true/false)");


            //Ghi vào lịch sử giao dịch
            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();
            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    account.getAccountNumber(),
                    "",
                    money,
                    Transaction.TransactionType.WITHDRAWAL,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);


            boolean check = InputMethods.getBoolean();
            if (check == true) {
                printReceipt(money, account.getBalance(), account.getAccountNumber());
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

    public void printReceipt(double money, double balance, String account) {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║                    Hóa đơn                ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║ Ngày & Giờ: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("║ Số tài khoản: " + account);
        System.out.println("║ Số tiền rút: " + money);
        System.out.println("║ Số dư còn lại: " + balance);
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║     Cảm ơn quý khách đã sử dụng dịch vụ!  ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    private void checkBalance() {
        List<Account> accounts = bankSystem.getUserAccounts(currentUser.getUsername());
        if (accounts.isEmpty()) {
            System.err.println("╔═════════════════════════════════════╗");
            System.err.println("║   Người dùng không có tài khoản!!!  ║");
            System.err.println("╚═════════════════════════════════════╝");
            return;
        }

        Account account = selectAccount(accounts);
        if (account == null) {
            System.err.println("╔══════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ!!!  ║");
            System.err.println("╚══════════════════════════════╝");
            return;
        }
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║                Kiểm tra số dư tài khoản            ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Số dư của tài khoản " + account.getAccountNumber() + " là: " + account.getBalance() + " $");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }


    private void Transfer() {
        List<Account> accounts = bankSystem.getUserAccounts(currentUser.getUsername());
        if (accounts.isEmpty()) {
            System.err.println("╔════════════════════════════════════╗");
            System.err.println("║   Người dùng không có tài khoản.   ║");
            System.err.println("╚════════════════════════════════════╝");
            return;
        }
        Account sourceAccount = selectAccount(accounts);
        if (sourceAccount == null) {
            System.out.println("Tài khoản không hợp lệ.");
            return;
        }
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║                 Chuyển tiền                ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.print("║ Nhập số tài khoản người nhận:");
        String stk = InputMethods.getString();
        System.out.print("║ Nhập số tiền muốn chuyển: ");
        double amount = InputMethods.getDouble();

        Account toAccount = bankSystem.getAccountByNumber(stk);
        if (toAccount == null) {
            System.err.println("║ Tài khoản người nhận không hợp lệ.");
            System.err.println("╚════════════════════════════════════════════╝");
            return;
        }

        if (bankSystem.Transfer(sourceAccount, toAccount, amount)) {
            System.out.println("║ Chuyển tiền thành công.");
            System.out.println("║ Số dư mới của tài khoản " + sourceAccount.getAccountNumber() + " là: " + sourceAccount.getBalance());

            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();

            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    sourceAccount.getAccountNumber(),
                    toAccount.getAccountNumber(),
                    amount,
                    Transaction.TransactionType.TRANSFER,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);


        } else {
            System.err.println("║ Không thể chuyển tiền từ tài khoản.");
        }
        System.out.println("╚════════════════════════════════════════════╝");

    }

    private void deposit() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║    nhập số tiền muốn nạp   ║");
        System.out.println("╚════════════════════════════╝");
        double amount = InputMethods.getDouble();

        List<Account> accountList = bankSystem.getUserAccounts(currentUser.getUsername());
        if (accountList.isEmpty()) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║Người dùng không có tài khoản║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        Account account = selectAccount(accountList);
        if (account == null) {
            System.err.println("╔═════════════════════════════╗");
            System.err.println("║   Tài khoản không hợp lệ.   ║");
            System.err.println("╚═════════════════════════════╝");
            return;
        }
        if (bankSystem.deposit(account, amount)) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║  Nạp tiền thành công. Số dư mới " + account.getBalance());
            System.out.println("╚══════════════════════════════════════════╝");

            //Ghi vào lịch sử giao dịch
            String transactionId = Transaction.generateTransactionId();
            LocalDateTime timestamp = LocalDateTime.now();
            Transaction transaction = new Transaction(
                    transactionId,
                    timestamp,
                    account.getAccountNumber(),
                    "",
                    amount,
                    Transaction.TransactionType.DEPOSIT,
                    Transaction.TransactionStatus.SUCCESS);
            bankSystem.processTransaction(transaction);

        } else {
            System.err.println("╔═══════════════════════════════════════╗");
            System.err.println("║   Không thể nạp tiền vào tài khoản.   ║");
            System.err.println("╚═══════════════════════════════════════╝");
        }
    }

    private void changePing() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin Cũ!!!     ║");
        System.out.println("╚════════════════════════════╝");
        String oldPin = InputMethods.getString();

        System.out.println("╔════════════════════════════╗");
        System.out.println("║      Nhập mã Pin mới!!!    ║");
        System.out.println("╚════════════════════════════╝");
        String newPin = InputMethods.getString();

        if (bankSystem.changePin(currentUser, oldPin, newPin)) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║        Thay đổi mã PIN thành công.         ║");
            System.out.println("╚════════════════════════════════════════════╝");
        } else {
            System.err.println("╔════════════════════════════════════════════╗");
            System.err.println("║        Không thể thay đổi mã PIN.          ║");
            System.err.println("╚════════════════════════════════════════════╝");
        }
    }


    private Account selectAccount(List<Account> accounts) {
        if (accounts.size() == 0) {
            System.out.println("Không có tài khoản");
            return null;
        }

        System.out.println("Danh sách tài khoản: ");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i).getAccountNumber());
        }

        System.out.println("Chọn tài khoản (1-" + accounts.size() + "): ");
        int choice = InputMethods.getInteger();

        if (choice >= 1 && choice <= accounts.size()) {
            return accounts.get(choice - 1);
        } else {
            System.out.println("Lựa chọn không hợp lệ.");
            return null;
        }
    }


    @Override
    public String toString() {
        return "ATM{" +
                "atmID='" + atmID + '\'' +
                '}';
    }
}
