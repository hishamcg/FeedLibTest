package com.strata.firstmyle_lib.create_post.model;

/**
 * Created by nagashree on 31/8/15.
 */
public class FmLocation {
    private String name;
    private String id;
    private int type_id = 0;

    public FmLocation(){

    }

    public FmLocation(String name,String id,int type_id){
        this.name = name;
        this.id = id;
        this.type_id = type_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
