package com.strata.firstmyle_lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.ImageUrl;

public class AppUtils {
    public static int getVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void picasoViewThumb(ImageUrl url, ImageView image, Context context) {
        if(image!=null)
            try {
                if (url != null && url.getThumb_url()!=null && !url.getThumb_url().isEmpty()) {
                    Picasso.with(context)
                            .load(url.getThumb_url())
                            .noFade()
                            .placeholder(R.drawable.image_placeholder)
                            .into(image);
                } else {
                    image.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d("image", "error");
            }
    }

    public static void picasoViewDisplay(ImageUrl url, ImageView image, Context context) {
        if(image!=null)
            try {
                if (url != null && url.getDisplay_url()!=null && !url.getDisplay_url().isEmpty()) {
                    Picasso.with(context)
                            .load(url.getDisplay_url())
                            .noFade()
                            .placeholder(R.drawable.image_placeholder)
                            .into(image);
                } else {
                    image.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d("image", "error");
            }
    }

    public static void picasoView(String url, ImageView image, Context context) {
        if(image!=null)
            try {
                if (url != null && !url.isEmpty()) {
                    Picasso.with(context)
                            .load(url)
                            .noFade()
                            .placeholder(R.drawable.image_placeholder)
                            .into(image);
                } else {
                    image.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d("image", "error");
            }
    }

    public static void setTextView(TextView view, String txt){
        if(view!=null){
            view.setText(txt!=null && !txt.isEmpty()?txt:"");
        }
    }
}
