package com.clipstraw.gx.clipstraw.timeline;

import java.util.Date;

/**
 * Created by tahir on 1/19/2016.
 */
public class Share {

    //defines the event shared for all of the contact list
    public static final byte SHARE_TYPE_PUBLIC = 0;

    //defines the event shared by the creator with some friend
    public static final byte SHARE_TYPE_SPECIFIC = 1;

    //id of the share
    private long shareId;

    //id of the user who shared
    private int userId;

    // defines the type of of share
    private byte shareType;

    //defines the date the event was shared
    private Date shareDate;


}
