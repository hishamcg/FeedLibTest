package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.feed.FeedInitializer;
import com.strata.firstmyle_lib.feed.fragment.LibFeedFragment;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.EventSummary;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements LibFeedFragment.OnFragmentInteractionListener{

    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        HashMap<String, Class<? extends PostSummary>> hashMap = new HashMap<>();
        hashMap.put("Event", EventSummary.class);
        FeedInitializer fed = new FeedInitializer(listener,hashMap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fed.getFeed())
                .commit();

    }

    private static final PostView.ActionClickListener listener = new PostView.ActionClickListener() {
        @Override
        public void onClick(ActionEnums action, FeedPost sPost) {

            switch (action) {
                case DETAIL:
                    Intent in = new Intent(context,DetailPage.class);
                    in.putExtra("sPost", new Gson().toJson(sPost));
                    context.startActivity(in);
                    break;
                default:
                    LibShowToast.setText("Clicked => " + action);
                    break;

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitiatorClick(FeedPost feed) {

    }

    @Override
    public void onCommentClick(FeedPost temp_feed) {

    }
}
