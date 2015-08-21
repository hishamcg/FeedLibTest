package com.strata.android_lib.inflater;

import android.app.Activity;
import android.util.Log;
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
public class Recommendation extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater, final Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_rateus;
        try {
            View rowView = inflater.inflate(R.layout.listview_item_promotion, null);
            ImageView image = (ImageView) rowView.findViewById(R.id.display_image);
            TextView content = (TextView) rowView.findViewById(R.id.text);
            ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
            ribbon.setImageResource(ribbon_image);
            AppUtils.picasoViewThumb(temp_feed.getImage_urls(), image, activity);
            AppUtils.ValidateTextView(content, temp_feed.getPost_header().getDetail());
            return this.addWrapper(rowView, temp_feed);
        }catch (Exception e){
            Log.e("test","failed");
        }
        return null;
    }
}
