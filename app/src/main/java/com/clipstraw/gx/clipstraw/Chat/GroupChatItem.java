package com.clipstraw.gx.clipstraw.Chat;

/**
 * Created by Faizzy on 29-01-2016.
 */
public class GroupChatItem extends ChatItem {
    private  String msg ,numberOfMsg;

    public GroupChatItem(String Name, String Time, String ImageUri , String msg , String numberOfMsg) {
        super(Name, Time, ImageUri);
        this.msg =msg;
        this.numberOfMsg =numberOfMsg;
    }

    protected String getMsg() {
        return msg;
    }

    protected String getNumberOfMsg() {
        return numberOfMsg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    protected void setNumberOfMsg(String numberOfMsg) {
        this.numberOfMsg = numberOfMsg;
    }
}
