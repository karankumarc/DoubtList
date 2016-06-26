package com.techpalle.karan.doubtlist;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by ADMIN on 6/26/2016.
 */
public class DoubtList extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
