package com.clipstraw.gx.clipstraw.model.chat;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.ClipstrawError;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.ChatRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Faizzy on 15-02-2016.
 */
public class Conversation {
    protected ArrayList<UserSkeleton> partners;
    protected ArrayList<ChatMessageItem> chatMessageList;
    protected int unReadMsgCount;
    protected boolean isUnread;
    protected String id;
    private ConversationListener conversationListener;


    public Conversation(ArrayList<UserSkeleton> partners, ArrayList<ChatMessageItem> chatMessageList) {
        //initializePartner();
        this.partners = partners;
        this.chatMessageList = chatMessageList;

    }

    public Conversation(String id) {
        this.id =id;
    }


    public int getUnReadMsgCount() {
        return unReadMsgCount;
    }

//    protected void initializePartner() {
//        partners = new ArrayList<UserSkeleton>(1);
//    }

    public void setUnReadMsgCount(int unReadMsgCount) {

        this.unReadMsgCount = unReadMsgCount;
    }

    protected void markAsUnread() {
        isUnread = true;

    }

    public String getId() {

        return id;
    }

    protected void delete() {
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
        parameters.putString("id", this.getId());
        deleteRequest.setParameters(parameters);
        deleteRequest.execute();

    }

    protected void clear() {
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
        params.putString("id", this.getId());
        clearRequest.setParameters(params);
        clearRequest.execute();

    }

    protected void fetchAllMsg() {

        Request fetchAllmsgRequest = new ChatRequest(ChatRequest.FETCH_ALL_MSG, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {

                        String id = response.getString("id");
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
        params.putString("id", this.getId());
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
