package com.strata.firstmyle_lib.detail.views;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.feed.views.PostView;
import com.strata.firstmyle_lib.utils.ActionEnums;

/**
 * Created by nagashree on 27/8/15.
 */
public class DetailView  extends FrameLayout {

    protected FeedPost sPost;
    private ActionClickListener listener;
    private ImageView share;
    private ImageView like;
    private ImageView comment;
    private ImageView overflow;

    public DetailView(Context context, FeedPost sPost, ActionClickListener l) {
        super(context);
        this.sPost = sPost;
        this.listener = l;
    }

    public interface ActionClickListener {
        void onClick(ActionEnums action, FeedPost sPost);
    }

    private void setActionOnClick(ActionEnums actionEnums){
        if (DetailView.this.listener != null) {
            DetailView.this.listener.onClick(actionEnums, sPost);
        }
    }

    public void initCommomViews(View rowView) {
        share = (ImageView) rowView.findViewById(R.id.share);
        comment = (ImageView) rowView.findViewById(R.id.comment);
        like = (ImageView) rowView.findViewById(R.id.like);
        overflow = (ImageView) rowView.findViewById(R.id.overflow);

        if (overflow != null) {
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFilterPopup(v);
                }
            });
        }
        if (share != null) {
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.SHARE);
                }
            });
        }
        if (like != null) {
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.LIKE);
                }
            });
        }
        if (comment != null) {
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setActionOnClick(ActionEnums.COMMENT);
                }
            });
        }
    }

    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(this.getContext(), v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(com.strata.firstmyle_lib.R.menu.list_item_overflow, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

}
