package com.strata.firstmyle_lib.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.strata.firstmyle_lib.chat.fragment.LibChatFragment;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;

import java.util.HashMap;

public class ChatInitializer {
    private PostView.ActionClickListener listener;
    private HashMap<String, Class<? extends PostSummary>> hashMap = new HashMap<>();

    public ChatInitializer(PostView.ActionClickListener listener){
        this.listener = listener;
    }

    public ChatInitializer(PostView.ActionClickListener listener, HashMap<String, Class<? extends PostSummary>> hashMap){
        this.listener = listener;
        this.hashMap = hashMap;
    }

    public Fragment getFeed(){
        Bundle bund = new Bundle();
        LibChatFragment fragment = new LibChatFragment();
        fragment.AddHashMap(hashMap);
        fragment.AddListener(listener);
        fragment.setArguments(bund);

        return fragment;
    }
}
