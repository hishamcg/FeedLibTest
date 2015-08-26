package com.strata.firstmyle_lib.feed.model;

import com.strata.firstmyle_lib.model.Publisher;

/**
 * Created by johnpollo on 24/07/15.
 */
public class Reply {


    String time;
    String comment;
    Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
