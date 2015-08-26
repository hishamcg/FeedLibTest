package com.strata.firstmyle_lib.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.strata.firstmyle_lib.detail.fragment.EventFragment;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;

import java.util.HashMap;

/**
 * Created by hisham on 26/8/15.
 */
public class PostInitializer {
    private Context context;
    private HashMap<String, Class<? extends Fragment>> hashMap = new HashMap<>();

    public PostInitializer(Context context){
        this.context = context;
    }

    public PostInitializer(Context context, HashMap<String, Class<? extends Fragment>> hashMap){
        this.context = context;
        this.hashMap = hashMap;
    }

    public Fragment getPostFragment(FeedPost sPost) throws Exception{
        hashMap.put("Event", EventFragment.class);

        Class<? extends Fragment> cls = hashMap.get(sPost.getType());
        if(cls!=null){
            Fragment frag = cls.newInstance();
            return frag;
        }

        return null;
    }
}
