package com.app.android.laboratorywork3.dao.rest;

import android.content.Context;
import android.util.Base64;

import com.app.android.laboratorywork3.dao.settings.SettingsUtils;

import retrofit.RequestInterceptor;

public class ApiRequestInterceptor implements RequestInterceptor {

    private static final String BASIC_PREFIX = "Basic ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static String encodeCredentialsForBasicAuthorization(String login, String password) {
        final String loginAndPassword = login + ":" + password;
        return BASIC_PREFIX + Base64.encodeToString(loginAndPassword.getBytes(), Base64.NO_WRAP);
    }

    private final Context context;

    public ApiRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade requestFacade) {
        String login = SettingsUtils.getLogin(context);
        String password = SettingsUtils.getPassword(context);

        if (null != login && null != password) {
            final String authorizationValue = encodeCredentialsForBasicAuthorization(login, password);
            requestFacade.addHeader(AUTHORIZATION_HEADER, authorizationValue);
        }
    }
}
