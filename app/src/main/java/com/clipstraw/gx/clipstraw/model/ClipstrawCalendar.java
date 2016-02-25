package com.clipstraw.gx.clipstraw.model;

import java.util.GregorianCalendar;

/**
 * Created by tahir on 2/24/2016.
 */
public class ClipstrawCalendar extends GregorianCalendar {

    public boolean matchDate(ClipstrawCalendar date){
        if(this.get(YEAR) != date.get(YEAR)) return false;
        if(this.get(MONTH) != date.get(MONTH)) return false;
        if(this.get(DAY_OF_MONTH) != date.get(DAY_OF_MONTH)) return false;
        return true;
    }

//    public ClipstrawCalendar(long millis){
//        super(false);
//        setTimeInMillis(millis);
//    }

   // public void timeAgo

}
