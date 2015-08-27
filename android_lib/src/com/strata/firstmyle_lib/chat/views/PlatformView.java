package com.strata.firstmyle_lib.chat.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.utils.ActionEnums;

/**
 * Created by hisham on 27/8/15.
 */
public class PlatformView extends ChatView {
    private LayoutInflater inflater;
    private ChatView.ChatClickListener listener;
    private Reply reply;
    private String publisher_id;


    public PlatformView(Context context, Reply reply, ChatClickListener l, String publisher_id) {
        super(context, reply, l, publisher_id);
        this.reply = reply;
        this.listener = l;
        this.publisher_id = publisher_id;

        initialize();
    }

    public void initialize() {
        this.inflater = LayoutInflater.from(this.getContext());

        View rowView = inflater.inflate(R.layout.chat_list_item_platform, null);
        TextView Lmessage = (TextView) rowView.findViewById(R.id.message);

        String m = reply.getContent();
        Lmessage.setText(m);
        this.addView(rowView);
    }

    private void setActionOnClick(ActionEnums actionEnums){
        if (listener != null) {
            listener.onClick(actionEnums, reply);
        }
    }
}
