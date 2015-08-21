package com.strata.android_lib.rest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strata.android_lib.rest.service.LibRouteService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class LibRestClient {
    private static LibRouteService routeService;
    private static LibSessionRequestInterceptor sessionInterceptor;




    private static String  lat = null;
    private static String  lon = null;

    public static void init(String token,String BASE_URL) {
        Gson gson = new GsonBuilder().create();
        sessionInterceptor = new LibSessionRequestInterceptor();


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(sessionInterceptor)
                .build();
        sessionInterceptor.setAuthToken(token);
        routeService = restAdapter.create(LibRouteService.class);
    }

    public static void setAuth_token(String token){
        sessionInterceptor.setAuthToken(token);
    }
    public static void setLocation(String lat,String lon){
        LibRestClient.lat = lat;
        LibRestClient.lon = lon;
        sessionInterceptor.setLatLon(lat, lon);
    }

    public static String getLat() {
        return lat;
    }
    public static String getLon() {
        return lon;
    }

    public static LibRouteService getRouteService() {

        return routeService;
    }

}
