package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction implements Serializable {
    private String transactionId;
    private LocalDateTime timestamp;
    private String fromAccount;
    private String toAccount;
    private double amount;
    private TransactionType type;
    private TransactionStatus status;

    public Transaction(String transactionId, LocalDateTime timestamp, String fromAccount, String toAccount, double amount, TransactionType type, TransactionStatus status) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
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
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
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
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                ", type=" + type + '}';
    }
}
