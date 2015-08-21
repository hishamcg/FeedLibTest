package com.strata.android_lib.inflater;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.rest.LibRestClient;
import com.strata.android_lib.utils.LibShowToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hisham on 20/8/15.
 */
public class LibFeedFragment extends Fragment implements InflaterInitializer.InflaterCallback{
    private FeedAdapter1 adapter;
    private ArrayList<FeedLib> feedLib = new ArrayList<>();
    private ProgressDialog progress;
    private HashMap<String, Class<? extends FeedItemInflater>> hashMap;
    private OnFragmentInteractionListener mListener;

    public void AddHashMap(HashMap<String, Class<? extends FeedItemInflater>> hashMap){
        this.hashMap = hashMap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = (ListView) getView().findViewById(R.id.listview);
        //feed = Feed.listAll(Feed.class);
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

        //FloatingActionButton buttonFloat = (FloatingActionButton) getView().findViewById(R.id.create_dine_run);
        //buttonFloat.attachToListView(listView);
        Collections.reverse(feedLib);
        adapter = new FeedAdapter1(getActivity(), feedLib,this, hashMap);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent in = new Intent(getActivity(), FeedDetailActivity.class);
//                in.putExtra("feed", new Gson().toJson(feedLib.get(position)));
//                startActivity(in);
            }
        });

//        buttonFloat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //startActivity(new Intent(getActivity(), CreateFeed.class));
//            }
//        });

        LibRestClient.getRouteService().getFeeds(callback);

    }

    private Callback<ArrayList<FeedLib>> callback = new Callback<ArrayList<FeedLib>>() {
        @Override
        public void success(ArrayList<FeedLib> feeds, Response response) {
            progress.dismiss();
            feedLib.clear();
            feedLib.addAll(feeds);
            adapter.updateTempFeed(feeds);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
            progress.dismiss();
            //ShowToast.setText("failed");
        }
    };

    public void refresh() {
        progress.show();
        LibRestClient.getRouteService().getFeeds(callback);
    }

    public boolean removeFilter() {
        return adapter != null && adapter.removeFilter();
    }

    @Override
    public void onInitiatorClick(FeedLib feed) {
        try {
            mListener.onInitiatorClick(feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    @Override
    public void onCommentClick(FeedLib temp_feed) {
        try {
            mListener.onCommentClick(temp_feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    public interface OnFragmentInteractionListener {
        void onInitiatorClick(FeedLib feed);
        void onCommentClick(FeedLib temp_feed);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


}

