package com.clipstraw.gx.clipstraw.model.friend;

import com.clipstraw.gx.clipstraw.helper.ClipstrawMediaHelper;
import com.clipstraw.gx.clipstraw.model.ClipstrawMedia;
import com.clipstraw.gx.clipstraw.model.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rehan on 15-02-2016.
 */
public class Friend {

    private int mutualFriends;

    private String userId;

    private String name;

    private ClipstrawMedia profileImage;

    public Friend(int mutualFriends, String userId, String name, String profileImageUrl) {
        this.mutualFriends = mutualFriends;
        this.userId = userId;
        this.name = name;
        this.profileImage = ClipstrawMediaHelper.getMediaFromUrl(profileImageUrl);
    }

    public int getMutualFriends() {
        return mutualFriends;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public ClipstrawMedia getProfileImage() {
        return profileImage;
    }

    public static Friend createFromJSON(JSONObject friend){
        try {
            String userId = friend.getString("user_id");
            String name = friend.getString("name");
            String profileImageUrl = friend.getString("profile_image_url");
            int mutualFriends = friend.getInt("mutual_friends");
            return new Friend(mutualFriends,userId,name,profileImageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
