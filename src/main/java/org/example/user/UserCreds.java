package org.example.user;

public class UserCreds {

    private String email;
    private String password;

    public UserCreds(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public UserCreds setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCreds setPassword(String password) {
        this.password = password;
        return this;
    }
}
