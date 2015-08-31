package com.strata.firstmyle_lib.create_post.model;

/**
 * Created by nagashree on 31/8/15.
 */
public class Biztag {

    String name,biz_id;

    public Biztag(String name,String biz_id){
        this.name = name;
        this.biz_id = biz_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiz_id() {
        return biz_id;
    }

    public void setBiz_id(String biz_id) {
        this.biz_id = biz_id;
    }
}
