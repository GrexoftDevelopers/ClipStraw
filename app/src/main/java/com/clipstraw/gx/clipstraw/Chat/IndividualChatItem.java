package com.clipstraw.gx.clipstraw.Chat;

/**
 * Created by Faizzy on 29-01-2016.
 */
public class IndividualChatItem extends GroupChatItem {

    private int actionId;
    private boolean isAvailable;

    public IndividualChatItem(String Name, String Time, String ImageUri, String msg, String numberOfMsg ,int actionId , boolean isAvailable) {
        super(Name, Time, ImageUri, msg, numberOfMsg);
        this.actionId=actionId;
        this.isAvailable =isAvailable;
    }

    protected int getActionId() {
        return actionId;
    }

    protected boolean isAvailable() {
        return isAvailable;
    }

    protected void setActionId(int actionId) {
        this.actionId = actionId;
    }

    protected void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
