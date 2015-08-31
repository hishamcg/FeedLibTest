package com.strata.firstmyle_lib.detail.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.detail.views.BctcDetailView;
import com.strata.firstmyle_lib.detail.views.BlogFeedDetailView;
import com.strata.firstmyle_lib.detail.views.BloggerScribbleDetailView;
import com.strata.firstmyle_lib.detail.views.CivicProblemDetailView;
import com.strata.firstmyle_lib.detail.views.DetailView;
import com.strata.firstmyle_lib.detail.views.EventDetailView;
import com.strata.firstmyle_lib.detail.views.PromotionDetailView;
import com.strata.firstmyle_lib.detail.views.RecommendationDetailView;
import com.strata.firstmyle_lib.detail.views.TransactDetailView;
import com.strata.firstmyle_lib.feed.model.Bctc;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by hisham on 26/8/15.
 */
public class EventFragment extends Fragment {
    FeedPost feed;
    DetailView.ActionClickListener listener;
    View rootView;
    DetailView detail_view;
    private OnFragmentInteractionListener mListener;
    HashMap<String, Class<? extends DetailView>> hashMap = new HashMap<>();

    public void addListener(DetailView.ActionClickListener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        feed = new Gson().fromJson(getArguments().getString("feed"),FeedPost.class);
        rootView = inflater.inflate(R.layout.post_fragment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hashMap.put("Event", EventDetailView.class);
        hashMap.put("Bctc", BctcDetailView.class);
        hashMap.put("BlogFeed", BlogFeedDetailView.class);
        hashMap.put("BloggerScribble", BloggerScribbleDetailView.class);
        hashMap.put("CivicProblem", CivicProblemDetailView.class);
        hashMap.put("Promotion", PromotionDetailView.class);
        hashMap.put("Recommendation", RecommendationDetailView.class);
        hashMap.put("Transact", TransactDetailView.class);

        Class<? extends DetailView> cls = hashMap.get(feed.getType());
        if (cls != null){
            try {
                Constructor<? extends DetailView> constructor = cls.getConstructor(Context.class,FeedPost.class, DetailView.ActionClickListener.class);
                detail_view = constructor.newInstance(getActivity().getApplicationContext(), feed, listener);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else {
            detail_view = new EventDetailView(getActivity().getApplicationContext(), feed, listener);
        }
        LinearLayout s =(LinearLayout) rootView.findViewById(R.id.detail);
        detail_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDetailClick(feed);
            }
        });
        s.addView(detail_view);
    }

    public interface OnFragmentInteractionListener {
        void onInitiatorClick(FeedPost feed);
        void onCommentClick(FeedPost feed);
        void onDetailClick(FeedPost feed);
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
