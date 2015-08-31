package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.Bctc;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.model.FmProduct;
import com.strata.firstmyle_lib.utils.AppUtils;

import java.util.ArrayList;

/**
 * Created by hisham on 25/8/15.
 */
public class BctcView extends PostView{
    private TextView content;
    private TextView subject;
    private LinearLayout item_container;
    private ImageView ribbon;
    private LayoutInflater inflater;
    private Bctc bctc;
    private View rowView;


    public BctcView (Context context,FeedPost sPost, ActionClickListener listener){
        super(context,sPost,listener);
        initialize();
    }

    private void initialize(){
        this.inflater = LayoutInflater.from(this.getContext());
        this.bctc = this.sPost.getBctc();
        rowView = inflater.inflate(R.layout.listview_item_multi_product, null);

        content = (TextView) rowView.findViewById(R.id.text);
        subject = (TextView) rowView.findViewById(R.id.subject);
        item_container = (LinearLayout) rowView.findViewById(R.id.image_list);
        ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);

        setContent(bctc.getDetail());
        setSubject(bctc.getTitle());
        ribbon.setImageResource(R.drawable.book_mark_alert);
        setItem_container(bctc.getProducts());

        this.initCommomViews(rowView);
        this.addView(rowView);
    }

    public void setContent(String txt){
        AppUtils.setTextView(content, txt);
    }

    public void setSubject(String txt) {
        AppUtils.setTextView(subject,txt);
    }

    public void setRibbon(int drawable) {
        ribbon.setImageResource(drawable);
    }

    public void setItem_container(ArrayList<FmProduct> dps) {
        if (dps.isEmpty()) {
            View tmp = inflater.inflate(R.layout.sub_list_item, null);
            ImageView book_image = (ImageView) tmp.findViewById(R.id.image);
            AppUtils.picasoViewDisplay(bctc.getImage_urls(), book_image, this.getContext());
            item_container.addView(tmp);
        } else
            for (int i = 0; i < dps.size(); i++) {
                View tmp = inflater.inflate(R.layout.sub_list_item, null);
                ImageView book_image = (ImageView) tmp.findViewById(R.id.image);
                String url = dps.get(i).getImage();
                //if (url != null && !"null".equals(url))
                AppUtils.picasoView(url, book_image, this.getContext());
                item_container.addView(tmp);
            }
    }
}
