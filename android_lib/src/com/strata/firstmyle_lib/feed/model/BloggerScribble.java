package com.strata.firstmyle_lib.feed.model;

/**
 * Created by hisham on 24/8/15.
 */
public class BloggerScribble {
    ImageUrl image_urls;
    String detail;
    String summary;
    String title;

    public ImageUrl getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(ImageUrl image_urls) {
        this.image_urls = image_urls;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
