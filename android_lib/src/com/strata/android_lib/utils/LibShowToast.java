package com.strata.android_lib.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by hisham on 6/6/15.
 */
public class LibShowToast {
    private static Context context;
    public static void initialize(Context mContext){
        context = mContext;
    }

    public static void setText(String txt){
        Toast toast = Toast.makeText(context,
                txt,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 170);
        toast.show();
    }

    public static void setTextShort(String txt){
        Toast toast = Toast.makeText(context,
                txt,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 170);
        toast.show();
    }

    public static void setTextThread(String message){
        Handler handler = new Handler();
        handler.post(new DisplayToast(message));
    }

    private static class DisplayToast implements Runnable {
        String mText;

        public DisplayToast(String text) {
            mText = text;
        }

        public void run() {
            Toast toast = Toast.makeText(context, mText, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 170);
                toast.show();
        }
    }

}
