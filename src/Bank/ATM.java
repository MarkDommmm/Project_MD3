
package Model;

import Bank.BankSystem;
import Model.Card;
import Config.InputMethods;
import Model.Transaction;
import Model.Users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;


public class ATM implements Serializable {
    private String atmID;
    private double balanceAtm;
    private BankSystem bankSystem;
    private Users currentUsers;


    public ATM(String atmID, double balanceAtm, BankSystem bankSystem) {
        this.atmID = atmID;
        this.balanceAtm = balanceAtm;
        this.bankSystem = bankSystem;

    }

    public ATM() {
    }

    public String getAtmID() {
        return atmID;
    }

    public void setAtmID(String atmID) {
        this.atmID = atmID;
    }

    public double getBalanceAtm() {
        return balanceAtm;
    }

    public void setBalanceAtm(double balanceAtm) {
        this.balanceAtm = balanceAtm;
    }

    public BankSystem getBankSystem() {
        return bankSystem;
    }

    public void setBankSystem(BankSystem bankSystem) {
        this.bankSystem = bankSystem;
    }

    public Users getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(Users currentUsers) {
        this.currentUsers = currentUsers;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "atmID='" + atmID + '\'' +
                '}';
    }
}
