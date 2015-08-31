package com.strata.firstmyle_lib.feed.model;

import com.strata.firstmyle_lib.model.Publisher;

import java.util.ArrayList;

/**
 * Created by hisham on 24/8/15.
 */
public class FeedPost {

    String feed_id;
    String type;
    String created_at;
    ArrayList<User> likes;
    ArrayList<Reply> comments;
    Publisher publisher;

    Bctc bctc;
    BlogFeed blogFeed;
    BloggerScribble blogger_scribble;
    CivicProblem civicProblem;
    Event event;
    Promotion promotion;
    Recommendation recommendation;
    Transact transact;


    public boolean findLikes(String user_id){
        for (int i=0;i<getLikes().size();i++){
            if(getLikes().get(i).getUser_id().equals(user_id))
                return  true;
        }
        return false;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public ArrayList<Reply> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Reply> comments) {
        this.comments = comments;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Bctc getBctc() {
        return bctc;
    }

    public void setBctc(Bctc bctc) {
        this.bctc = bctc;
    }

    public BlogFeed getBlogFeed() {
        return blogFeed;
    }

    public void setBlogFeed(BlogFeed blogFeed) {
        this.blogFeed = blogFeed;
    }

    public BloggerScribble getBloggerScribble() {
        return blogger_scribble;
    }

    public void setBloggerScribble(BloggerScribble bloggerScribble) {
        this.blogger_scribble = bloggerScribble;
    }

    public CivicProblem getCivicProblem() {
        return civicProblem;
    }

    public void setCivicProblem(CivicProblem civicProblem) {
        this.civicProblem = civicProblem;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Transact getTransact() {
        return transact;
    }

    public void setTransact(Transact transact) {
        this.transact = transact;
    }
}
