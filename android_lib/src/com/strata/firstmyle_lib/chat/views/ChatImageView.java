package com.strata.firstmyle_lib.chat.views;

import android.content.Context;
import android.media.Image;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.AppUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by hisham on 27/8/15.
 */
public class ChatImageView extends ChatView {
    private LayoutInflater inflater;
    private ChatView.ChatClickListener listener;
    private Reply reply;
    private String publisher_id;
    private int padding35;
    private int padding5;
    private ForegroundColorSpan fcs;

    static class ViewHolder {
        public TextView Lname;
        public TextView Lmessage;
        public TextView Ltime;
        public ImageView Limage;
        public ImageView user_icon;
        public RelativeLayout singleMessageContainer;
        public LinearLayout singleMessagealign;
    }

    public ChatImageView(Context context, Reply reply, ChatClickListener l, String publisher_id) {
        super(context, reply, l, publisher_id);
        this.reply = reply;
        this.listener = l;
        this.publisher_id = publisher_id;

        fcs = new ForegroundColorSpan(0x00000000);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        padding35 = (int) (35*metrics.density);
        padding5 = (int) (5*metrics.density);

        initialize();
    }

    public void initialize() {
        this.inflater = LayoutInflater.from(this.getContext());

        ViewHolder holder = new ViewHolder();
        View rowView = inflater.inflate(R.layout.chat_list_item_image, null);
        holder.Lname = (TextView) rowView.findViewById(R.id.name);
        holder.Lmessage = (TextView) rowView.findViewById(R.id.message);
        holder.Ltime = (TextView) rowView.findViewById(R.id.time);
        holder.Limage = (ImageView) rowView.findViewById(R.id.image);
        holder.user_icon = (ImageView) rowView.findViewById(R.id.user_icon);
        holder.singleMessageContainer = (RelativeLayout) rowView.findViewById(R.id.LL_main);
        holder.singleMessagealign = (LinearLayout) rowView.findViewById(R.id.RL_top);
        if (reply.getPublisher().getPublisher_id().equals(publisher_id)) {
            holder.user_icon.setVisibility(View.GONE);
            holder.singleMessageContainer.setBackgroundResource(R.drawable.bubble_me);
            //holder.singleMessageContainer.setPadding(padding15, padding5, padding30, padding5);
            holder.singleMessagealign.setGravity(Gravity.RIGHT);
            holder.singleMessagealign.setPadding(padding35, padding5, padding5, padding5);
            holder.Lname.setText(reply.getPublisher().getName());
        } else {
            holder.user_icon.setVisibility(View.VISIBLE);
            AppUtils.picasoView(reply.getPublisher().getImage(), holder.user_icon, this.getContext(), 250);
            holder.Lname.setText(reply.getPublisher().getName());
            holder.singleMessagealign.setGravity(Gravity.LEFT);
            holder.singleMessagealign.setPadding(padding5, padding5, padding35, padding5);
            holder.singleMessageContainer.setBackgroundResource(R.drawable.bubble_you);
            //holder.singleMessageContainer.setPadding(padding30, padding5, padding15, padding5);
        }
        String msg;
        try {
            msg = URLDecoder.decode(reply.getContent(), "UTF-8");
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            msg = reply.getContent();
        }
        String t = reply.getTime();
        holder.Lmessage.setText(msg);
        holder.Ltime.setText(t);
        AppUtils.picasoView(reply.getImage_url(),holder.Limage,getContext());
        this.addView(rowView);
    }

    private void setActionOnClick(ActionEnums actionEnums){
        if (listener != null) {
            listener.onClick(actionEnums, reply);
        }
    }
}
