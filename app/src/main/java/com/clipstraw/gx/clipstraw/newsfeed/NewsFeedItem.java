package com.clipstraw.gx.clipstraw.newsfeed;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.timeline.Comment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 1/30/2016.
 */
public class NewsFeedItem {

    protected int itemId;

    protected User user;

    protected Date date;

    protected int commentCount;
    protected int likeCount;
    protected int shareCount;

    protected ArrayList<Comment> comments;

    protected ArrayList<User> taggedUsers;

    public NewsFeedItem(int itemId, User user, Date date, int likeCount, int shareCount, ArrayList<User> taggedUsers, ArrayList<Comment> comments) {
        this.itemId = itemId;
        this.user = user;
        this.date = date;
        this.commentCount = comments != null ? comments.size() : 0;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.taggedUsers = taggedUsers;
        this.comments = comments;
    }

    public int getItemId() {
        return itemId;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<User> getTaggedUsers() {
        return taggedUsers;
    }
}
