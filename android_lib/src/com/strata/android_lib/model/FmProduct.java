package com.strata.android_lib.model;

import com.orm.SugarRecord;

/**
 * Created by hisham on 20/7/15.
 */
public class FmProduct extends SugarRecord {
    String name,image,description,price;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
