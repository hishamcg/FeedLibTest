package com.strata.firstmyle_lib.create_post;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.create_post.fragment.CreatePostFragment;
import com.strata.firstmyle_lib.detail.fragment.EventFragment;
import com.strata.firstmyle_lib.detail.views.DetailView;
import com.strata.firstmyle_lib.feed.model.FeedPost;

import java.util.HashMap;

/**
 * Created by nagashree on 31/8/15.
 */
public class CreatePostInitializer {
    private Context context;
    private HashMap<String, Class<? extends Fragment>> hashMap = new HashMap<>();
    private DetailView.ActionClickListener listener;

    public CreatePostInitializer(Context context){
        this.context = context;
    }

    public Fragment getCreatePostFragment() throws Exception{
        CreatePostFragment frag = new CreatePostFragment();
//        frag.addListener(listener);
        Bundle bundle=new Bundle();
//        bundle.putString("feed", new Gson().toJson(sPost));
//        frag.setArguments(bundle);
        return frag;
    }
}
