package com.clipstraw.gx.clipstraw.model.chat;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.ClipstrawError;
import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.ChatRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Group extends Conversation {
    private String groupName;
    private String displayImageUrl;
    private static GroupListener groupListener;

    public Group(ArrayList<UserSkeleton> partners, ArrayList<ChatMessageItem> chatMessageList) {

        super(partners, chatMessageList);
    }

    public Group(String id, String groupName ,String displayImageUrl){
        super(id);
        this.groupName =groupName;
        this.displayImageUrl =displayImageUrl;
    }

    public void setGroupListener(GroupListener groupListener) {
        this.groupListener = groupListener;
    }


    public ArrayList<ChatMessageItem> getChatMessageList() {
        return chatMessageList;
    }

    public String getGroupName() {
        return groupName;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDisplayImageUrl() {
        return displayImageUrl;
    }

    public void setDisplayImageUrl(String displayImageUrl) {
        this.displayImageUrl = displayImageUrl;
    }




    private void addMember(final User user) {

        Request addMemberRequest = new ChatRequest(ChatRequest.ADD_MEMBER, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {

                        String userId = response.getString("user_id");
                        partners.add(user);
                        if (groupListener != null) {
                            groupListener.onAddMember(user);
                        }

                    } else {

                        if (groupListener != null) {
                            groupListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                try {
                    if (!response.has("error")) {

                        String userId = response.getString("user_id");
                        partners.remove(user);
                        if (groupListener != null) {
                            groupListener.onRemoveMember(user);
                        }

                    } else {

                        if (groupListener != null) {
                            groupListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        parameters.putString("group_id", this.getId());
        removeMemberRequest.setParameters(parameters);
        removeMemberRequest.execute();
    }


    public static void fetchAllGroups() {
        Request fetchAllGroupsRequest = new ChatRequest(ChatRequest.FETCH_ALL_GROUPS, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {

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


                    } else {

                        if (groupListener != null) {
                            groupListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        fetchAllGroupsRequest.execute();

    }

    private void leaveGroup(final User user) {
        Request leaveGroupRequest = new ChatRequest(ChatRequest.LEAVE_GROUP, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {
                        String userId = response.getString("user_id");
                        partners.remove(user);
                        if (groupListener != null) {
                            groupListener.onLeaveGroup(user);
                        }

                    } else {

                        if (groupListener != null) {
                            groupListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("user_id", user.getUserId());
        leaveGroupRequest.setParameters(parameters);
        leaveGroupRequest.execute();

    }

    public interface GroupListener extends ConversationListener {
        public void onAddMember(User user);

        public void onRemoveMember(User user);

        public void onGroupFetched();

        public void onLeaveGroup(User userId);

    }


}
