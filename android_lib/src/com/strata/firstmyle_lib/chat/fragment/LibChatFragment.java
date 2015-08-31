package com.strata.firstmyle_lib.chat.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.chat.adapter.ChatAdapter;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.chat.model.ReplyMessage;
import com.strata.firstmyle_lib.chat.views.ChatView;
import com.strata.firstmyle_lib.model.Publisher;
import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LibChatFragment extends Fragment implements ChatAdapter.AdapterCallback {
	private ChatAdapter chat_adapter;
    private ArrayList<Reply> reply_list =  new ArrayList<>();
	private String feed_id;
	private ListView listview;
    private Context context;
	private IconTextView button;
	private EditText edit_message;
    private OnFragmentInteractionListener mListener;
    private HashMap<String, Class<? extends ChatView>> hashMap;
    private ChatView.ChatClickListener listener;

    public void AddHashMap(HashMap<String, Class<? extends ChatView>> hashMap){
        this.hashMap = hashMap;
    }

    public void AddListener(ChatView.ChatClickListener listener){
        this.listener = listener;
    }

    @Override
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     context = getActivity().getApplicationContext();
	 }


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("just reahced on createView");
		View rootView = inflater.inflate(R.layout.chat_fragment, container, false);

		listview = (ListView) rootView.findViewById(R.id.listview);
		button = (IconTextView) rootView.findViewById(R.id.chat_send);
		edit_message = (EditText) rootView.findViewById(R.id.chat_text);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
        System.out.println("just reahced on Actvity created");
		// get feed id
        Bundle bund = getArguments();
		feed_id = bund.getString("feed_id");
        System.out.println("going for retro");

        reply_list.addAll(getDummyData());
		chat_adapter = new ChatAdapter(getActivity(), reply_list,listener,"123",hashMap);
		listview.setAdapter(chat_adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setActionOnClick(ActionEnums.CHAT, reply_list.get(position));
            }
        });



		if(reply_list.size() > 0){
			listview.setSelection(chat_adapter.getCount() - 1);
		}

		button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!edit_message.getText().toString().isEmpty()) {
                    String msg = edit_message.getText().toString();
                    try {
                        msg = URLEncoder.encode(msg, HTTP.UTF_8);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    LibRestClient.getRouteService().sendMessage(feed_id, msg, callback);
                    edit_message.setText("");
                }

            }
        });

        //LibRestClient.getRouteService().sendMessage(feed_id, "", callback);
	}

    private void setActionOnClick(ActionEnums actionEnums, Reply reply){
        if (listener != null) {
            listener.onClick(actionEnums, reply);
        }
    }

    private Callback<ReplyMessage> callback = new Callback<ReplyMessage>(){
        @Override
        public void success(ReplyMessage replyMessage, Response response) {
            updateFeed(replyMessage.getMessage());
        }

        @Override
        public void failure(RetrofitError error) {
            LibShowToast.setText("failed to udpate chat");
        }
    };

	//This is the handler that will manager to process the broadcast intent
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	    @Override

	    public void onReceive(Context context, Intent intent) {
	    	Bundle bund = intent.getExtras();
            if (bund.getString("feed_id").equals(feed_id)) {
                if(bund.containsKey("reply")){
                    ReplyMessage replyMsg = new Gson().fromJson(bund.getString("reply"),ReplyMessage.class);
                    updateFeed(replyMsg.getMessage());
                }
            }
	    }
	};

	void updateFeed(ArrayList<Reply> replyList) {
        if (replyList!=null){
            for(Reply rep:replyList){
                if(feed_id.equals(String.valueOf(rep.getFeed_id()))){
                    Reply r = reply_list.size()>0?reply_list.get(reply_list.size() - 1):null;
                    if (r!=null && r.getConsumer_id() == rep.getConsumer_id() && rep.getType().equals("MS") && r.getType().equals("MS")) {
                        r.setContent(r.getContent() + "\n" + rep.getContent());
                        r.setReply_id(rep.getReply_id());
                    }else
                        reply_list.add(rep);
                }
            }
        }
        chat_adapter.notifyDataSetChanged();

        if (reply_list.size() > 0){
            listview.setSelection(chat_adapter.getCount() - 1);
        }
	}

	//Must unregister onPause()
	@Override
	public void onPause() {
	    super.onPause();
	    context.unregisterReceiver(mMessageReceiver);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
    }

    //register your activity onResume()
	@Override
	public void onResume() {
        super.onResume();
        context.registerReceiver(mMessageReceiver, new IntentFilter("com.strata.firstmyle_lib.chat"));
	}

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
    }

    @Override
    public void onVoteClick() {
        try {
            mListener.onFragmentInteraction();
        } catch (ClassCastException exception) {
            LibShowToast.setText("Internal Error");
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }


    public ArrayList<Reply> getDummyData(){
        ArrayList<Reply> replies = new ArrayList<>();

        Reply reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("123");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        Publisher pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("123");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("MESSAGE");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("123");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("123");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("CHAT_IMAGE");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("123");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("123");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("PLATFORM");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("123");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("123");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("CONTACT");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("13");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("13");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("MESSAGE");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("13");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("13");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("CHAT_IMAGE");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("13");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("13");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("PLATFORM");
        replies.add(reply);

        reply = new Reply();
        reply.setContent("hahahhaha");
        reply.setConsumer_id("13");
        reply.setFeed_id("123");
        reply.setImage_url("https://scdailymakeover.files.wordpress.com/2010/08/hayden-panettiere-face-shape-hairstyle-cropped-proto-custom_9.jpg");
        pub = new Publisher();
        pub.setName("hisham");
        pub.setImage("http://www.prettydesigns.com/wp-content/uploads/2013/04/Katherine-Heigl-Short-Bob-Hairstyle-for-Round-Face-Shapes-200x200.jpg");
        pub.setPublisher_id("13");
        reply.setPublisher(pub);
        reply.setTime("12:12");
        reply.setType("CONTACT");
        replies.add(reply);

        return replies;

    }
}
