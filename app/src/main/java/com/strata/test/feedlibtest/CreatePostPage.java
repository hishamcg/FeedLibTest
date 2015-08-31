package com.strata.test.feedlibtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.strata.firstmyle_lib.create_post.CreatePostInitializer;

/**
 * Created by nagashree on 31/8/15.
 */
public class CreatePostPage extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        CreatePostInitializer createPostInitializer = new CreatePostInitializer(context);
        Fragment frag;

        try {
            frag = createPostInitializer.getCreatePostFragment();
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
