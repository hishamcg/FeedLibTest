package com.strata.firstmyle_lib.feed.views;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.utils.ActionEnums;
import com.strata.firstmyle_lib.utils.AppUtils;

/**
 * Created by hisham on 25/8/15.
 */
public class PostView extends FrameLayout {
    private ImageView publisher_image;
    private TextView publisher_name;
    private TextView neighbourhood;
    private TextView created_time;
    private ImageView share;
    private ImageView like;
    private ImageView comment;
    private ImageView overflow;
    protected FeedPost sPost;
    private ActionClickListener listener;

    public PostView(Context context, FeedPost sPost, ActionClickListener l) {
        super(context);
        this.sPost = sPost;
        this.listener = l;
    }


    public interface ActionClickListener {
        void onClick(ActionEnums action, FeedPost sPost);
    }

    public void initCommomViews(View rowView) {
        publisher_image = (ImageView) rowView.findViewById(R.id.publisher_image);
        publisher_name = (TextView) rowView.findViewById(R.id.publisher_name);
        neighbourhood = (TextView) rowView.findViewById(R.id.neighbourhood);
        created_time = (TextView) rowView.findViewById(R.id.time);
        LinearLayout LL_cat = (LinearLayout) rowView.findViewById(R.id.LL_cat);
        RelativeLayout RL_initiator_cont = (RelativeLayout) rowView.findViewById(R.id.RL_initiator_cont);

        AppUtils.setTextView(publisher_name, sPost.getPublisher().getName());
        AppUtils.setTextView(created_time, sPost.getCreated_at());
        AppUtils.setTextView(neighbourhood, sPost.getPublisher().getNeighborhood());
        AppUtils.picasoView(sPost.getPublisher().getImage(), publisher_image, this.getContext());


        share = (ImageView) rowView.findViewById(R.id.share);
        comment = (ImageView) rowView.findViewById(R.id.comment);
        like = (ImageView) rowView.findViewById(R.id.like);
        overflow = (ImageView) rowView.findViewById(R.id.overflow);

        if (like != null)
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.LIKE);
//                    LibRestClient.getRouteService().FeedLike(sPost.getFeed_id(), "Like", new Callback<AuthToken>() {
//                        @Override
//                        public void success(AuthToken authtoken, Response response) {
//                            like.setImageResource(com.strata.firstmyle_lib.R.drawable.ic_action_heart);
//                            LibShowToast.setText("Added to Favourite");
//                        }
//
//                        @Override
//                        public void failure(RetrofitError retrofitError) {
//                            System.out.println(retrofitError);
//                            LibShowToast.setText("Failed to add to Favourite. Try Again Later");
//                        }
//                    });
                }
            });

        if (RL_initiator_cont != null)
            RL_initiator_cont.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.PUBLISHER);
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
                    setActionOnClick(ActionEnums.SHARE);
                }
            });

        if (comment != null)
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.COMMENT);
                }
            });


//            if (temp_feed.findLikes(SharedPref.getStringValue("USER_ID"))) {
//                like.setImageResource(R.drawable.ic_action_heart);
//            }
//            if (LL_cat != null) {
//                final View vi = inflater.getFeed(android.R.layout.list_content, null);
//                final ListView list = (ListView) vi.findViewById(android.R.id.list);
//                final String[] txt = context.getResources().getStringArray(R.array.nav_cat_txt);
//                final CategoryFilterAdapter catAdapter = new CategoryFilterAdapter(context, txt);
//                list.setAdapter(catAdapter);
//
//                AlertDialog.Builder enter = new AlertDialog.Builder(context);
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
    }

    private void setActionOnClick(ActionEnums actionEnums){
        if (PostView.this.listener != null) {
            PostView.this.listener.onClick(actionEnums, sPost);
        }
    }

    public void setOverflow(int drawable) {
        this.overflow.setImageResource(drawable);
    }

    public void setComment(int drawable) {
        this.comment.setImageResource(drawable);
    }

    public void setLike(int drawable) {
        this.like.setImageResource(drawable);
    }

    public void setShare(int drawable) {
        this.share.setImageResource(drawable);
    }

    public void setCreated_time(String txt) {
        AppUtils.setTextView(this.created_time, txt);
    }

    public void setNeighbourhood(String txt) {
        AppUtils.setTextView(this.neighbourhood, txt);
    }

    public void setPublisher_name(String txt) {
        AppUtils.setTextView(this.publisher_name, txt);
    }

    public void setPublisher_image(String imageUrl) {
        AppUtils.picasoView(imageUrl, publisher_image, this.getContext());
    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(this.getContext(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(com.strata.firstmyle_lib.R.menu.list_item_overflow, popup.getMenu());
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

    public interface InflaterCallback {
        void onInitiatorClick(FeedPost feed);
        void onCommentClick(FeedPost temp_feed);
    }
}
