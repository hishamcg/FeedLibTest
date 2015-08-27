package com.strata.firstmyle_lib.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.detail.fragment.EventFragment;
import com.strata.firstmyle_lib.detail.views.DetailView;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;

import java.util.HashMap;

/**
 * Created by hisham on 26/8/15.
 */
public class PostInitializer {
    private Context context;
    private HashMap<String, Class<? extends Fragment>> hashMap = new HashMap<>();
    private DetailView.ActionClickListener listener;

    public PostInitializer(Context context){
        this.context = context;
    }

    public PostInitializer(Context context, HashMap<String, Class<? extends Fragment>> hashMap,DetailView.ActionClickListener listener){
        this.context = context;
        this.hashMap = hashMap;
        this.listener = listener;
    }

    public Fragment getPostFragment(FeedPost sPost) throws Exception{
        hashMap.put("Event", EventFragment.class);
        hashMap.put("Bctc", EventFragment.class);
        hashMap.put("BlogFeed", EventFragment.class);

        Class<? extends Fragment> cls = hashMap.get(sPost.getType());
        if(cls!=null){
            Fragment frag = cls.newInstance();
            Bundle bundle=new Bundle();
            bundle.putString("feed",  new Gson().toJson(sPost));
            frag.setArguments(bundle);
            return frag;
        }

        return null;
    }
}
