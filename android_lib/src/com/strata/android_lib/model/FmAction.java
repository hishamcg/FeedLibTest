package com.strata.android_lib.model;

import com.orm.SugarRecord;

/**
 * Created by hisham on 20/7/15.
 */
public class FmAction extends SugarRecord {
    String type;
    String label;
    String amount;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
