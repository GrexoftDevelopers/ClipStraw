package com.clipstraw.gx.clipstraw.model.chat;

import com.clipstraw.gx.clipstraw.model.user.User;

import java.util.List;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Conversation {
    private User partnerUser;
    private List<ChatMessageItem> chatMessageList;
    private int unReadMsgCount;

    public Conversation(User partnerUser, List<ChatMessageItem> chatMessageList) {
        this.partnerUser = partnerUser;
        this.chatMessageList = chatMessageList;
    }

    public int getUnReadMsgCount() {
        return unReadMsgCount;
    }

    public void setUnReadMsgCount(int unReadMsgCount) {
        this.unReadMsgCount = unReadMsgCount;
    }

    private void markAsUnread(){

    }
    private  void  delete(){

    }
}
