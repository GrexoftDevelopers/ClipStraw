package com.clipstraw.gx.clipstraw.model.friend;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.FriendsRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rehan on 15-02-2016.
 */
public class FriendRequest {

    private FriendRequestListener friendRequestListener;

    private String requestId;

    private String time;

    private boolean isAccepted;

    private UserSkeleton user;

    private String error;

    public void setFriendRequestListener(FriendRequestListener friendRequestListener) {
        this.friendRequestListener = friendRequestListener;
    }

    public FriendRequest(String time, boolean isAccepted, UserSkeleton user, String requestId) {
        this.time = time;
        this.isAccepted = isAccepted;
        this.user = user;
        this.requestId = requestId;
    }

    private static FriendRequest createFromJSON(JSONObject friendRequestJSON){
        try {
            String requestId = friendRequestJSON.getString("request_id");
            boolean isAccepted = friendRequestJSON.getBoolean("is_accepted");
            String time = friendRequestJSON.getString("time");

            JSONObject userJSON = friendRequestJSON.getJSONObject("user");
            String userId = userJSON.getString("user_id");
            String name = userJSON.getString("name");
            String profileImageUrl = userJSON.getString("profile_image_url");

            UserSkeleton user = new UserSkeleton(userId,name,profileImageUrl);

            return new FriendRequest(time,isAccepted,user,requestId);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTime() {
        return time;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public UserSkeleton getUser() {
        return user;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getError() {
        return error;
    }

    public void getAllFriendRequest() {

        Request getFriendRequest = new FriendsRequest(FriendsRequest.GET_ALL_FRIEND_REQUEST, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        ArrayList<FriendRequest> friendRequests = null;
                        JSONArray friendRequestsJSON = response.getJSONArray("friendRequests");
                        if (friendRequestsJSON.length() > 0) {
                            friendRequests = new ArrayList<FriendRequest>();
                            for (int i = 0; i < friendRequestsJSON.length(); i++) {
                                JSONObject friendRequest = friendRequestsJSON.getJSONObject(i);
                                friendRequests.add(createFromJSON(friendRequest));
                            }
                        }
                        if (friendRequestListener != null) {
                            friendRequestListener.onFriendRequestsFetched(friendRequests);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FriendRequest.this.error = response.getJSONObject("error").getString("error_msg");
                        if (friendRequestListener != null) {
                            friendRequestListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        getFriendRequest.execute();
    }

    public void hideFriendRequest(FriendRequest friendRequest) {

        Request hideFriendRequest = new FriendsRequest(FriendsRequest.HIDE_FRIEND_REQUEST, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String requestId = data.getString("request_id");
                        if (friendRequestListener != null) {
                            friendRequestListener.onFriendRequestHidden(requestId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FriendRequest.this.error = response.getJSONObject("error").getString("error_msg");
                        if (friendRequestListener != null) {
                            friendRequestListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("request_id", friendRequest.getRequestId());
        hideFriendRequest.setParameters(params);
        hideFriendRequest.execute();
    }

    public void acceptFriendRequest(FriendRequest friendRequest) {

        Request acceptFriendRequest = new FriendsRequest(FriendsRequest.ACCEPT_FRIEND_REQUEST, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String requestId = data.getString("request_id");
                        if (friendRequestListener != null) {
                            friendRequestListener.onFriendRequestAccepted(requestId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FriendRequest.this.error = response.getJSONObject("error").getString("error_msg");
                        if (friendRequestListener != null) {
                            friendRequestListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("request_id", friendRequest.getRequestId());
        acceptFriendRequest.setParameters(params);
        acceptFriendRequest.execute();
    }

    public void sendFriendRequest(Friend friend) {

        Request sendFriendRequest = new FriendsRequest(FriendsRequest.SEND_FRIEND_REQUEST, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String userId = data.getString("user_id");
                        if (friendRequestListener != null) {
                            friendRequestListener.onFriendRequestSent(userId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FriendRequest.this.error = response.getJSONObject("error").getString("error_msg");
                        if (friendRequestListener != null) {
                            friendRequestListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("user_id", friend.getUserId());
        sendFriendRequest.setParameters(params);
        sendFriendRequest.execute();
    }

    public void declineFriendRequest(FriendRequest friendRequest) {

        Request declineFriendRequest = new FriendsRequest(FriendsRequest.DECLINE_FRIEND_REQUEST, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String requestId = data.getString("request_id");
                        if (friendRequestListener != null) {
                            friendRequestListener.onFriendRequestDeclined(requestId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        FriendRequest.this.error = response.getJSONObject("error").getString("error_msg");
                        if (friendRequestListener != null) {
                            friendRequestListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("request_id", friendRequest.getRequestId());
        declineFriendRequest.setParameters(params);
        declineFriendRequest.execute();
    }


    public interface FriendRequestListener {

        public void onFriendRequestsFetched(ArrayList<FriendRequest> friendRequests);

        public void onFriendRequestHidden(String requestId);

        public void onFriendRequestAccepted(String requestId);

        public void onFriendRequestSent(String userId);

        public void onFriendRequestDeclined(String requestId);

        public void onError();

    }

}
