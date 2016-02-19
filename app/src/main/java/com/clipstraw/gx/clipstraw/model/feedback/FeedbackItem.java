package com.clipstraw.gx.clipstraw.model.feedback;

import com.clipstraw.gx.clipstraw.model.feedback.feedbackitem.Comment;
import com.clipstraw.gx.clipstraw.model.user.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 2/15/2016.
 */
public class FeedbackItem {

    //stores the unique id of this news feed item
    protected String id;

    // stores the user this news feed item is associated with
    protected User user;

    //stores the time at which this news feed it was created
    protected Date date;

    //stores the number of likes this news feed item has
    protected int likeCount;

    //stores the number of comments this news feed item has
    protected int commentCount;

    //stores the list of comments
    protected ArrayList<Comment> comments;

    //checks whether the item is liked by the logged in user
    protected boolean isLiked;

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
}
