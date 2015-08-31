package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.strata.firstmyle_lib.chat.ChatInitializer;
import com.strata.firstmyle_lib.chat.fragment.LibChatFragment;
import com.strata.firstmyle_lib.chat.model.Reply;
import com.strata.firstmyle_lib.chat.views.ChatView;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.LibShowToast;

import java.util.HashMap;

/**
 * Created by hisham on 28/8/15.
 */
public class ChatPage extends AppCompatActivity implements LibChatFragment.OnFragmentInteractionListener{
    private static Context context;
    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main);
        context = this;
        HashMap<String, Class<? extends ChatView>> hashMap = new HashMap<>();
        ChatInitializer fed = new ChatInitializer(chat_listener,hashMap);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, fed.getChat())
                .commit();
    }

    @Override
    public void onFragmentInteraction() {

    }

    private static final ChatView.ChatClickListener chat_listener = new ChatView.ChatClickListener() {
        @Override
        public void onClick(ActionEnums action, Reply reply) {

            switch (action) {
                case DETAIL:
                    Intent in = new Intent(context,DetailPage.class);
                    in.putExtra("sPost", new Gson().toJson(reply));
                    context.startActivity(in);
                    break;
                default:
                    LibShowToast.setText("Clicked => " + action);
                    break;

            }
        }
    };
}
