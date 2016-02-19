package com.clipstraw.gx.clipstraw.model;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.MessageRequest;
import com.clipstraw.gx.clipstraw.request.Request;
import com.clipstraw.gx.clipstraw.timeline.ClipstrawEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by tahir on 2/15/2016.
 */
public class SharedMessage {

    private SharedMessageListener sharedMessageListener;

    //store the id of message
    private String messageId;

    //stores the concerned user
    private UserSkeleton user;

    //stores whether message is incoming or outgoing
    private boolean isIncoming;

    //stores the content of message
    private String content;

    //stores the event shared by the message
    private ClipstrawEvent event;

    private boolean isSeen;

    private String error;

    public void setSharedMessageListener(SharedMessageListener sharedMessageListener) {
        this.sharedMessageListener = sharedMessageListener;
    }

    public SharedMessage(String messageId, UserSkeleton user, boolean isIncoming, String content, ClipstrawEvent event, boolean isSeen) {
        this.messageId = messageId;
        this.user = user;
        this.isIncoming = isIncoming;
        this.content = content;
        this.event = event;
        this.isSeen = isSeen;
    }

    public static SharedMessage createFromJSON(JSONObject sharedMessageJSON) {

        try {
            String messageId = sharedMessageJSON.getString("message_id");
            String content = sharedMessageJSON.getString("content");
            boolean isSeen = sharedMessageJSON.getBoolean("is_seen");
            boolean isIncoming = true;

            JSONObject userJSON = sharedMessageJSON.getJSONObject("user");
            String userId = userJSON.getString("user_id");
            String name = userJSON.getString("name");
            String profileImageUrl = userJSON.getString("profile_image_url");

            UserSkeleton user = new UserSkeleton(userId, name, profileImageUrl);

            ClipstrawEvent event = null;
            if(sharedMessageJSON.has("event")){
                JSONObject eventJSON = sharedMessageJSON.getJSONObject("event");
                String eventTitle = eventJSON.getString("event_title");
                String eventId = eventJSON.getString("event_id");
                Date eventDate = (Date) eventJSON.get("event_date");

                event = new ClipstrawEvent(eventId, eventDate, eventTitle, null, null, null);
            }

            return new SharedMessage(messageId, user, isIncoming, content, event, isSeen);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getMessageId() {
        return messageId;
    }

    public UserSkeleton getUser() {
        return user;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public String getContent() {
        return content;
    }

    public ClipstrawEvent getEvent() {
        return event;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public String getError() {
        return error;
    }

    public void send() {
    }


    public void getAllSharedMessages() {

        Request getAllSharedMessagesRequest = new MessageRequest(MessageRequest.ALL_SHARED_MESSAGES, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {

                        ArrayList<SharedMessage> sharedMessages = null;
                        JSONArray sharedMessagesJSON = response.getJSONArray("data");
                        if (sharedMessagesJSON.length() > 0) {
                            sharedMessages = new ArrayList<SharedMessage>();
                            for (int i = 0; i < sharedMessagesJSON.length(); i++) {
                                JSONObject sharedMessage = sharedMessagesJSON.getJSONObject(i);
                                sharedMessages.add(createFromJSON(sharedMessage));
                            }
                        }
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onSharedMessagesFetched(sharedMessages);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        SharedMessage.this.error = response.getJSONObject("error").getString("error_msg");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getAllSharedMessagesRequest.execute();
    }

    public void getAllSharedMessages(String eventId) {

        Request getAllSharedMessagesRequest = new MessageRequest(MessageRequest.ALL_SHARED_MESSAGES, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {

                        ArrayList<SharedMessage> sharedMessages = null;
                        JSONArray sharedMessagesJSON = response.getJSONArray("data");
                        if (sharedMessagesJSON.length() > 0) {
                            sharedMessages = new ArrayList<SharedMessage>();
                            for (int i = 0; i < sharedMessagesJSON.length(); i++) {
                                JSONObject sharedMessage = sharedMessagesJSON.getJSONObject(i);
                                sharedMessages.add(createFromJSON(sharedMessage));
                            }
                        }
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onSharedMessagesFetched(sharedMessages);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        SharedMessage.this.error = response.getJSONObject("error").getString("error_msg");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getAllSharedMessagesRequest.execute();
    }

    public void sentMessage() {

        Request sentMessageRequest = new MessageRequest(MessageRequest.SENT_MESSAGE, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        messageId = data.getString("message_id");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onMessageSent(messageId);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        SharedMessage.this.error = response.getJSONObject("error").getString("error_msg");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Bundle params = new Bundle();
        params.putString("user_id", user.getUserId());
        params.putString("event_id", event.getEventId());
        params.putString("content", getContent());
        sentMessageRequest.setParameters(params);
        sentMessageRequest.execute();
    }

    public void deleteMessage() {

        Request deleteMessageRequest = new MessageRequest(MessageRequest.DELETE_MESSAGE, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("eror")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        messageId = data.getString("message_id");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onMesageDeleted(messageId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        SharedMessage.this.error = response.getJSONObject("error").getString("error_msg");
                        if (sharedMessageListener != null) {
                            sharedMessageListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Bundle params = new Bundle();
        params.putString("message_id", getMessageId());
        deleteMessageRequest.setParameters(params);
        deleteMessageRequest.execute();
    }

    public interface SharedMessageListener {

        public void onSharedMessagesFetched(ArrayList<SharedMessage> sharedMessages);

        public void onMessageSent(String messageId);

        public void onMesageDeleted(String messageId);

        public void onError();
    }
}
