package com.strata.firstmyle_lib.chat.views;

import android.content.Context;
import android.widget.FrameLayout;

import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.utils.ActionEnums;

/**
 * Created by hisham on 27/8/15.
 */
public class ChatView extends FrameLayout {
    public ChatView(Context context) {
        super(context);
    }

    public ChatView(Context context, Reply reply, ChatClickListener l, String publisher_id) {
        super(context);
    }

    public interface ChatClickListener {
        void onClick(ActionEnums action, Reply reply);
    }
}
