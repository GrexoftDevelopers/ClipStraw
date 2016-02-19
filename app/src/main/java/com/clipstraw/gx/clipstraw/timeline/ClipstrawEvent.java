package com.clipstraw.gx.clipstraw.timeline;

import android.content.Context;
import android.view.View;

import com.clipstraw.gx.clipstraw.model.ClipstrawMedia;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 1/19/2016.
 */
public class ClipstrawEvent {

    private String eventId;

    //refers to the date the event is created
    private Date creationDate;

    //title of the event
    private String title;

    //short description of the event
    private String descriptionShort;

    //long description of the event
    private String descriptionLong;

    private ArrayList<ClipstrawMedia> mediaList;

    public ClipstrawEvent(String eventId, Date creationDate, String title, String descriptionShort, String descriptionLong, ArrayList<ClipstrawMedia> mediaList) {
        this.creationDate = creationDate;
        this.title = title;
        this.descriptionShort = descriptionShort;
        this.descriptionLong = descriptionLong;
        this.mediaList = mediaList;
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public String getDescriptionLong() {
        return descriptionLong;
    }

    public ArrayList<ClipstrawMedia> getMediaList() {
        return mediaList;
    }
}
