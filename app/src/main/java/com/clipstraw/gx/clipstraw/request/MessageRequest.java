package com.clipstraw.gx.clipstraw.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rehan on 18-02-2016.
 */
public class MessageRequest extends Request {

    private static final String API_NAME = "message";

    public static final String ALL_SHARED_MESSAGES = "all_shared_messages";

    public static final String SENT_MESSAGE = "sent_message";

    public static final String DELETE_MESSAGE = "delete_message";

    public MessageRequest(String apiEndPoint, RequestCallback callback) {
        super(API_NAME, apiEndPoint);
        this.callback = callback;
    }


    @Override
    protected void onRawResponse(String response) {

        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint) {

            case ALL_SHARED_MESSAGES:

                try {
                    JSONArray data = new JSONArray();

                    for (int i = 0; i < 20; i++) {

                        JSONObject message = new JSONObject();
                        message.put("message_id", "msg123");
                        message.put("content", "content");
                        message.put("isSeen", true);

                        JSONObject user = new JSONObject();
                        user.put("name", "User Name");
                        user.put("user_id", "user123");
                        user.put("profile_image_url", "abc");
                        message.put("user", user);

                        if(parameters.getString("event_id") == null){
                            JSONObject event = new JSONObject();
                            event.put("event_id", "evt123");
                            event.put("event_title", "Your Party");
                            event.put("event_date", "01 FEB");
                            message.put("event", event);
                        }
                        data.put(message);
                    }
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case SENT_MESSAGE:

                try {
                    JSONObject data = new JSONObject();
                    data.put("message_id", parameters.getString("message_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case DELETE_MESSAGE:

                try {
                    JSONObject data = new JSONObject();
                    data.put("message_id", parameters.getString("message_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }
}
