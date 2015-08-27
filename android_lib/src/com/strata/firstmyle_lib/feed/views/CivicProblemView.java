package com.strata.firstmyle_lib.feed.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.CivicProblem;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.utils.AppUtils;

/**
 * Created by hisham on 25/8/15.
 */
public class CivicProblemView extends PostView {
    private TextView content;
    private ImageView ribbon;
    private View rowView;

    public CivicProblemView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        CivicProblem civicProblem = sPost.getCivicProblem();
        rowView = inflater.inflate(R.layout.listview_item_civic_news, null);
        content = (TextView) rowView.findViewById(R.id.text);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        setContent(civicProblem.getDetail());
        setRibbon(R.drawable.book_mark_rateus);

        this.initCommomViews(rowView);
        this.addView(rowView);
    }

    public void setContent(String txt){
        AppUtils.setTextView(content, txt);
    }

    public void setRibbon(int drawable) {
        ribbon.setImageResource(drawable);

    }
}
