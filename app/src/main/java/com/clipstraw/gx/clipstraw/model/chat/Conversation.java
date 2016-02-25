package com.clipstraw.gx.clipstraw.model.chat;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.ClipstrawApplication;
import com.clipstraw.gx.clipstraw.model.ClipstrawError;
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
public abstract class Conversation {
    protected ArrayList<ChatMessageItem> chatMessageList;
    protected int unReadMsgCount;
    protected boolean isUnread;
    protected String ConversationId;
    protected ConversationListener conversationListener;


    public Conversation(String conversationId) {
        this.ConversationId = conversationId;
    }


    public int getUnReadMsgCount() {

        return unReadMsgCount;
    }


    public void setUnReadMsgCount(int unReadMsgCount) {

        this.unReadMsgCount = unReadMsgCount;
    }

    public void markAsUnread() {
        isUnread = true;

    }

    public String getConversationId() {

        return ConversationId;
    }

    public void delete() {
        Request deleteRequest = new ChatRequest(ChatRequest.DELETE, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {

                        String id = response.getString("id");
                        if (conversationListener != null) {
                            conversationListener.onDelete(Conversation.this);
                        }

                    } else {

                        if (conversationListener != null) {
                            conversationListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("id", this.getConversationId());
        deleteRequest.setParameters(parameters);
        deleteRequest.execute();

    }

    public void clear() {
        Request clearRequest = new ChatRequest(ChatRequest.CLEAR, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {
                        String clearId = response.getString("id");
                        if (conversationListener != null) {
                            conversationListener.onClear(clearId);
                        }


                    } else {
                        if (conversationListener != null) {
                            conversationListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("id", this.getConversationId());
        clearRequest.setParameters(params);
        clearRequest.execute();

    }

    public void fetchAllMsg() {

        Request fetchAllmsgRequest = new ChatRequest(ChatRequest.FETCH_ALL_MSG, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {

                        JSONArray chatMsgs = response.getJSONArray("chat_Msgs");
                        if (chatMsgs.length() > 0) {
                            for (int i = 0; i < chatMsgs.length(); i++) {
                                chatMessageList = new ArrayList<ChatMessageItem>();
                                JSONObject chatMsg = chatMsgs.getJSONObject(i);
                                String id = chatMsg.getString("id");
                                String content = chatMsg.getString("content");
                                String time = chatMsg.getString("time");

                                JSONObject sender = chatMsg.getJSONObject("sender");
                                String userId = sender.getString("user_id");
                                String profileImgUrl = sender.getString("profile_img_url");
                                String name = sender.getString("user_name");

                                UserSkeleton user = new UserSkeleton(userId, name, profileImgUrl);
                                ChatMessageItem chatMessageItem;

                                if (ClipstrawApplication.getInstance().getUser().getUserId().equals(userId)) {
                                    chatMessageItem = new ChatMessageItem(id, false, time, content, user);
                                } else {
                                    chatMessageItem = new ChatMessageItem(id, true, time, content, user);
                                }

                                chatMessageList.add(chatMessageItem);
                            }


                        }


                        if (conversationListener != null) {
                            conversationListener.onFetchMsgs();
                        }


                    } else {
                        if (conversationListener != null) {
                            conversationListener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("id", this.getConversationId());
        fetchAllmsgRequest.setParameters(params);
        fetchAllmsgRequest.execute();

    }

    protected interface ConversationListener {

        public void onDelete(Conversation id);

        public void onError(ClipstrawError error);

        public void onClear(String id);

        public void onFetchMsgs();
    }
}
