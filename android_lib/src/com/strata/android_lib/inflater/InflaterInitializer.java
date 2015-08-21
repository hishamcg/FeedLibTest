package com.strata.android_lib.inflater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.utils.LibShowToast;

import java.util.HashMap;

/**
 * Created by hisham on 6/8/15.
 */
public class InflaterInitializer implements FeedItemInflater.InflaterCallback{
    Activity activity;
    LayoutInflater inflater;
    private InflaterCallback inflaterCallback;
    //InflateView inflateView;
    //FeedAdapter1 adapter;
    HashMap<String, Class<? extends FeedItemInflater>> hashMap;
    public InflaterInitializer(Activity activity,LibFeedFragment frag){
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            this.inflaterCallback = frag;
        } catch (ClassCastException e) {
            LibShowToast.setText("Internal Error");
            throw new ClassCastException("Fragment must implement AdapterCallback.");
        }
        //this.adapter = adapter;
        //inflateView = new InflateView(inflater,activity);

        hashMap = new HashMap<>();
        hashMap.put("BloggerScribble",BloggerScribble.class);
        hashMap.put("Article",BloggerScribble.class);
        hashMap.put("Review",BloggerScribble.class);
        hashMap.put("Ask",BloggerScribble.class);
        hashMap.put("Event",Event.class);
        hashMap.put("Bctc",Bctc.class);
        hashMap.put("BlogFeed",BlogFeed.class);
        hashMap.put("Influencer",BlogFeed.class);
        hashMap.put("CivicProblem",CivicProblem.class);
        hashMap.put("Civic News",CivicProblem.class);
        hashMap.put("Recommendation",Recommendation.class);
        hashMap.put("Promotion",Promotion.class);
        hashMap.put("Promote",Promotion.class);
        hashMap.put("Transact",Transact.class);
        hashMap.put("Transaction",Transact.class);

    }

    public void AddHashMap(HashMap<String, Class<? extends FeedItemInflater>> hash_map){
        hashMap.putAll(hash_map);
    }

    public View FillView(final FeedLib temp_feed) throws Exception{
        Class<? extends FeedItemInflater> cls = hashMap.get(temp_feed.getType());
        if(cls!=null){
            //cls = CivicProblem.class;
            FeedItemInflater inf = cls.newInstance();
            return inf.inflate(inflater,activity,temp_feed,this);
        }
        return new View(activity);
    }

    @Override
    public void onInitiatorClick(FeedLib feed) {
        try {
            inflaterCallback.onInitiatorClick(feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    @Override
    public void onCommentClick(FeedLib temp_feed) {
        try {
            inflaterCallback.onCommentClick(temp_feed);
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
    }

    public interface InflaterCallback {
        void onInitiatorClick(FeedLib feed);
        void onCommentClick(FeedLib temp_feed);
    }
}
