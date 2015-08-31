package com.strata.firstmyle_lib.create_post.fragment;

/**
 * Created by nagashree on 31/8/15.
 */

import android.view.Gravity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.create_post.model.Biztag;
import com.strata.firstmyle_lib.create_post.model.FmLocation;
import com.strata.firstmyle_lib.rest.LibRestClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hisham on 21/7/15.
 */
public class BizSelectFragment extends Fragment {
    private ArrayList<Biztag> nbs_array = new ArrayList<>();
    private Context context;
    private String[] location_array;
    private ListView frag_listview;
    private SearchView search;
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private OnlistSelectedListener mCallback;

    //Activity must implement this interface. check create_group/CreateGroup for the implementation
    public interface OnlistSelectedListener {
        void onBizSelected(String name, String id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnlistSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.context=getActivity();
        View rootView = inflater.inflate(R.layout.neighbour_select_layout, container, false);
        frag_listview = (ListView)rootView.findViewById(R.id.frag_listview);
        search = (SearchView)rootView.findViewById(R.id.search);
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        frag_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                String name = parent.getAdapter().getItem(position).toString();
                int selec_id = items.indexOf(name);
                mCallback.onBizSelected(name, String.valueOf(nbs_array.get(selec_id).getBiz_id()));

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Nothing needs to happen when the user submits the search string
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
                if(adapter!=null)
                    adapter.getFilter().filter(newFilter);
                return true;
            }
        });

        LibRestClient.getRouteService().getBusiness(new Callback<ArrayList<Biztag>>() {
            @Override
            public void success(ArrayList<Biztag> locations, Response response) {
                nbs_array = locations;
                location_array = new String[locations.size()];
                for (int i = 0; i < locations.size(); i++) {
                    location_array[i] = locations.get(i).getName();
                    items.add(locations.get(i).getName());
                }
                adapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_list_item_1, android.R.id.text1, items);
                frag_listview.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast toast = Toast.makeText(context,
                        "Failed to fetch data",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 170);
                toast.show();
            }
        });
    }

    public void onResume() {
        super.onResume();
        search.clearFocus();
    }



}


