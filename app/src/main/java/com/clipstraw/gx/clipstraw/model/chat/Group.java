package com.clipstraw.gx.clipstraw.model.chat;

import com.clipstraw.gx.clipstraw.model.user.User;

import java.net.URI;
import java.util.List;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Group {

    private String groupName;
   // private ClipstrawMedia clipstrawMedia;
    private URI displayImageUri;
    private List<User> memberUsersList;
    private List<ChatMessageItem> chatMessageList;
    private int ubReadMsgCount;

    public Group(String groupName, List<User> memberUsersList, List<ChatMessageItem> chatMessageList, URI displayImageUri) {
        this.groupName = groupName;
        this.memberUsersList = memberUsersList;
        this.chatMessageList = chatMessageList;
        this.displayImageUri = displayImageUri;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public URI getDisplayImageUri() {
        return displayImageUri;
    }

    public void setDisplayImageUri(URI displayImageUri) {
        this.displayImageUri = displayImageUri;
    }

    public int getUbReadMsgCount() {
        return ubReadMsgCount;
    }

    public void setUbReadMsgCount(int ubReadMsgCount) {
        this.ubReadMsgCount = ubReadMsgCount;
    }

//    public ClipstrawMedia getClipstrawMedia() {
//        return clipstrawMedia;
//    }
//
//    public void setClipstrawMedia(ClipstrawMedia clipstrawMedia) {
//        this.clipstrawMedia = clipstrawMedia;
//    }

    private void  addMember(){

    }
    private  void removeMember(){

    }
    private void  deleteGroup(){

    }
    private void  leaveGroup(){

    }
    private void  markAsUnread(){

    }
    private void  addChatMessage(){

    }
    private void deleteChatMessage(){

    }
}
