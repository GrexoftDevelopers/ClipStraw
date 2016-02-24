package com.clipstraw.gx.clipstraw.model.chat;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.request.ChatRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Group {

    private String id;

    private String groupName;
    private String displayImageUri;
    private List<User> memberUsersList;


    private List<ChatMessageItem> chatMessageList;
    private int ubReadMsgCount;
    private boolean isUnread;
    private static GroupListener groupListener;

    public Group(String id, String groupName, String displayImageUri) {
        this.id = id;
        this.groupName = groupName;
        this.displayImageUri = displayImageUri;
    }

    public List<ChatMessageItem> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessageItem> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    public List<User> getMemberUsersList() {
        return memberUsersList;
    }

    public void setMemberUsersList(List<User> memberUsersList) {
        this.memberUsersList = memberUsersList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupListener(GroupListener groupListener) {
        this.groupListener = groupListener;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDisplayImageUri() {
        return displayImageUri;
    }

    public void setDisplayImageUri(String displayImageUri) {
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
                } else {

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
                } else {

                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        parameters.putString("group_id", this.getId());
        removeMemberRequest.setParameters(parameters);
        removeMemberRequest.execute();
    }

    private void deleteGroup() {

        Request deleteGroupRequest = new ChatRequest(ChatRequest.DELETE_GROUP, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {
                        String groupId = response.getString("group_Id");
                        if (groupListener != null) {
                            groupListener.onGroupDelete(Group.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("group_id", this.getId());
        deleteGroupRequest.setParameters(parameters);
        deleteGroupRequest.execute();

    }

    public static void fetchAllGroups() {
        Request fetchAllGroupsRequest = new ChatRequest(ChatRequest.FETCH_ALL_GROUPS, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {
                        ArrayList<Group> groupArrayList = null;
                        JSONArray groups = response.getJSONArray("groups");
                        if (groups.length() > 0) {
                            for (int i = 0; i < groups.length(); i++) {
                                groupArrayList = new ArrayList<Group>();
                                JSONObject group = groups.getJSONObject(i);
                                String groupName = group.getString("group_name");
                                String groupId = group.getString("group_id");
                                String groupImageUrl = group.getString("group_image_url");
                                Group clipStrawGroup = new Group(groupId, groupName, groupImageUrl);
                                groupArrayList.add(clipStrawGroup);

                            }
                        }
                        if (groupListener != null) {
                            groupListener.onGroupFetched();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }

            }
        });

        fetchAllGroupsRequest.execute();

    }

    private void leaveGroup(final User user) {
        Request leaveGroupRequest = new ChatRequest(ChatRequest.LEAVE_GROUP, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                if (!response.has("error")) {
                    try {

                        String userId = response.getString("user_id");
                        memberUsersList.remove(user);
                        if (groupListener != null) {
                            groupListener.onLeaveGroup(user);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        leaveGroupRequest.setParameters(parameters);
        leaveGroupRequest.execute();

    }

    private void markAsUnread() {
        isUnread =true;

    }

    public interface GroupListener {
        public void onAddMember(User user);

        public void onRemoveMember(User user);

        public void onGroupDelete(Group group);

        public void onGroupFetched();

        public void onLeaveGroup(User userId);

        public void onError();
    }


}
