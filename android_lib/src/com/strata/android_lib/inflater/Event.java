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
public class Event extends FeedItemInflater{
    @Override
    public View inflate(LayoutInflater inflater,Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.ribbon;
        View rowView = inflater.inflate(R.layout.listview_item_ev0, null);
        TextView dateFrom = (TextView) rowView.findViewById(R.id.date_from);
        TextView monthFrom = (TextView) rowView.findViewById(R.id.month_from);
        //TextView content = (TextView) rowView.findViewById(R.id.content);
        TextView venue = (TextView) rowView.findViewById(R.id.venue);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        ImageView image = (ImageView) rowView.findViewById(R.id.display_image);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);
        rowView.findViewById(R.id.LL_ribbon_date).setVisibility(View.VISIBLE);
        AppUtils.picasoViewThumb(temp_feed.getImage_urls(), image, activity);
        venue.setText("Venue: "+(temp_feed.getVenue()!=null?temp_feed.getVenue():""));
        //content.setText(temp_feed.getPost_header().getDetail());
        date.setText("Date: " + temp_feed.getFrom_date() + " to " + temp_feed.getTo_date());
        try {
            String[] from = temp_feed.getFrom_date().split("-");
            monthFrom.setText(from[1]);
            dateFrom.setText(from[2]);
        }catch (Exception e){
            System.out.println("EVENT : failed to parse date.\n" + e.getMessage());
        }
        return this.addWrapper(rowView, temp_feed);
    }
}
