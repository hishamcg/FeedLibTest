package com.strata.firstmyle_lib.feed.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.PostSummaryInflater;
import com.strata.firstmyle_lib.feed.adapter.FeedAdapter1;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hisham on 20/8/15.
 */
public class LibFeedFragment extends Fragment implements PostSummaryInflater.InflaterCallback {
    private FeedAdapter1 adapter;
    private ArrayList<FeedPost> postSummaries = new ArrayList<>();
    private ProgressDialog progress;
    private HashMap<String, Class<? extends PostSummary>> hashMap;
    private OnFragmentInteractionListener mListener;
    private PostView.ActionClickListener listener;

    public void AddHashMap(HashMap<String, Class<? extends PostSummary>> hashMap){
        this.hashMap = hashMap;
    }

    public void AddListener(PostView.ActionClickListener listener){
        this.listener = listener;
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

        FloatingActionButton buttonFloat = (FloatingActionButton) getView().findViewById(R.id.create_dine_run);
        buttonFloat.attachToListView(listView);
        Collections.reverse(postSummaries);
        adapter = new FeedAdapter1(getActivity().getApplicationContext(), postSummaries,listener, hashMap);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent in = new Intent(getActivity(), FeedDetailActivity.class);
//                in.putExtra("feed", new Gson().toJson(feedLib.get(position)));
//                startActivity(in);
                if (listener != null) {
                    listener.onClick(ActionEnums.DETAIL, postSummaries.get(position));
                }
            }
        });

        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), CreateFeed.class));
                if (listener != null) {
                    listener.onClick(ActionEnums.DETAIL, null);
                }
            }
        });

        LibRestClient.getRouteService().getFeeds(callback);

    }

    private Callback<ArrayList<FeedPost>> callback = new Callback<ArrayList<FeedPost>>() {
        @Override
        public void success(ArrayList<FeedPost> posts, Response response) {
            progress.dismiss();
            postSummaries.clear();
            postSummaries.addAll(posts);
            adapter.updateTempFeed(posts);
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
    public void onInitiatorClick(FeedPost feed) {
        try {
            mListener.onInitiatorClick(feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    @Override
    public void onCommentClick(FeedPost temp_feed) {
        try {
            mListener.onCommentClick(temp_feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    public interface OnFragmentInteractionListener {
        void onInitiatorClick(FeedPost feed);
        void onCommentClick(FeedPost temp_feed);
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
