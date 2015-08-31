package com.strata.firstmyle_lib.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.strata.firstmyle_lib.chat.fragment.LibChatFragment;
import com.strata.firstmyle_lib.chat.views.ChatView;

import java.util.HashMap;

public class ChatInitializer {
    private ChatView.ChatClickListener listener;
    private HashMap<String, Class<? extends ChatView>> hashMap = new HashMap<>();

    public ChatInitializer(ChatView.ChatClickListener listener){
        this.listener = listener;
    }

    public ChatInitializer(ChatView.ChatClickListener listener, HashMap<String, Class<? extends ChatView>> hashMap){
        this.listener = listener;
        this.hashMap = hashMap;
    }

    public Fragment getChat(){
        Bundle bund = new Bundle();
        LibChatFragment fragment = new LibChatFragment();
        fragment.AddHashMap(hashMap);
        fragment.AddListener(listener);
        fragment.setArguments(bund);

        return fragment;
    }
}
