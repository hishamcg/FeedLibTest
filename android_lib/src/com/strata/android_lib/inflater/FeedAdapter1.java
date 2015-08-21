package com.strata.android_lib.inflater;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.utils.LibShowToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedAdapter1 extends BaseAdapter implements Filterable {
    private List<FeedLib> feeds;
    private List<FeedLib> tempFeeds = new ArrayList<>();
    private InflaterInitializer helper;
    private boolean isFiltered = false;
    private Activity activity;

    public FeedAdapter1(Activity activity, List<FeedLib> feeds,LibFeedFragment frag,
                        HashMap<String, Class<? extends FeedItemInflater>> hashMap){
        this.feeds = feeds;
        tempFeeds.addAll(feeds);
        this.activity = activity;
        helper = new InflaterInitializer(activity,frag);
        if(hashMap!=null)
            helper.AddHashMap(hashMap);
    }
    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public Object getItem(int position) {
        return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        try {
            rowView =helper.FillView(feeds.get(position));
            return rowView;
        }catch (Exception e){
            LibShowToast.setText(e.getMessage());
        }
        return new View(activity);

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
            final ArrayList<FeedLib> nfeeds = new ArrayList<>();
            Log.d("typedText", filterString);

            if(!filterString.equals("All")) {
                for (FeedLib f : tempFeeds) {
                    if (f.getType().equals(filterString))
                        nfeeds.add(f);
                }
                isFiltered = true;
            }else{
                nfeeds.addAll(tempFeeds);
                isFiltered = false;
            }

            if(nfeeds.isEmpty()){
                //ShowToast.setTextThread("No posts on " + filterString + " for you.");
            }

            results.values = nfeeds;
            results.count = nfeeds.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null){
                ArrayList<FeedLib> tmp = (ArrayList<FeedLib>) results.values;
                feeds.clear();
                feeds.addAll(tmp);
                notifyDataSetChanged();
            }
        }

    };


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public boolean removeFilter() {
        if(isFiltered){
            feeds.clear();
            feeds.addAll(tempFeeds);
            notifyDataSetChanged();
            isFiltered = false;
            return true;
        }else
            return false;
    }

    public void updateTempFeed(List<FeedLib> feeds){
        tempFeeds.clear();
        tempFeeds.addAll(feeds);
    }
}
