package com.clipstraw.gx.clipstraw.model.session;

import com.clipstraw.gx.clipstraw.timeline.ClipstrawEvent;

import java.util.Calendar;

/**
 * Created by tahir on 2/12/2016.
 */
public class ClipstrawSession {

    private String sessionId;

    private String userId;

    private String userEmail;

    private String userName;

    private String createdAt;

    private String refreshedAt;

    private boolean isValid;

    public ClipstrawSession(String sessionId, String userEmail, String userId, String userName){
        this.sessionId = sessionId;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
        this.isValid = true;

    }

    @Override
    public String toString() {
        return isValid ? this.sessionId : "expired";
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRefreshedAt() {
        return refreshedAt;
    }

    public void clear(){
        this.sessionId = null;
        this.userName = null;
        this.userId = null;
        this.userEmail = null;
        this.isValid = false;
    }

    public void expire(){
        isValid = false;
    }
}
