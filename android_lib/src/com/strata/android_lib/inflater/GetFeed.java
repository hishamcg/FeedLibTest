package com.strata.android_lib.inflater;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;

public class GetFeed {
    private String baseUrl;
    private HashMap<String, Class<? extends FeedItemInflater>> hashMap = new HashMap<>();

    public GetFeed(String baseUrl){
        this.baseUrl = baseUrl;
    }

    public GetFeed(String baseUrl,HashMap<String, Class<? extends FeedItemInflater>> hashMap){
        this.baseUrl = baseUrl;
        this.hashMap = hashMap;
    }

    public Fragment inflate(){
        Bundle bund = new Bundle();
        bund.putString("baseUrl", baseUrl);
        LibFeedFragment fragment = new LibFeedFragment();
        fragment.AddHashMap(hashMap);
        fragment.setArguments(bund);

        return fragment;
    }
}
