package com.strata.firstmyle_lib.detail.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strata.firstmyle_lib.R;

/**
 * Created by hisham on 26/8/15.
 */
public class EventFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.listview_item_ev0, container, false);
        return rootView;
    }
}
