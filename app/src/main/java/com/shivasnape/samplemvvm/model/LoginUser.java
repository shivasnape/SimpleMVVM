package com.shivasnape.samplemvvm.model;

import android.util.Patterns;

public class LoginUser {

    private String api_key;
    private String username;
    private String password;

    public LoginUser(String userName, String password) {
        this.username = userName;
        this.password = password;
    }


    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidMobile() {
        return getUsername().length() == 10;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getUsername()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }
}
