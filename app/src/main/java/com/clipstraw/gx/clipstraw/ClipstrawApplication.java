package com.clipstraw.gx.clipstraw;


import android.app.Application;
import android.content.Context;

import widgets.User;

/**
 * Created by FaizZy on 07-01-2016.
 */
public class ClipstrawApplication extends Application {

    private static ClipstrawApplication mInstance;

    public static final String PREF_NAME = "clipstraw_preferences";

    public static final int PREF_MODE = Context.MODE_PRIVATE;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
        System.out.println("In setUser");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static ClipstrawApplication getInstance(){
        return mInstance;
    }
}
