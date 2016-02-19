package com.clipstraw.gx.clipstraw.model.friend;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.request.*;
import com.clipstraw.gx.clipstraw.request.FriendsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rehan on 15-02-2016.
 */
public class FriendList extends ArrayList<Friend> {

    private FriendListListener friendListListener;

    private String error;

    public String getError() {
        return error;
    }

    public void setFriendListListener(FriendListListener friendListListener) {
        this.friendListListener = friendListListener;
    }

    public void fetchAllFriends(){
        Request fetchFriendRequest = new FriendsRequest(FriendsRequest.FETCH_ALL_FRIENDS, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")){
                    try {
                        JSONArray friends = response.getJSONArray("friends");
                        if (friends.length() > 0){
                            for(int i = 0; i < friends.length() ; i++){
                                JSONObject friend = friends.getJSONObject(i);
                                FriendList.this.add(Friend.createFromJSON(friend));
                            }
                        }
                        if(friendListListener != null){
                            friendListListener.onFriendsFetched();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        FriendList.this.error = response.getJSONObject("error").getString("error_msg");
                        if(friendListListener != null){
                            friendListListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        fetchFriendRequest.execute();
    }

    public void removeFriend(Friend friend){

        Request removeFriendRequest = new FriendsRequest(FriendsRequest.REMOVE_FRIEND, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")){
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String userId = data.getString("user_id");
                        if(friendListListener != null){
                            friendListListener.onUnFriend(userId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        FriendList.this.error = response.getJSONObject("error").getString("error_msg");
                        if(friendListListener != null){
                            friendListListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle params = new Bundle();
        params.putString("user_id",friend.getUserId());
        removeFriendRequest.setParameters(params);
        removeFriendRequest.execute();


    }

    public void unFollowFriend(Friend friend){

        Request unFollowFriendRequest = new FriendsRequest(FriendsRequest.UNFOLLOW, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")){
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String userId = data.getString("user_id");
                        if(friendListListener != null){
                            friendListListener.onUnFollow(userId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        FriendList.this.error = response.getJSONObject("error").getString("error_msg");
                        if(friendListListener != null){
                            friendListListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Bundle params = new Bundle();
        params.putString("user_id",friend.getUserId());
        unFollowFriendRequest.setParameters(params);
        unFollowFriendRequest.execute();

    }

    public void followFriend(Friend friend){

        Request followFriendRequest = new FriendsRequest(FriendsRequest.FOLLOW, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")){
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String userId = data.getString("user_id");
                        if(friendListListener != null){
                            friendListListener.onFollow(userId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        FriendList.this.error = response.getJSONObject("error").getString("error_msg");
                        if(friendListListener != null){
                            friendListListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Bundle params = new Bundle();
        params.putString("user_id",friend.getUserId());
        followFriendRequest.setParameters(params);
        followFriendRequest.execute();

    }

    public interface FriendListListener{

        public void onFriendsFetched();

        public void onUnFriend(String userId);

        public void onUnFollow(String userId);

        public void onFollow(String userId);

        public void onError();

    }

}
