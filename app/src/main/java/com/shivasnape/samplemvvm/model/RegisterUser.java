package com.shivasnape.samplemvvm.model;

import android.util.Patterns;


public class RegisterUser {

    private String api_key;
    private String mCompanyName;
    private String mMobile;
    private String mEmail;
    private String mPassword;

    public RegisterUser(String companyName, String mobile, String email, String password) {
        mCompanyName = companyName;
        mMobile = mobile;
        mEmail = email;
        mPassword = password;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean isValidMobile() {
        return getmMobile().length() == 10;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getmEmail()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getmPassword().length() > 5;
    }

}
