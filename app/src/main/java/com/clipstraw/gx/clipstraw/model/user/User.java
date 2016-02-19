package com.clipstraw.gx.clipstraw.model.user;

/**
 * Created by tahir on 1/30/2016.
 */
public class User extends UserSkeleton {

    private String dateOfBirth;

    private String gender;

    private String status;

    private String email;

    private String phone;

    public User(String userId, String userName, String email, String profileImageUrl, String status, String gender, String dateOfBirth, String phone) {
        super(userId, userName, profileImageUrl);
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.status = status;
        this.gender = gender;
    }

    public User(String userId, String userName, String profileImageUrl){
        super(userId, userName, profileImageUrl);

    }


}
