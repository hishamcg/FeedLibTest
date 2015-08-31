package com.strata.firstmyle_lib.chat.model;

import java.util.ArrayList;

public class ReplyMessage {
    Boolean success;
    ArrayList<Reply> message;
    String state;

    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public ArrayList<Reply> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<Reply> message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
