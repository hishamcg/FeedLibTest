package com.strata.android_lib.gcm;

import retrofit.RequestInterceptor;

/**
 * Created by hisham on 2/3/15.
 */
public class SessionRequestInterceptor implements RequestInterceptor {
    private String auth_token = "";

    public void setAuth_token(String auth) {
        this.auth_token = auth;
    }
    @Override
    public void intercept(RequestInterceptor.RequestFacade request) {
        if (!this.auth_token.isEmpty()) {
            request.addHeader("auth-token", this.auth_token);
        }

    }
}