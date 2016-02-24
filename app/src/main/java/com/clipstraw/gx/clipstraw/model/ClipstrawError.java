package com.clipstraw.gx.clipstraw.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tahir on 2/19/2016.
 */
public class ClipstrawError {

    private int code;

    private String message;

    private String resolution;

    public ClipstrawError(int code, String message, String resolution){
        this.code = code;
        this.message = message;
        this.resolution = resolution;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getResolution() {
        return resolution;
    }

    public static ClipstrawError createError(JSONObject error){

        try {
            int code = error.getInt("code");
            String message = error.getString("message");
            String resolution = error.getString("resolution");
            return new ClipstrawError(code,message,resolution);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
