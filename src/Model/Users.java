package Model;

import java.io.Serializable;

public class Users implements Serializable {
    private String username;
    private String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "NumberCard " + getUsername() + "  " + "Pin " + getPassword() + "||";
    }
}
