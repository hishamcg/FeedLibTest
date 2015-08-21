package com.strata.android_lib.inflater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strata.android_lib.R;
import com.strata.android_lib.model.AuthToken;
import com.strata.android_lib.model.FeedLib;
import com.strata.android_lib.rest.LibRestClient;
import com.strata.android_lib.utils.AppUtils;
import com.strata.android_lib.utils.LibShowToast;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class FeedItemInflater {
    private Activity activity;
    private LayoutInflater inflater;
    private InflaterCallback inflaterCallback;

    public View inflate(LayoutInflater inflater,Activity activity,FeedLib temp_feed,InflaterInitializer initializer){
        this.activity = activity;
        this.inflater = inflater;
        inflaterCallback = initializer;
        return null;
    }

    public View addWrapper(View rowView, final FeedLib temp_feed){
        ImageView image = (ImageView) rowView.findViewById(R.id.publisher_image);
        TextView name = (TextView) rowView.findViewById(R.id.publisher_name);
        TextView nb = (TextView) rowView.findViewById(R.id.neighbourhood);
        TextView date = (TextView) rowView.findViewById(R.id.time);
        TextView subject = (TextView) rowView.findViewById(R.id.subject);
        LinearLayout LL_cat = (LinearLayout) rowView.findViewById(R.id.LL_cat);
        RelativeLayout RL_initiator_cont = (RelativeLayout) rowView.findViewById(R.id.RL_initiator_cont);

        AppUtils.ValidateTextView(name, temp_feed.getPost_header().getPublisher().getName());
        AppUtils.ValidateTextView(date, temp_feed.getCreated_at());
        AppUtils.ValidateTextView(nb, temp_feed.getPost_header().getPublisher().getNeighborhood());
        AppUtils.ValidateTextView(subject, temp_feed.getPost_header().getTitle());
        AppUtils.picasoView(temp_feed.getPost_header().getPublisher().getImage(), image,activity);


        final ImageView share = (ImageView) rowView.findViewById(R.id.share);
        final ImageView comment = (ImageView) rowView.findViewById(R.id.comment);
        final ImageView like = (ImageView) rowView.findViewById(R.id.like);
        final ImageView overflow = (ImageView) rowView.findViewById(R.id.overflow);


        try {
//            if (temp_feed.findLikes(SharedPref.getStringValue("USER_ID"))) {
//                like.setImageResource(R.drawable.ic_action_heart);
//            }
//            if (LL_cat != null) {
//                final View vi = inflater.inflate(android.R.layout.list_content, null);
//                final ListView list = (ListView) vi.findViewById(android.R.id.list);
//                final String[] txt = activity.getResources().getStringArray(R.array.nav_cat_txt);
//                final CategoryFilterAdapter catAdapter = new CategoryFilterAdapter(activity, txt);
//                list.setAdapter(catAdapter);
//
//                AlertDialog.Builder enter = new AlertDialog.Builder(activity);
//                enter.setView(vi);
//                enter.setTitle("Filter");
//                final Dialog d = enter.create();
//
//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (adapter != null) {
//                            adapter.getFilter().filter(txt[position]);
//                        } else {
//                            ShowToast.setText("failed to load filter");
//                        }
//                        d.dismiss();
//                    }
//                });
//
//                LL_cat.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        d.show();
//                    }
//                });
//            }


            if (like != null)
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LibRestClient.getRouteService().FeedLike(temp_feed.getFeed_id(), "Like", new retrofit.Callback<AuthToken>() {
                            @Override
                            public void success(AuthToken authtoken, Response response) {
                                like.setImageResource(R.drawable.ic_action_heart);
                                LibShowToast.setText("Added to Favourite");
                            }

                            @Override
                            public void failure(RetrofitError retrofitError) {
                                System.out.println(retrofitError);
                                LibShowToast.setText("Failed to add to Favourite. Try Again Later");
                            }
                        });
                    }
                });



            if (RL_initiator_cont != null)
                RL_initiator_cont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            inflaterCallback.onInitiatorClick(temp_feed);
                        } catch (ClassCastException exception) {
                            LibShowToast.setText("Internal Error");
                        }
                    }
                });

            if (overflow != null)
                overflow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFilterPopup(v);
                    }
                });


            if (share != null)
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_message("Share this Post", share, activity, temp_feed.getFeed_id());
                    }
                });
            if (comment != null)
                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            inflaterCallback.onCommentClick(temp_feed);
                        } catch (ClassCastException exception) {
                            LibShowToast.setText("Internal Error");
                        }
                    }

                });
        }catch (Exception e){
            LibShowToast.setText(e.getMessage());
        }
        return rowView;
    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.list_item_overflow, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.gallery:
//                        return true;
//                    case R.id.camera:
//                        return true;
//                    default:
//                        return false;
//                }
                return true;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }


    private void alert_message(String msg, final ImageView icon, final Context context, final String feed_id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Share",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//                        RestClient.getRouteService().FeedShare(feed_id, new retrofit.Callback<AuthToken>() {
//                            @Override
//                            public void success(AuthToken authtoken, Response response) {
//                                //icon.setTextColor(Color.parseColor("#FFFF6600"));
//                                icon.setImageResource(R.drawable.ic_action_share);
//                                ShowToast.setText("Shared Successfully!");
//                            }
//
//                            @Override
//                            public void failure(RetrofitError retrofitError) {
//                                System.out.println(retrofitError);
//                                ShowToast.setText("Share Post Failed. Try Again Later");
//
//                            }
//                        });
                        dialog.cancel();
//                        Toast.makeText(context, "Post Shared", Toast.LENGTH_SHORT).show();
                    }
                });
        builder1.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public interface InflaterCallback {
        void onInitiatorClick(FeedLib feed);
        void onCommentClick(FeedLib temp_feed);
    }
}
