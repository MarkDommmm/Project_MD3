package Model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Card implements Serializable {
    private Integer cardNumber;
    private Integer pin;
    public double balance;
    private Users owner;
    private cardStatus status;
    private cardNember cardNember;
    private double withdrawalPerDay;

    private double witHDrawalCountLimit;

    public Card() {
    }


    public Card(Integer cardNumber, Integer pin, double balance, Users owner, cardStatus status, cardNember cardNember, double witHDrawalCountLimit) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.owner = owner;
        this.status = status;
        this.cardNember = cardNember;
        this.witHDrawalCountLimit = witHDrawalCountLimit;
        this.withdrawalPerDay = cardNember.getWithdrawalLimit();
    }

    public double getWitHDrawalCountLimit() {
        return witHDrawalCountLimit;
    }

    public void setWitHDrawalCountLimit(double witHDrawalCountLimit) {
        this.witHDrawalCountLimit = witHDrawalCountLimit;
    }

    public double getMaxWithdrawalPerDay() {
        return cardNember.getWithdrawalLimit();
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public cardStatus getStatus() {
        return status;
    }

    public void setStatus(cardStatus status) {
        this.status = status;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Users getOwner() {
        return owner;
    }

    public enum cardStatus {
        UNLOCK, LOCK, BANNED
    }

    public enum cardNember {
        BRONZE(20000000),
        SILVER(50000000),
        GOLD(100000000);

        private final double withdrawalLimit;

        cardNember(double withdrawalLimit) {
            this.withdrawalLimit = withdrawalLimit;
        }

        public double getWithdrawalLimit() {
            return withdrawalLimit;
        }
    }


    @Override
    public String toString() {
        return "cardNumber ='" + cardNumber + '\'' +
                ", balance= " + balance +
                ", owner=" + owner +
                '}';
    }


}
