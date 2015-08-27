package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.firstmyle_lib.feed.model.CivicProblem;
import com.strata.firstmyle_lib.feed.model.Event;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.ImageUrl;
import com.strata.firstmyle_lib.utils.AppUtils;
import com.strata.firstmyle_lib.R;

/**
 * Created by hisham on 25/8/15.
 */
public class EventView extends PostView {
    private ImageView display_image;
    private ImageView ribbon;
    private View rowView;
    private TextView dateFrom;
    private TextView monthFrom;
    private TextView venue;
    private TextView date;


    public EventView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        Event event = sPost.getEvent();
        rowView = inflater.inflate(R.layout.listview_item_ev0, null);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        dateFrom = (TextView) rowView.findViewById(R.id.date_from);
        monthFrom = (TextView) rowView.findViewById(R.id.month_from);
        venue = (TextView) rowView.findViewById(R.id.venue);
        date = (TextView) rowView.findViewById(R.id.date);
        display_image = (ImageView) rowView.findViewById(R.id.display_image);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        rowView.findViewById(R.id.LL_ribbon_date).setVisibility(View.VISIBLE);

        try {
            String[] from = event.getFrom_date().split("-");
            setMonthFrom(from[1]);
            setDateFrom(from[2]);
        }catch (Exception e){
            System.out.println("EVENT : failed to parse date.\n" + e.getMessage());
        }

        setVenue(event.getVenue());
        setDate(event.getFrom_date(), event.getTo_date());
        setDisplay_image(event.getImage_urls());
        setRibbon(R.drawable.ribbon);

        this.initCommomViews(rowView);
        this.addView(rowView);
    }

    public void setDisplay_image(ImageUrl urls) {
        AppUtils.picasoViewThumb(urls, display_image, this.getContext());
    }

    public void setDateFrom(String txt){
        AppUtils.setTextView(dateFrom, txt);
    }

    public void setMonthFrom(String txt){
        AppUtils.setTextView(monthFrom, txt);
    }

    public void setVenue(String txt){
        venue.setText("Venue: " + (txt != null ? txt : "-"));
    }

    public void setDate(String from,String to){
        date.setText("Date: " + from + " to " + to);
    }

    public void setRibbon(int drawable) {
        ribbon.setImageResource(drawable);

    }
}
