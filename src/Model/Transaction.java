package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable {
    private String transactionId;
    private LocalDateTime timestamp;
    private Integer fromCard;
    private Integer toCard;
    private double amount;
    private double balance;
    private  double feeService;
    private TransactionType type;
    private TransactionStatus status;

    public Transaction(String transactionId, LocalDateTime timestamp, Integer fromCard, Integer toCard, double amount,double balance,double fee, TransactionType type, TransactionStatus status) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.fromCard = fromCard;
        this.toCard = toCard;
        this.amount = amount;
        this.balance = balance;
        this.feeService = fee;
        this.type = type;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getBalance() {
        return balance;
    }

    public Integer getFromAccount() {
        return fromCard;
    }

    public Integer getToAccount() {
        return toCard;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public static String generateTransactionId() {
        double random = Math.random() * 100000000;
        String covert = String.valueOf((int) Math.floor(random));
        String newCardNumber = "GĐ."+ covert;
        return newCardNumber.toString();
    }

    public enum TransactionType {
        WITHDRAWAL,
        DEPOSIT,
        TRANSFER
    }

    public enum TransactionStatus {
        SUCCESS,
        FAILED,
        WAITING,
    }

    @Override
    public String toString() {
        return "{" + "transactionId='" + transactionId + '\'' +
                ", timestamp=" + timestamp +
                ", fromAccount='" + fromCard + '\'' +
                ", toAccount='" + toCard + '\'' +
                ", amount=" + amount +
                ", type=" + type + '}';
    }
}
