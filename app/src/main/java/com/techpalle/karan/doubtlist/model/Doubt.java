package com.techpalle.karan.doubtlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.techpalle.karan.doubtlist.utils.Constants;

import java.util.HashMap;

/**
 * Created by ADMIN on 7/5/2016.
 */
public class Doubt {
    private String doubtTitle, doubtContent, owner;
    private HashMap<String, Object> timestampLastChanged;

    public Doubt() {
    }

    public Doubt(String doubtTitle, String doubtContent, String owner) {
        this.doubtTitle = doubtTitle;
        this.doubtContent = doubtContent;
        this.owner = owner;
        HashMap<String, Object> timestampLastChangedObj = new HashMap<String, Object>();
        timestampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampLastChangedObj;
    }

    public String getDoubtTitle() {
        return doubtTitle;
    }

    public void setDoubtTitle(String doubtTitle) {
        this.doubtTitle = doubtTitle;
    }

    public String getDoubtContent() {
        return doubtContent;
    }

    public void setDoubtContent(String doubtContent) {
        this.doubtContent = doubtContent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {
        return (long)timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
