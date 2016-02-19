package com.clipstraw.gx.clipstraw.timeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.pipedprogressbar.PipedProgress;

import java.util.ArrayList;
import java.util.Date;

import widgets.EventBox;
import widgets.TimelinePageView;
import widgets.TimelineView;

/**
 * Created by tahir on 1/21/2016.
 */
public class TimelinePage {

    public static final byte DIRECTION_LEFT = 0;

    public static final byte DIRECTION_RIGHT = 1;

    Context context;

    // refers to the date the event is associated with
    private Date date;

    //the total number of likes the event has
    private int likesCount;

    //the total number of comments the event has
    private int commentsCount;

    //the total comments the event has
    private ArrayList<Comment> bufferedComments;

    //the total shares the event has
    private ArrayList<Share> shares;

    //defines whether the event is public or private
    private boolean isPrivate;

    //defines whether the event is published or waiting to be published
    private boolean isPublished;

    //shows the piped view of this page on timeline
    private TimelinePageView timelinePageView;

    //the direction in which the pipe will flow
    private byte direction;

    //the title of the page
    private String title;

    public TimelinePage(Context context,byte direction, Date date, String title, boolean isPublished, boolean showPipeView){
        this.context = context;
        this.direction = direction;
        this.date = date;
        this.title = title;
        this.isPublished = isPublished;
        if(showPipeView){
            createTimelinePageView();
        }
    }

    private void createTimelinePageView(){
        timelinePageView = new TimelinePageView(context, this);
    }

    public boolean isPublished() {
        return isPublished;
    }

    public byte getDirection() {
        return direction;
    }

    public TimelinePageView getTimelinePageView() {
        return timelinePageView;
    }
}
