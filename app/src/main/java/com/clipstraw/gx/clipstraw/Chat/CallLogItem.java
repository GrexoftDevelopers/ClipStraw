package com.clipstraw.gx.clipstraw.Chat;

/**
 * Created by Faizzy on 29-01-2016.
 */
public class CallLogItem extends  ChatItem {

    private String numberOfcall;
    private int callActionId , actionId;
    private boolean isAvailable;

    public CallLogItem(String Name, String Time, String ImageUri, String numberOfcall , int callActionId , int actionId ,boolean isAvailable) {
        super(Name, Time, ImageUri);
        this.callActionId =callActionId;
        this.numberOfcall = numberOfcall;
        this.actionId =actionId;
        this.isAvailable =isAvailable;
    }

    protected int getCallActionId() {
        return callActionId;
    }

    protected String getNumberOfcall() {
        return numberOfcall;
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

    protected void setCallActionId(int callActionId) {
        this.callActionId = callActionId;
    }

    protected void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    protected void setNumberOfcall(String numberOfcall) {
        this.numberOfcall = numberOfcall;
    }
}
