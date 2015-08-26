package com.strata.firstmyle_lib.feed.model;

/**
 * Created by johnpollo on 13/08/15.
 */
public class ImageUrl {
    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    String display_url;
    String thumb_url;
}
