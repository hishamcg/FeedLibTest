package com.strata.android_lib.inflater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.utils.AppUtils;

/**
 * Created by hisham on 12/8/15.
 */
public class Transact extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater, final Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_pay;
        View rowView = inflater.inflate(R.layout.listview_item_in0, null);
        TextView content = (TextView) rowView.findViewById(R.id.text);
        rowView.findViewById(R.id.content_image).setVisibility(View.GONE);
        ImageView close = (ImageView) rowView.findViewById(R.id.feed_action_close);
        Button b = (Button) rowView.findViewById(R.id.feed_action_btn);
        final RelativeLayout RL_feed_action_cont = (RelativeLayout) rowView.findViewById(R.id.RL_feed_action_cont);
        final LinearLayout RL_rate_cont = (LinearLayout) rowView.findViewById(R.id.RL_rate_cont);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);

//        if (temp_feed.getAction() != null && temp_feed.getAction().getLabel() != null) {
//            b.setText(temp_feed.getAction().getLabel());
//        }
        RL_feed_action_cont.setVisibility(View.VISIBLE);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make a server call to take action
                RL_feed_action_cont.setVisibility(View.GONE);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL_feed_action_cont.setVisibility(View.GONE);
            }
        });
        AppUtils.ValidateTextView(content, temp_feed.getPost_header().getDetail());
        RL_rate_cont.setVisibility(View.VISIBLE);
        return this.addWrapper(rowView, temp_feed);
    }
}
