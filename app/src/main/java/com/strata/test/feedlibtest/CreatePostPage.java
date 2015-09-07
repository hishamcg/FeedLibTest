package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.strata.firstmyle_lib.create_post.CreatePostInitializer;
import com.strata.firstmyle_lib.create_post.fragment.AskFragment;
import com.strata.firstmyle_lib.create_post.fragment.BackHandledFragment;
import com.strata.firstmyle_lib.create_post.fragment.BizSelectFragment;
import com.strata.firstmyle_lib.create_post.fragment.CreatePostFragment;
import com.strata.firstmyle_lib.create_post.fragment.EventFragment;
import com.strata.firstmyle_lib.create_post.fragment.PostTypeFragment;
import com.strata.firstmyle_lib.create_post.fragment.SayAboutFragment;
import com.strata.firstmyle_lib.create_post.fragment.SayFragment;
import com.strata.firstmyle_lib.create_post.views.AskView;
import com.strata.firstmyle_lib.create_post.views.CreatePostView;
import com.strata.firstmyle_lib.create_post.views.EventView;
import com.strata.firstmyle_lib.create_post.views.SayAboutView;
import com.strata.firstmyle_lib.create_post.views.SayView;
import com.strata.firstmyle_lib.detail.detail_views.EventDetailView;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;

/**
 * Created by nagashree on 31/8/15.
 */
public class CreatePostPage extends AppCompatActivity  implements CreatePostFragment.OnCreatePostFragmentListener,BackHandledFragment.BackHandlerInterface,PostTypeFragment.OnPostTypeListener {

    private static Context context;
    private BackHandledFragment selectedFragment;
    Fragment frag;
    FragmentManager fragmentManager;
    CreatePostInitializer createPostInitializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        createPostInitializer = new CreatePostInitializer(context);
        HashMap<String,Integer> hashMap = new HashMap<>();
        hashMap.put("Say Something",com.strata.firstmyle_lib.R.drawable.icon_say);
        hashMap.put("Say Something About",com.strata.firstmyle_lib.R.drawable.icon_say_about);
        hashMap.put("Ask",com.strata.firstmyle_lib.R.drawable.icon_ask);
        hashMap.put("Event",com.strata.firstmyle_lib.R.drawable.icon_event);

        try {
            frag = createPostInitializer.getPostTypeFragment(hashMap);
        } catch (Exception e) {
            frag = new Fragment();
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment,frag )
                .commit();

    }

    @Override
    public void onBackPressed() {
        if(selectedFragment == null || !selectedFragment.onBackPressed()) {
            // Selected fragment did not consume the back press event.
            super.onBackPressed();
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.selectedFragment = selectedFragment;
    }

    @Override
    public void onCreateFeed() {
        LibShowToast.setText("Feed Created");
        finish();
    }

    @Override
    public void onClose() {
        finish();
    }

    @Override
    public void onPostType(String post_type) {
        fragmentManager.beginTransaction().remove(frag).commit();
        try {
            HashMap<String,Class<? extends CreatePostFragment>> hashMap = new HashMap<>();
            hashMap.put("Say Something", SayFragment.class);
            hashMap.put("Say Something About", SayAboutFragment.class);
            hashMap.put("Ask", AskFragment.class);
            hashMap.put("Event", EventFragment.class);
            frag = createPostInitializer.getCreatePostFragment(post_type,hashMap);
            fragmentManager.beginTransaction().replace(R.id.fragment,frag ).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LibShowToast.setText(post_type);
    }
}
