package com.strata.test.feedlibtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.detail.PostInitializer;
import com.strata.firstmyle_lib.detail.fragment.EventFragment;
import com.strata.firstmyle_lib.feed.FeedInitializer;
import com.strata.firstmyle_lib.feed.fragment.LibFeedFragment;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.EventSummary;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;

import java.util.HashMap;

/**
 * Created by hisham on 26/8/15.
 */
public class DetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FeedPost sPost = new Gson().fromJson(getIntent().getStringExtra("sPost"),FeedPost.class);

        HashMap<String, Class<? extends Fragment>> hashMap = new HashMap<>();
        PostInitializer postInitializer = new PostInitializer(this,hashMap);
        Fragment frag;

        try {
            frag = postInitializer.getPostFragment(sPost);
        } catch (Exception e) {
            frag = new Fragment();
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment,frag )
                .commit();

    }
}