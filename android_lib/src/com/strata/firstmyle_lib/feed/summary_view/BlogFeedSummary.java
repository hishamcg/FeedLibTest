package com.strata.firstmyle_lib.feed.summary_view;

import android.content.Context;
import android.view.View;

import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.views.BlogFeedView;
import com.strata.firstmyle_lib.feed.views.PostView;

/**
 * Created by hisham on 12/8/15.
 */
public class BlogFeedSummary extends PostSummary {
    @Override
    public View getView(Context context, FeedPost sPost, PostView.ActionClickListener listener){
        super.getView(context, sPost, listener);

        BlogFeedView blogFeedView = new BlogFeedView(context,sPost,listener);

        return blogFeedView.getView();
    }
}
