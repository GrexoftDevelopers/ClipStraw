package com.clipstraw.gx.clipstraw.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Faizzy on 22-02-2016.
 */
public class ChatRequest extends Request {

    private static final String API_NAME = "Chat";
    public static final String ADD_MEMBER = "add_member";
    public static final String REMOVE_MEMBER = "remove_member";
    public static final String DELETE_GROUP = "delete_group";
    public static final String FETCH_ALL_GROUPS = "fetch_all_groups";
    public static final String LEAVE_GROUP = "leave_group";
    public static final String ADD_CHAT_MESSAGE = "add_chat_message";
    public static final String DELETE_CHAT_MESSAGE = "delete_chat_message";
    public static final String MARK_AS_UNREAD = "mark_as_unread";
    public static final String SEND_CHAT = "send_chat";
    public static final String DELETE_CONVERSATION = "delete_conversation";


    public ChatRequest(String apiEndPoint, RequestCallback callback) {

        super(API_NAME, apiEndPoint);
        this.callback = callback;

    }

    @Override
    protected void onRawResponse(String response) {
        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint) {
            case ADD_MEMBER:
                try {
                    jsonResponse.put("user_id", parameters.getString("user_id"));
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case REMOVE_MEMBER:
                try {
                    jsonResponse.put("user_id", parameters.getString("user_id"));
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case DELETE_GROUP:
                try {
                    jsonResponse.put("group_id", parameters.getString("group_id"));
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case FETCH_ALL_GROUPS:

                try {
                    JSONArray groups = new JSONArray();
                    JSONObject group;
                    for (int i = 0; i < 15; i++) {
                        group = new JSONObject();
                        group.put("name", "Fun "+i);
                        group.put("group_id", "group1234");
                        group.put("group_image_url", "abc");
                        group.put("time","12:00");
                        group.put("last_msg","this is a message");
                        groups.put(group);
                    }
                    jsonResponse.put("groups", groups);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case LEAVE_GROUP:
                try{
                    jsonResponse.put("group_id",parameters.getString("group_id"));
                    callback.onCompleted(jsonResponse);

                }catch (JSONException e){
                    e.printStackTrace();
                }


        }


    }
}
