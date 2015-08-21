package com.strata.android_lib.inflater;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
public class Promotion extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater, final Activity activity, final FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_announcement;
        View rowView = inflater.inflate(R.layout.listview_item_promotion, null);
        ImageView image = (ImageView) rowView.findViewById(R.id.display_image);
        ImageView call_action = (ImageView) rowView.findViewById(R.id.call_action);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);
        call_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+919880932237"));
                activity.startActivity(intent);
            }
        });

        TextView content = (TextView) rowView.findViewById(R.id.text);
        AppUtils.picasoViewDisplay(temp_feed.getImage_urls(), image, activity);
        AppUtils.ValidateTextView(content, temp_feed.getPost_header().getDetail());

        return this.addWrapper(rowView, temp_feed);
    }
}
