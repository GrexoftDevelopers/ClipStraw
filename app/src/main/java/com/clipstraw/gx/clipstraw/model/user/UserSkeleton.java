package com.clipstraw.gx.clipstraw.model.user;

import com.clipstraw.gx.clipstraw.helper.ClipstrawMediaHelper;
import com.clipstraw.gx.clipstraw.model.ClipstrawMedia;

/**
 * Created by tahir on 2/13/2016.
 */
public class UserSkeleton {

    private String userId;

    private String userName;

    private ClipstrawMedia profileImage;

    public UserSkeleton(String userId, String userName, ClipstrawMedia profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public UserSkeleton(String userId, String userName, String profileImageUrl) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = ClipstrawMediaHelper.getMediaFromUrl(profileImageUrl);
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public ClipstrawMedia getProfileImage() {
        return profileImage;
    }



}
