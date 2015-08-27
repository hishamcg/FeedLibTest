package com.strata.firstmyle_lib.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.strata.firstmyle_lib.chat.ChatInflater;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.chat.views.ChatView;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter{
	private Context context;
    private String publisher_id;
	private ArrayList<Reply> reply_list;
    private AdapterCallback adapterCallback;
    private ChatInflater helper;
    private ChatView.ChatClickListener listener;

	public ChatAdapter(Context context, ArrayList<Reply> reply_list,ChatView.ChatClickListener listener,String publisher_id) {

		this.context = context;
		this.reply_list = reply_list;
        this.helper = new ChatInflater(context);
        this.listener = listener;
        this.publisher_id = publisher_id;
	}

    public interface AdapterCallback {
        void onVoteClick();
    }

	@Override
	public int getCount() {
		return reply_list.size();
	}

	@Override
	public Object getItem(int position) {
		return reply_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View rowView, ViewGroup parent) {
        try {
            rowView = helper.FillView(reply_list.get(position),listener,publisher_id).getRootView();
            return rowView;
        }catch (Exception e){
            throw new ClassCastException("View return problem in ChatAdapter\n"+e.getMessage());
            //LibShowToast.setText(e.getMessage());
        }
        //return new View(context);
	}
	
	public int getViewTypeCount() {
	    return 5;
	}

}
