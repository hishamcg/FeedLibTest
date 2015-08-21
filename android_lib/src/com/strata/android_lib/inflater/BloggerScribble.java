package com.strata.android_lib.inflater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.utils.AppUtils;

/**
 * Created by hisham on 12/8/15.
 */
public class BloggerScribble extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater,Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_rateus;
        View rowView = inflater.inflate(R.layout.listview_item_in0, null);
        TextView content = (TextView) rowView.findViewById(R.id.text);
        ImageView display_image = (ImageView) rowView.findViewById(R.id.content_image);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);

        content.setText(temp_feed.getPost_header().getDetail());
        AppUtils.picasoViewThumb(temp_feed.getImage_urls(), display_image, activity);
        return this.addWrapper(rowView, temp_feed);
    }
}
