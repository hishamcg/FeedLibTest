package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.chat.ChatInitializer;
import com.strata.firstmyle_lib.chat.fragment.LibChatFragment;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.chat.views.ChatView;
import com.strata.firstmyle_lib.feed.FeedInitializer;
import com.strata.firstmyle_lib.feed.fragment.LibFeedFragment;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.EventSummary;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibSharedPref;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements LibFeedFragment.OnFragmentInteractionListener{

    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LibRestClient.setAuth_token("eu3gvPA3a55-XyLs8Szx");
        LibSharedPref.setStringValue("NAME", "NAGASHREE");
        LibSharedPref.setStringValue("EMAIL","nagashree.ashwath@strata.co.in");
        LibSharedPref.setStringValue("PHONE_NO", "9964408383");
        LibSharedPref.setStringValue("USER_IMAGE", "https://s3.amazonaws.com/prod.firstmiler.com/images/shoba-acharya.jpg");
        setContentView(R.layout.activity_main);
        context = this;
        HashMap<String, Class<? extends PostSummary>> hashMap = new HashMap<>();
        FeedInitializer fed = new FeedInitializer(listener,hashMap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fed.getFeed())
                .commit();
    }

        private static final PostView.ActionClickListener listener = new PostView.ActionClickListener() {
            @Override
            public void onClick(ActionEnums action, FeedPost sPost) {

                Intent in;
                switch (action) {
                    case DETAIL:
                        in = new Intent(context,DetailPage.class);
                        in.putExtra("sPost", new Gson().toJson(sPost));
                        context.startActivity(in);
                        break;
                    case CHAT:
                        in = new Intent(context,ChatPage.class);
                        in.putExtra("sPost", new Gson().toJson(sPost));
                        context.startActivity(in);
                    case CREATE:
                        in = new Intent(context,CreatePostPage.class);
                        in.putExtra("sPost", new Gson().toJson(sPost));
                        context.startActivity(in);
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
        if (id == R.id.action_settings){
            return true;
        }else if(id == R.id.action_chat){
            Intent in = new Intent(context,ChatPage.class);
            //in.putExtra("sPost", new Gson().toJson(sPost));
            context.startActivity(in);
            return true;
        }else if(id == R.id.action_mentions){
            Intent in = new Intent(context,SimpleMentions.class);
            context.startActivity(in);
            return true;
        }else if(id == R.id.action_payment){
            Intent in = new Intent(context,PaymentActivity.class);
            context.startActivity(in);
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

    @Override
    public void onDetailClick(FeedPost feed) {
        Intent in = new Intent(context,DetailPage.class);
        in.putExtra("sPost", new Gson().toJson(feed));
        context.startActivity(in);
    }
}
