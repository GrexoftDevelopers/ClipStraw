package com.clipstraw.gx.clipstraw.model.chat;

import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;

/**
 * Created by Faizzy on 25-02-2016.
 */
public class BinaryConversation extends  Conversation {
    private UserSkeleton partner;
    private String id;


    public BinaryConversation(String conversationId, UserSkeleton partner) {
        super(conversationId);
        this.partner =partner;
    }

    public UserSkeleton getPartner() {
        return partner;
    }

}
