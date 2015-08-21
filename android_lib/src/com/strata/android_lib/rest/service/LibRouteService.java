package com.strata.android_lib.rest.service;

import com.strata.android_lib.model.AuthToken;
import com.strata.android_lib.model.FeedLib;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface LibRouteService {
    @GET("/login_create")
    void userAuthenticate(@Query("phone_no") String phone_no, Callback<AuthToken> callback);

    @GET("/login")
    void userLogin(@Query("email") String email, @Query("password") String password, @Query("provider") String provider, Callback<AuthToken> callback);

    @GET("/validate_otp")
    void validateOTP(@Query("otp") String otp, @Query("phone_no") String phone, Callback<AuthToken> success);

    @GET("/getdummy_data")
    public void getFeeds( Callback<ArrayList<FeedLib>> callback);

    @GET("/feeds/create_action.json")
    public void FeedLike(@Query("feed_id") String feed_id, @Query("action_type") String action, Callback<AuthToken> callback);
}
