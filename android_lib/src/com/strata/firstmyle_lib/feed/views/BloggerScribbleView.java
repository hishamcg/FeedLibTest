package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.strata.firstmyle_lib.feed.model.BloggerScribble;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.ImageUrl;
import com.strata.firstmyle_lib.utils.AppUtils;
import com.strata.firstmyle_lib.R;

/**
 * Created by hisham on 25/8/15.
 */
public class BloggerScribbleView extends PostView {
    private TextView content;
    private ImageView ribbon,display_image;
    private View rowView;

    public BloggerScribbleView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context, sPost, listener);
        initialize();
    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        BloggerScribble bloggerScribble = sPost.getBloggerScribble();
        rowView = inflater.inflate(R.layout.listview_item_in0, null);
        content = (TextView) rowView.findViewById(R.id.text);
        display_image = (ImageView) rowView.findViewById(R.id.content_image);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        setContent(bloggerScribble.getDetail());
        setRibbon(R.drawable.book_mark_rateus);
        setDisplay_image(bloggerScribble.getImage_urls());

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
