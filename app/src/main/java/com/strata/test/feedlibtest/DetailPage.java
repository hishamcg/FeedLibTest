package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.detail.PostInitializer;
import com.strata.firstmyle_lib.detail.fragment.PostFragment;
import com.strata.firstmyle_lib.detail.views.DetailView;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;

/**
 * Created by hisham on 26/8/15.
 */
public class DetailPage extends AppCompatActivity  implements PostFragment.OnFragmentInteractionListener{

    private static Context context;


    private static final DetailView.ActionClickListener listener = new DetailView.ActionClickListener() {
        @Override
        public void onClick(ActionEnums action, FeedPost sPost) {

            switch (action) {
                default:
                    LibShowToast.setText("Clicked => " + action);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        FeedPost sPost = new Gson().fromJson(getIntent().getStringExtra("sPost"),FeedPost.class);

        HashMap<String, Class<? extends Fragment>> hashMap = new HashMap<>();
        PostInitializer postInitializer = new PostInitializer(this,hashMap,listener);
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

    @Override
    public void onInitiatorClick(FeedPost feed) {

    }

    @Override
    public void onCommentClick(FeedPost feed) {

    }

    @Override
    public void onDetailClick(FeedPost feed) {
        LibShowToast.setText("im here");
    }
}