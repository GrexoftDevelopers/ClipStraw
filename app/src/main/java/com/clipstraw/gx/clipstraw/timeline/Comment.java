package com.clipstraw.gx.clipstraw.timeline;

import com.clipstraw.gx.clipstraw.model.user.User;

import java.util.Date;

/**
 * Created by tahir on 1/19/2016.
 */
public class Comment {

    //clipstraw unique id of this comment
    private long commentId;

    //the id of the user who made this comment
    private User user;

    //date at which the comment is made
    private Date commentDate;

    //the content of the comment
    private String content;

    private int likeCount;

    public Comment(long commentId, User user, Date commentDate, String content, int likeCount) {
        this.commentId = commentId;
        this.user = user;
        this.commentDate = commentDate;
        this.content = content;
        this.likeCount = likeCount;
    }

    public long getCommentId() {
        return commentId;
    }

    public User getUser() {
        return user;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
