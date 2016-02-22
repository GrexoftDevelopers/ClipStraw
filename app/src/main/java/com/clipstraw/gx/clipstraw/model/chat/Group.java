package com.clipstraw.gx.clipstraw.model.chat;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.request.ChatRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Group {

    private String id;

    private String groupName;
    private URI displayImageUri;
    private List<User> memberUsersList;


    public void setGroupListener(GroupListener groupListener) {
        this.groupListener = groupListener;
    }

    private List<ChatMessageItem> chatMessageList;
    private int ubReadMsgCount;
    private GroupListener groupListener;

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

    public String getId() {
        return id;
    }

    private void addMember(final User user) {

        Request addMemberRequest = new ChatRequest(ChatRequest.ADD_MEMBER, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {
                        String userId = response.getString("user_id");
                        memberUsersList.add(user);
                        if (groupListener != null) {
                            groupListener.onAddMember(user);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        parameters.putString("group_id", this.getId());
        addMemberRequest.setParameters(parameters);
        addMemberRequest.execute();

    }

    private void removeMember(final User user) {
        Request removeMemberRequest = new ChatRequest(ChatRequest.REMOVE_MEMBER, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {
                        String userId = response.getString("user_id");
                        memberUsersList.remove(user);
                        if (groupListener != null) {
                            groupListener.onRemoveMember(user);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        parameters.putString("group_id", this.getId());
        removeMemberRequest.setParameters(parameters);
        removeMemberRequest.execute();

    }

    private void deleteGroup(String groupId) {

        Request deleteGroupRequest = new ChatRequest(ChatRequest.DELETE_GROUP, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {
                        String groupId = response.getString("group_Id");
                        if (groupListener != null) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("group_id", this.getId());
        deleteGroupRequest.setParameters(parameters);
        deleteGroupRequest.execute();

    }

    private void leaveGroup() {

    }

    private void markAsUnread() {

    }

    public interface GroupListener {
        public void onAddMember(User user);

        public void onRemoveMember(User user);

        public void onGroupDelete(String id);
        public void onError();
    }

    public static void fetchAllGroups() {
        Request fetchAllGroups = new ChatRequest(ChatRequest.FETCH_ALL_GROUPS, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

            }
        });

    }
}
