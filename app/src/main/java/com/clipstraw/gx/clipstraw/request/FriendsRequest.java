package com.clipstraw.gx.clipstraw.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rehan on 15-02-2016.
 */
public class FriendsRequest extends Request {

    private static final String API_NAME = "friend";

    public static final String FETCH_ALL_FRIENDS = "fetch_all_friends";

    public static final String REMOVE_FRIEND = "remove_friend";

    public static final String UNFOLLOW = "un_follow";

    public static final String FOLLOW = "follow";

    public static final String GET_ALL_FRIEND_REQUEST = "get_all_friend_request";

    public static final String HIDE_FRIEND_REQUEST = "hide_friend_request";

    public static final String ACCEPT_FRIEND_REQUEST = "accept_friend_request";

    public static final String SEND_FRIEND_REQUEST = "send_friend_request";

    public static final String DECLINE_FRIEND_REQUEST = "decline_friend_request";


    public FriendsRequest(String apiEndPoint, RequestCallback callback) {
        super(API_NAME, apiEndPoint);
        this.callback = callback;
    }

    @Override
    protected void onRawResponse(String response) {
        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint) {
            case FETCH_ALL_FRIENDS:

                try {
                    JSONArray friends = new JSONArray();
                    JSONObject friend;
                    for (int i = 0; i < 15; i++) {
                        friend = new JSONObject();
                        friend.put("name", "John Walker");
                        friend.put("mutual_friends", 4);
                        friend.put("user_id", "user1234");
                        friend.put("profile_image_url", "abc");
                        friends.put(friend);
                    }
                    jsonResponse.put("friends", friends);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case REMOVE_FRIEND:
                try {
                    JSONObject data = new JSONObject();
                    data.put("user_id", parameters.getString("user_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case UNFOLLOW:
                try {
                    JSONObject data = new JSONObject();
                    data.put("user_id", parameters.getString("user_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case FOLLOW:
                try {
                    JSONObject data = new JSONObject();
                    data.put("user_id", parameters.getString("user_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case GET_ALL_FRIEND_REQUEST:
                try {
                    JSONArray friendRequests = new JSONArray();
                    JSONObject friendRequest;
                    for (int i = 0; i < 15; i++) {
                        friendRequest = new JSONObject();
                        friendRequest.put("name", "John Walker");
                        friendRequest.put("mutual_friends", 4);
                        friendRequest.put("user_id", "user1234");
                        friendRequest.put("profile_image_url", "abc");
                        friendRequests.put(friendRequest);
                    }
                    jsonResponse.put("friendRequests", friendRequests);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case HIDE_FRIEND_REQUEST:
                try {
                    JSONObject data = new JSONObject();
                    data.put("request_id", parameters.getString("request_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case ACCEPT_FRIEND_REQUEST:
                try {
                    JSONObject data = new JSONObject();
                    data.put("request_id", parameters.getString("request_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case SEND_FRIEND_REQUEST:
                try {
                    JSONObject data = new JSONObject();
                    data.put("user_id", parameters.getString("user_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case DECLINE_FRIEND_REQUEST:
                try {
                    JSONObject data = new JSONObject();
                    data.put("request_id", parameters.getString("request_id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
}
