package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.ImageUrl;
import com.strata.firstmyle_lib.feed.model.Recommendation;
import com.strata.firstmyle_lib.utils.AppUtils;

/**
 * Created by hisham on 25/8/15.
 */
public class RecommendationView extends PostView {
    private TextView content;
    private ImageView ribbon,display_image;
    private View rowView;

    public RecommendationView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        Recommendation recommendation = sPost.getRecommendation();
        rowView = inflater.inflate(R.layout.listview_item_recommendation, null);
        content = (TextView) rowView.findViewById(R.id.text);
        display_image = (ImageView) rowView.findViewById(R.id.display_image);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        setContent(recommendation.getDetail());
        setRibbon(R.drawable.book_mark_rateus);
        setDisplay_image(recommendation.getImage_urls());

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
}
