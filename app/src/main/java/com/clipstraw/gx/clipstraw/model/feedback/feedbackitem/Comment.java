package com.clipstraw.gx.clipstraw.model.feedback.feedbackitem;

import com.clipstraw.gx.clipstraw.model.feedback.FeedbackItem;
import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 2/15/2016.
 */
public class Comment extends FeedbackItem {

    //stores the actual content of comment
    private String content;

    public Comment(String content, String id, UserSkeleton user, Date date, int likeCount, int commentCount, ArrayList<Comment> comments, boolean isLiked) {
        //super(content, id, user, date, likeCount, commentCount, comments, isLiked);
    }


    public String getContent() {
        return content;
    }

    public static Comment createFromJSON(JSONObject commentJSON) {

        try {

            String id = commentJSON.getString("id");
            String content = commentJSON.getString("content");
            boolean isLiked = commentJSON.getBoolean("is_liked");
            int likeCount = commentJSON.getInt("like_count");

            JSONObject userJSON = commentJSON.getJSONObject("user");
            String userId = userJSON.getString("user_id");
            String userName = userJSON.getString("name");
            String profileImageUrl = userJSON.getString("profile_image_url");
            UserSkeleton user = new UserSkeleton(userId, userName, profileImageUrl);

            String dateString = commentJSON.getString("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("DD MM YYYY");
            Date date = dateFormat.parse(dateString);

            return new Comment(content, id, user, date, likeCount, 0, null, isLiked);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
