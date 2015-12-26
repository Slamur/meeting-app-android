package com.app.android.laboratorywork3.dao.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsUtils {

    public static SharedPreferences getSettings(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    private static final String LOGIN_KEY = "Login key", DEFAULT_LOGIN = "";
    private static final String PASSWORD_KEY = "Password key", DEFAULT_PASSWORD = "";

    public static String getLogin(Context context) {
//        String login = getSettings(context).getString(LOGIN_KEY, DEFAULT_LOGIN);
//        if (DEFAULT_LOGIN.equals(login)) login = null;
//
//        return login;

        return "admin";
    }

    public static String getPassword(Context context) {
//        String password = getSettings(context).getString(PASSWORD_KEY, DEFAULT_PASSWORD);
//        if (DEFAULT_PASSWORD.equals(password)) password = null;
//
//        return password;

        return "dmina";
    }
}
