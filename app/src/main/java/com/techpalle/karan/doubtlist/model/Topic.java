package com.techpalle.karan.doubtlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.techpalle.karan.doubtlist.utils.Constants;

import java.util.HashMap;

/**
 * Created by ADMIN on 7/5/2016.
 */
public class Topic {
    private String topicTitle, topicDescription, owner;
    private HashMap<String, Object> timestampLastChanged;

    public Topic() {
    }

    public Topic(String doubtTitle, String doubtContent, String owner) {
        this.topicTitle = doubtTitle;
        this.topicDescription = doubtContent;
        this.owner = owner;
        HashMap<String, Object> timestampLastChangedObj = new HashMap<String, Object>();
        timestampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampLastChangedObj;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
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
