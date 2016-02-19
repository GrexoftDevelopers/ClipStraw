package com.clipstraw.gx.clipstraw.newsfeed;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.timeline.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.timeline.Comment;

import java.util.ArrayList;

/**
 * Created by tahir on 1/30/2016.
 */
public class EventNewsFeedItem extends NewsFeedItem {

    private ClipstrawEvent event;

    public EventNewsFeedItem(int itemId, User user, int likeCount, int shareCount, ArrayList<User> taggedUsers, ArrayList<Comment> comments, ClipstrawEvent event) {
        super(itemId, user, event.getCreationDate(), likeCount, shareCount, taggedUsers, comments);
        this.event = event;
    }

    public ClipstrawEvent getEvent() {
        return event;
    }
}
