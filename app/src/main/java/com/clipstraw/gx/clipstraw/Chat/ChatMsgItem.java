package com.clipstraw.gx.clipstraw.Chat;

/**
 * Created by Faizzy on 31-01-2016.
 */
public class ChatMsgItem {

    private String msgContent,time;
    private boolean hasSeen;
    private boolean isIncomming;

    public ChatMsgItem(String msgContent, String time, boolean hasSeen, boolean isIncomming) {
        this.msgContent = msgContent;
        this.time = time;
        this.hasSeen = hasSeen;
        this.isIncomming = isIncomming;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public String getTime() {
        return time;
    }

    public boolean isHasSeen() {
        return hasSeen;
    }

    public boolean isIncomming() {
        return isIncomming;
    }


}
