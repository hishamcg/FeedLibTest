package com.strata.firstmyle_lib.feed.model;

/**
 * Created by hisham on 24/8/15.
 */
public class Event {
    String from_date;
    String to_date;
    String venue;
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

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
