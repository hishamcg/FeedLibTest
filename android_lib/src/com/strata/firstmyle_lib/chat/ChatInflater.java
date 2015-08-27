package com.strata.firstmyle_lib.chat;

import android.content.Context;
import android.view.View;

import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.chat.views.ChatImageView;
import com.strata.firstmyle_lib.chat.views.ChatView;
import com.strata.firstmyle_lib.chat.views.ContactView;
import com.strata.firstmyle_lib.chat.views.MessageView;
import com.strata.firstmyle_lib.chat.views.PlatformView;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by hisham on 27/8/15.
 */
public class ChatInflater {

    Context context;

    HashMap<String, Class<? extends ChatView>> hashMap;
    public ChatInflater(Context context){
        this.context = context;
        //this.adapter = adapter;
        //inflateView = new InflateView(inflater,context);

        hashMap = new HashMap<>();
        hashMap.put("MESSAGE",MessageView.class);
        hashMap.put("CHAT_IMAGE",ChatImageView.class);
        hashMap.put("PLATFORM",PlatformView.class);
        hashMap.put("CONTACT",ContactView.class);

    }

    public void AddHashMap(HashMap<String, Class<? extends ChatView>> hash_map){
        hashMap.putAll(hash_map);
    }

    public View FillView(final Reply reply,ChatView.ChatClickListener listener,String publisher_id) throws Exception {
        Class<? extends ChatView> cls = hashMap.get(reply.getType());
        if (cls != null) {
            Constructor<? extends ChatView> contruct = cls.getConstructor(Context.class,Reply.class,ChatView.ChatClickListener.class,String.class);
            return contruct.newInstance(context,reply, listener, publisher_id);
        }
        return new View(context);
    }
}
