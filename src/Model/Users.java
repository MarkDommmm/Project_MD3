package Model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer cardNumbers;
    private Integer pin;

    public User(Integer cardNumbers, Integer pin) {
        this.cardNumbers = cardNumbers;
        this.pin = pin;
    }

    public User() {

    }

    public Integer getCardNumbers() {
        return cardNumbers;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }


    @Override
    public String toString() {
        return "NumberCard " + getCardNumbers() + "  " + "Pin " + getPin() + "||";
    }
}
