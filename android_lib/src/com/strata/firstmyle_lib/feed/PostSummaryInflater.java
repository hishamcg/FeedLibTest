package com.strata.firstmyle_lib.feed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.strata.firstmyle_lib.feed.fragment.LibFeedFragment;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.BctcSummary;
import com.strata.firstmyle_lib.feed.summary_view.BlogFeedSummary;
import com.strata.firstmyle_lib.feed.summary_view.BloggerScribbleSummary;
import com.strata.firstmyle_lib.feed.summary_view.CivicProblemSummary;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.summary_view.PromotionSummary;
import com.strata.firstmyle_lib.feed.summary_view.RecommendationSummary;
import com.strata.firstmyle_lib.feed.summary_view.TransactSummary;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;

/**
 * Created by hisham on 6/8/15.
 */
public class PostSummaryInflater{
    Context context;
    private InflaterCallback inflaterCallback;
    //InflateView inflateView;
    //FeedAdapter1 adapter;
    HashMap<String, Class<? extends PostSummary>> hashMap;
    public PostSummaryInflater(Context context){
        this.context = context;
        //this.adapter = adapter;
        //inflateView = new InflateView(inflater,context);

        hashMap = new HashMap<>();
        hashMap.put("BloggerScribble",BloggerScribbleSummary.class);
        hashMap.put("Article",BloggerScribbleSummary.class);
        hashMap.put("Review",BloggerScribbleSummary.class);
        hashMap.put("Ask",BloggerScribbleSummary.class);
        //hashMap.put("Event",Event.class);
        hashMap.put("Bctc",BctcSummary.class);
        hashMap.put("BlogFeed",BlogFeedSummary.class);
        hashMap.put("Influencer",BlogFeedSummary.class);
        hashMap.put("CivicProblem",CivicProblemSummary.class);
        hashMap.put("Civic News",CivicProblemSummary.class);
        hashMap.put("Recommendation",RecommendationSummary.class);
        hashMap.put("Promotion",PromotionSummary.class);
        hashMap.put("Promote",PromotionSummary.class);
        hashMap.put("Transact",TransactSummary.class);
        hashMap.put("Transaction",TransactSummary.class);

    }

    public void AddHashMap(HashMap<String, Class<? extends PostSummary>> hash_map){
        hashMap.putAll(hash_map);
    }

    public View FillView(final FeedPost temp_post,PostView.ActionClickListener listener) throws Exception{
        Class<? extends PostSummary> cls = hashMap.get(temp_post.getType());
        if(cls!=null){
            PostSummary inf = cls.newInstance();
            return inf.getView(context,temp_post,listener);
        }
        return new View(context);
    }

//    @Override
//    public void onInitiatorClick(FeedPost feed) {
//        try {
//            inflaterCallback.onInitiatorClick(feed);
//        } catch (ClassCastException exception) {
//            LibShowToast.setText("Internal Error");
//        }
//    }
//
//    @Override
//    public void onCommentClick(FeedPost temp_feed) {
//        try {
//            inflaterCallback.onCommentClick(temp_feed);
//        } catch (ClassCastException exception) {
//            LibShowToast.setText("Internal Error");
//        }
//    }

    public interface InflaterCallback {
        void onInitiatorClick(FeedPost feed);
        void onCommentClick(FeedPost feed);
    }
}
