package com.strata.android_lib.inflater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.model.FmProduct;
import com.strata.android_lib.utils.AppUtils;

import java.util.ArrayList;

/**
 * Created by hisham on 12/8/15.
 */
public class Bctc extends FeedItemInflater {
    @Override
    public View inflate(LayoutInflater inflater,Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        super.inflate(inflater,activity,temp_feed,initializer);
        int ribbon_image = R.drawable.book_mark_alert;
        View rowView = inflater.inflate(R.layout.listview_item_multi_product, null);
        TextView content = (TextView) rowView.findViewById(R.id.text);
        content.setText(temp_feed.getPost_header().getDetail());
        LinearLayout item_container = (LinearLayout) rowView.findViewById(R.id.image_list);
        ImageView ribbon = (ImageView) rowView.findViewById(R.id.ribbon_img);
        ribbon.setImageResource(ribbon_image);
        item_container.removeAllViews();
        ArrayList<FmProduct> dps = temp_feed.getProducts();
        if (dps.isEmpty()) {
            View tmp = inflater.inflate(R.layout.sub_list_item, null);
            ImageView book_image = (ImageView) tmp.findViewById(R.id.image);
            AppUtils.picasoViewDisplay(temp_feed.getImage_urls(), book_image, activity);
            item_container.addView(tmp);
        } else
            for (int i = 0; i < dps.size(); i++) {
                View tmp = inflater.inflate(R.layout.sub_list_item, null);
                ImageView book_image = (ImageView) tmp.findViewById(R.id.image);
                String url = dps.get(i).getImage();
                //if (url != null && !"null".equals(url))
                AppUtils.picasoView(url, book_image, activity);
                item_container.addView(tmp);
            }

        return this.addWrapper(rowView, temp_feed);
    }
}
