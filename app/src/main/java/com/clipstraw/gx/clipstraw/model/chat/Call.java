package com.clipstraw.gx.clipstraw.model.chat;

import com.clipstraw.gx.clipstraw.model.user.User;

import java.util.Date;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Call {
    public User partnerUser;
    private boolean isIncoming;
    private String duration;
    private Date createdAt;
    private int dataTransferred;
    private boolean isOnHold;
    private boolean isConnected;
    private boolean isMissed;
    private boolean isEnded;

    public Call(User partnerUser, boolean isIncoming, Date createdAt) {
        this.partnerUser = partnerUser;
        this.isIncoming = isIncoming;
        this.createdAt = createdAt;
    }


    private void create(){

    }
    private void hold(){

    }
    private void disconnect(){

    }
    private void unHold()
    {

    }
    private  void recieve(){

    }
    private void clearLog(){

    }

    public User getPartnerUser() {
        return partnerUser;
    }


    public boolean isIncoming() {
        return isIncoming;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public int getDataTransferred() {
        return dataTransferred;
    }

    public void setDataTransferred(int dataTransferred) {
        this.dataTransferred = dataTransferred;
    }

    public boolean isOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(boolean isOnHold) {
        this.isOnHold = isOnHold;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setIsEnded(boolean isEnded) {
        this.isEnded = isEnded;
    }

    public boolean isMissed() {
        return isMissed;
    }

    public void setIsMissed(boolean isMissed) {
        this.isMissed = isMissed;
    }
}
