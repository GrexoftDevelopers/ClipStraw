package widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.model.feedback.newsfeed.newsfeeditem.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.pipedprogressbar.PipedProgress;

import java.util.ArrayList;
import java.util.Date;

import widgets.EventBox;
import widgets.TimelinePageView;
import widgets.TimelineView;

/**
 * Created by tahir on 1/21/2016.
 */
public class TimelinePage{

    public static final byte DIRECTION_LEFT = 0;

    public static final byte DIRECTION_RIGHT = 1;

    //the direction in which the pipe will flow
    private byte direction;

    private ClipstrawEvent event;

    private EventBox eventBox;

    private PipedProgress pipedView;

    public TimelinePage(Context context,byte direction, ClipstrawEvent event ){
        this.direction = direction;
        this.event = event;
        init(context);
    }


    private void init(Context context){

        if (direction == DIRECTION_LEFT){
            pipedView = (PipedProgress)((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.timeline_page_view_left,null);
        }
        else if (direction == DIRECTION_RIGHT){
            pipedView = (PipedProgress)((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.timeline_page_view_right, null);
        }
        eventBox = (EventBox)pipedView.findViewById(R.id.event_box);

        if (event.getTitle() != null){
            ((ViewGroup)pipedView.findViewById(R.id.progress_item1)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)pipedView.findViewById(R.id.progress_item2)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)pipedView.findViewById(R.id.progress_item1_2)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)pipedView.findViewById(R.id.progress_item3)).getChildAt(1).setVisibility(View.GONE);
            eventBox.setBoxColor(EventBox.EVENT_BOX_RED);
            System.out.println("date of this timeline page : " + event.getDate().toString());
            eventBox.setDate(event.getDate().getDay() + "");
        }

    }

    public boolean isPublished() {
        return event == null ? false : true;
    }

    public byte getDirection() {
        return direction;
    }

    public int getProgressBoxWidth() {
        return pipedView.findViewById(R.id.progress_item1).getMeasuredWidth() + pipedView.findViewById(R.id.progress_item1_2).getMeasuredWidth() + pipedView.findViewById(R.id.progress_item2).getMeasuredWidth();
    }

    public int getPipeWidth() {
        return pipedView.findViewById(R.id.progress_item3).getMeasuredWidth();
    }

    public EventBox getEventBox() {
        return eventBox;
    }

    public PipedProgress getPipedView() {
        return pipedView;
    }
}
