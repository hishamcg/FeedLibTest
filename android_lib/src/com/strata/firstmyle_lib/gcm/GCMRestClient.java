package com.strata.firstmyle_lib.gcm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

public class GCMRestClient {
    private final GcmRegisterService gcmRegisterService;
    private static SessionRequestInterceptor sessionInterceptor;
    public GCMRestClient(String baseUrl, String token){
        Gson gson = new GsonBuilder().create();
        sessionInterceptor = new SessionRequestInterceptor();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(baseUrl)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(sessionInterceptor)
                .build();
        sessionInterceptor.setAuth_token(token);
        gcmRegisterService = restAdapter.create(GcmRegisterService.class);
    }

    public GcmRegisterService getGcmRegisterService(){
        return gcmRegisterService;
    }

    public interface GcmRegisterService {
        @GET("/consumers/gcm_register")
        Response gcmShare(@Query("gcm") String regId);
    }
}
