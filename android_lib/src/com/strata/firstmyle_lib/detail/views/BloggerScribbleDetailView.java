package com.strata.firstmyle_lib.detail.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.feed.model.FeedPost;
import com.strata.firstmyle_lib.utils.AppUtils;
import com.strata.firstmyle_lib.utils.LibShowToast;

/**
 * Created by nagashree on 27/8/15.
 */
public class BloggerScribbleDetailView extends DetailView {

    private View rootView;

    public BloggerScribbleDetailView (Context context,FeedPost sPost, DetailView.ActionClickListener listener){
        super(context, sPost, listener);
        initialize();

    }

    private void initialize(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        rootView = inflater.inflate(R.layout.common_detail_page, null);

        if(rootView!=null){

            ImageView blog_image = (ImageView)rootView.findViewById(R.id.blog_image);
            TextView title = (TextView) rootView.findViewById(R.id.title);
            TextView subject = (TextView)rootView.findViewById(R.id.subject);
            TextView description = (TextView) rootView.findViewById(R.id.description);
            ImageView publisher_image = (ImageView) rootView.findViewById(R.id.publisher_image);
            TextView initiator_name = (TextView) rootView.findViewById(R.id.publisher_name);
            TextView neighborhood = (TextView) rootView.findViewById(R.id.neighbourhood);
            neighborhood.setText(sPost.getPublisher().getNeighborhood());

//            EditText review_text = (EditText) rootView.findViewById(R.id.review_text);
//            comment_button =(IconTextView)rootView.findViewById(R.id.review_button);
//            ImageView icon_comment = (ImageView)rootView.findViewById(R.id.comment);

            TextView date = (TextView)rootView.findViewById(R.id.time);
            date.setText(sPost.getCreated_at());
            initiator_name.setText(sPost.getPublisher().getName() != null ? sPost.getPublisher().getName() : "Hisham");
            title.setText(sPost.getBloggerScribble().getTitle());
            subject.setText(sPost.getBloggerScribble().getTitle());
            description.setText(sPost.getBloggerScribble().getDetail());

            if(sPost.getBloggerScribble().getImage_urls().getDisplay_url()!=null && !"null".equals(sPost.getBloggerScribble().getImage_urls().getDisplay_url())){
                try{
                    AppUtils.picasoViewThumb(sPost.getBloggerScribble().getImage_urls(), blog_image, this.getContext());
                }catch(Exception e){
                    blog_image.setVisibility(View.GONE);
                }

            }else{
                blog_image.setVisibility(View.GONE);
            }
            this.initCommomViews(rootView);
            this.addView(rootView);
        }
    }
}