package com.strata.android_lib.model;


import java.util.ArrayList;

/**
 * Created by hisham on 20/7/15.
 */
public class Publisher {
    String publisherType;
    String publisher_id;
    String name;
    String image;
    String neighborhood;
    String banner;
    ArrayList<InitiatorPost> posts;

    public ArrayList<InitiatorPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<InitiatorPost> posts) {
        this.posts = posts;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }




    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPublisher_type() {
        return publisherType;
    }

    public void setPublisher_type(String publisherType) {
        this.publisherType = publisherType;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
