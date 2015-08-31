package com.strata.firstmyle_lib.feed.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.strata.firstmyle_lib.feed.PostSummaryInflater;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.summary_view.PostSummary;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedAdapter1 extends BaseAdapter implements Filterable {
    private List<FeedPost> posts;
    private List<FeedPost> tempFeeds = new ArrayList<>();
    private PostSummaryInflater helper;
    private boolean isFiltered = false;
    private Context context;
    private PostView.ActionClickListener listener;

    public FeedAdapter1(Context context, List<FeedPost> posts,PostView.ActionClickListener listener,
                        HashMap<String, Class<? extends PostSummary>> hashMap){
        this.posts = posts;
        this.tempFeeds.addAll(posts);
        this.context = context;
        this.listener = listener;
        this.helper = new PostSummaryInflater(context);
        if(hashMap!=null)
            helper.AddHashMap(hashMap);
    }
    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        try {
            rowView =helper.FillView(posts.get(position),listener);
            return rowView;
        }catch (Exception e){
            System.out.println(e.toString());
            LibShowToast.setText(e.getMessage());
        }
        return new View(context);

    }

    public int getViewTypeCount() {
        return 7;
    }

    @Override
    public Filter getFilter() {
        return mItemFilter;
    }
    private final Filter mItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString();
            FilterResults results = new FilterResults();
            final ArrayList<FeedPost> nfeeds = new ArrayList<>();
            Log.d("typedText", filterString);

            if(!filterString.equals("All")) {
                for (FeedPost f : tempFeeds) {
                    if (f.getType().equals(filterString))
                        nfeeds.add(f);
                }
                isFiltered = true;
            }else{
                nfeeds.addAll(tempFeeds);
                isFiltered = false;
            }

            if(nfeeds.isEmpty()){
                LibShowToast.setTextThread("No posts on " + filterString + " for you.");
            }

            results.values = nfeeds;
            results.count = nfeeds.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null){
                ArrayList<FeedPost> tmp = (ArrayList<FeedPost>) results.values;
                posts.clear();
                posts.addAll(tmp);
                notifyDataSetChanged();
            }
        }

    };


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public boolean removeFilter() {
        if(isFiltered){
            posts.clear();
            posts.addAll(tempFeeds);
            notifyDataSetChanged();
            isFiltered = false;
            return true;
        }else
            return false;
    }

    public void updateTempFeed(List<FeedPost> feeds){
        tempFeeds.clear();
        tempFeeds.addAll(feeds);
    }
}
