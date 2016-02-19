package com.clipstraw.gx.clipstraw.timeline;


import com.clipstraw.gx.clipstraw.model.user.User;

/**
 * Created by Rehan on 01-02-2016.
 */
public class Message {

    private String msgContent;

    private User user;

    private ClipstrawEvent event;

    public Message(String msgContent, User user, ClipstrawEvent event) {
        this.msgContent = msgContent;
        this.user = user;
        this.event = event;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public User getUser() {
        return user;
    }

    public ClipstrawEvent getEvent() {
        return event;
    }


}
