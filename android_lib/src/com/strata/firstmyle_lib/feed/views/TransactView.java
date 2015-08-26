package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.ImageUrl;
import com.strata.firstmyle_lib.feed.model.Transact;
import com.strata.firstmyle_lib.utils.AppUtils;

/**
 * Created by hisham on 25/8/15.
 */
public class TransactView extends PostView {
    private TextView content;
    private ImageView ribbon,display_image;
    private ImageView feed_action_close;
    private View rowView;
    private Button feed_action_btn;
    private LinearLayout RL_rate_cont;
    private RelativeLayout RL_feed_action_cont;

    public TransactView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        Transact transact = sPost.getTransact();

        rowView = inflater.inflate(R.layout.listview_item_in0, null);
        content = (TextView) rowView.findViewById(R.id.text);
        rowView.findViewById(R.id.content_image).setVisibility(View.GONE);
        feed_action_close = (ImageView) rowView.findViewById(R.id.feed_action_close);
        feed_action_btn = (Button) rowView.findViewById(R.id.feed_action_btn);
        RL_feed_action_cont = (RelativeLayout) rowView.findViewById(R.id.RL_feed_action_cont);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        display_image = (ImageView) rowView.findViewById(R.id.content_image);

        RL_feed_action_cont.setVisibility(View.VISIBLE);
        feed_action_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RL_feed_action_cont.setVisibility(View.GONE);
            }
        });

        setContent(transact.getDetail());
        setRibbon(R.drawable.book_mark_pay);
        setDisplay_image(transact.getImage_urls());

        this.initCommomViews(rowView);
        this.addView(rowView);
    }

    public void setContent(String txt){
        AppUtils.setTextView(content, txt);
    }


    public void setDisplay_image(ImageUrl url) {
        AppUtils.picasoViewThumb(url, display_image, this.getContext());
    }

    public void setRibbon(int drawable) {
        ribbon.setImageResource(drawable);

    }

    public View getView(){
        return rowView;
    }
}
