package com.strata.test.feedlibtest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.strata.android_lib.inflater.FeedItemInflater;
import com.strata.android_lib.inflater.GetFeed;
import com.strata.android_lib.inflater.LibFeedFragment;
import com.strata.android_lib.model.FeedLib;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements LibFeedFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, Class<? extends FeedItemInflater>> hashMap = new HashMap<>();
        GetFeed fed = new GetFeed("",hashMap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fed.inflate())
                .commit();

    }


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
    public void onInitiatorClick(FeedLib feed) {
        Toast.makeText(this,"initiator clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentClick(FeedLib temp_feed) {
        Toast.makeText(this,"comment clicked",Toast.LENGTH_SHORT).show();
    }
}
