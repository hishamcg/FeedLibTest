package com.strata.android_lib.model;



import java.util.ArrayList;

public class FeedLib{
    Mention mentions;
    ImageUrl image_urls;
    ArrayList<FmProduct> products;
    ArrayList<Reply> comments;
    FeedBasic post_header;
    String feed_id;
    String type;
    String from_date;
    String to_date;
    String venue;
    String created_at;
    //Boolean action_taken;
    ArrayList<User> likes;

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public boolean findLikes(String user_id){
        for (int i=0;i<getLikes().size();i++){
            if(getLikes().get(i).getUser_id().equals(user_id))
                return  true;
        }
        return false;
    }

    public ImageUrl getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(ImageUrl image_urls) {
        this.image_urls = image_urls;
    }
//    public Boolean getAction_taken() {
//        return action_taken;
//    }
//
//    public void setAction_taken(Boolean action_taken) {
//        this.action_taken = action_taken;
//    }

    public FeedBasic getPost_header() {
        return post_header;
    }

    public void setPost_header(FeedBasic post_header) {
        this.post_header = post_header;
    }


    public Mention getMentions() {
        return mentions;
    }

    public void setMentions(Mention mentions) {
        this.mentions = mentions;
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







    public FeedLib(){
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public ArrayList<FmProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<FmProduct> products) {
        this.products = products;
    }

}
