package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.Event;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.ImageUrl;
import com.strata.firstmyle_lib.utils.AppUtils;

/**
 * Created by hisham on 25/8/15.
 */
public class PromotionView extends PostView {
    private TextView content;
    private ImageView call_action;
    private ImageView display_image;
    private ImageView ribbon;
    private View rowView;


    public PromotionView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        Event event = sPost.getEvent();
        rowView = inflater.inflate(R.layout.listview_item_promotion, null);
        content = (TextView) rowView.findViewById(R.id.text);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        call_action = (ImageView) rowView.findViewById(R.id.call_action);
        display_image = (ImageView) rowView.findViewById(R.id.display_image);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        call_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+919880932237"));
                //activity.startActivity(intent);
            }
        });

        setDisplay_image(event.getImage_urls());
        setContent(event.getDetail());
        setRibbon(R.drawable.book_mark_rateus);

        this.initCommomViews(rowView);
        this.addView(rowView);
    }

    public void setDisplay_image(ImageUrl urls) {
        AppUtils.picasoViewDisplay(urls, display_image, this.getContext());
    }


    public void setContent(String txt){
        AppUtils.setTextView(content, txt);
    }

    public void setRibbon(int drawable) {
        ribbon.setImageResource(drawable);

    }

}
