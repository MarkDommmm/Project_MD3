package Model;

import java.io.Serializable;

public class Account implements Serializable {
    private  String accountNumber;
    public  double balance;
    private  User owner;

    public Account() {
    }

    public Account(String accountNumber, double balance, User owner) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "accountNumber ='" + accountNumber + '\'' +
                ", balance= " + balance +
                ", owner=" + owner +
                '}';
    }


}
