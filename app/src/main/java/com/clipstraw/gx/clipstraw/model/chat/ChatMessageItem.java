package com.clipstraw.gx.clipstraw.model.chat;

import com.clipstraw.gx.clipstraw.model.user.User;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class ChatMessageItem {

    private User partnerUser;
    private boolean isIncoming;
    private String time;
    private boolean isSeen;
    private boolean isDelivered;
    private boolean isSent;
    private String content;
    private int dataSize;

    public ChatMessageItem(User partnerUser, boolean isIncoming, String time, String content) {
        this.partnerUser = partnerUser;
        this.isIncoming = isIncoming;
        this.time = time;
        this.content = content;
    }

    public User getPartnerUser() {
        return partnerUser;
    }


    public boolean isIncoming() {
        return isIncoming;
    }


    public String getTime() {
        return time;
    }


    public boolean isSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }


    public String getContent() {
        return content;
    }


    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    private void  receive(){

    }
    private void  delete(){

    }
    private  void send (){

    }

}
