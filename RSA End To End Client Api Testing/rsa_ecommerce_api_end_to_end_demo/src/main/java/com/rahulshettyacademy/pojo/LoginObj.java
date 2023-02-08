package com.rahulshettyacademy.pojo;

public class LoginObj {
    private String userEmail = "oleg.kuzmanov1@gmail.com";
    private String userPassword = "Oleg1997!";

    public LoginObj(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
