package com.strata.firstmyle_lib.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.strata.firstmyle_lib.feed.fragment.LibFeedFragment;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;

import java.util.HashMap;

public class FeedInitializer {
    private PostView.ActionClickListener listener;
    private HashMap<String, Class<? extends PostSummary>> hashMap = new HashMap<>();

    public FeedInitializer(PostView.ActionClickListener listener){
        this.listener = listener;
    }

    public FeedInitializer(PostView.ActionClickListener listener, HashMap<String, Class<? extends PostSummary>> hashMap){
        this.listener = listener;
        this.hashMap = hashMap;
    }

    public Fragment getFeed(){
        Bundle bund = new Bundle();
        LibFeedFragment fragment = new LibFeedFragment();
        fragment.AddHashMap(hashMap);
        fragment.AddListener(listener);
        fragment.setArguments(bund);

        return fragment;
    }
}
