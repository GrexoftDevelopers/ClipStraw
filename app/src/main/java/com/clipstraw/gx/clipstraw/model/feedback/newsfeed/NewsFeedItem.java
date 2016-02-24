package com.clipstraw.gx.clipstraw.model.feedback.newsfeed;

import com.clipstraw.gx.clipstraw.model.feedback.FeedbackItem;
import com.clipstraw.gx.clipstraw.model.feedback.feedbackitem.Comment;
import com.clipstraw.gx.clipstraw.model.user.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 2/15/2016.
 */
public class NewsFeedItem extends FeedbackItem {

    //stores the number of public shares this news feed item got
    private int shareCount;

//    public NewsFeedItem(String content, String id, User user, Date date, int likeCount, int commentCount, ArrayList<Comment> comments, boolean isLiked) {
//        super(content, id, user, date, likeCount, commentCount, comments, isLiked);
//    }
}
