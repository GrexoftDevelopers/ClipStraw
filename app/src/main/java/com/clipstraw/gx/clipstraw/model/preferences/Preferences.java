package com.clipstraw.gx.clipstraw.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.clipstraw.gx.clipstraw.ClipstrawApplication;

/**
 * Created by Rehan on 15-02-2016.
 */
public abstract class Preferences {

    protected SharedPreferences sharedPreferences;

    protected SharedPreferences.Editor editor;

    Preferences(){
        Context mContext = ClipstrawApplication.getInstance().getApplicationContext();
        sharedPreferences = mContext.getSharedPreferences(ClipstrawApplication.PREF_NAME,ClipstrawApplication.PREF_MODE);
        editor = sharedPreferences.edit();
    }

    protected abstract void save();
}
