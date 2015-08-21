package com.strata.android_lib.inflater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.FeedLib;

/**
 * Created by hisham on 12/8/15.
 */
public class CivicProblem extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater,Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_biz;
        View rowView = inflater.inflate(R.layout.listview_item_civic_news, null);
        TextView text = (TextView) rowView.findViewById(R.id.text);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);
//                TextView verb = (TextView) rowView.findViewById(R.id.verb);
//                verb.setVisibility(View.VISIBLE);
        //verb.setText(temp_feed.);
        text.setText(temp_feed.getPost_header().getDetail());
        return this.addWrapper(rowView, temp_feed);
    }
}
