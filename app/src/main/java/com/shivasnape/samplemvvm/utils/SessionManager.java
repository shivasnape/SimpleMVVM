package com.shivasnape.samplemvvm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "Session";

    private final String LOGIN_STATUS = "login_status";


    private final String TOKEN = "api_token";
    private final String FIREBASE_TOKEN = "firebase_token";


    @Inject
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        // Shared pref mode
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /*set session*/
    public void setLoginStatus(boolean status) {
        editor.putBoolean(LOGIN_STATUS, status);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(LOGIN_STATUS, false);
    }

    /*set token*/
    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return pref.getString(TOKEN, "");
    }

    /*firebase token*/
    public void setFirebaseToken(String token) {
        editor.putString(FIREBASE_TOKEN, token);
        editor.apply();
    }

    public String getFirebaseToken() {
        return pref.getString(FIREBASE_TOKEN, "");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
