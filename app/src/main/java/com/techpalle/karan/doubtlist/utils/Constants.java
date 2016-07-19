package com.techpalle.karan.doubtlist.utils;

/**
 * Created by ADMIN on 6/26/2016.
 */
public class Constants {


    /**
     * Constants for Firebase object properties
     */
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where user lists are stored (ie "userLists")
     */
    public static final String FIREBASE_LOCATION_TOPICS ="topics";
    public static final String FIREBASE_LOCATION_QUESTIONS = "questions";
    public static final String FIREBASE_LOCATION_USERS = "users";

    /**
     * Constants for Firebase URL
     */
    public static final String FIREBASE_URL = "https://doubtlist.firebaseio.com/";
    public static final String FIREBASE_URL_QUESTIONS = FIREBASE_URL +"/"+FIREBASE_LOCATION_QUESTIONS;
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    /**
     * Constants for bundles, extras and shared preferences keys
     */

}
