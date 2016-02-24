package widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.pipedprogressbar.PipedProgress;

/**
 * Created by tahir on 2/8/2016.
 */
public class TimelinePageView extends PipedProgress{

    public static final byte DIRECTION_LEFT = 0;

    public static final byte DIRECTION_RIGHT = 1;

    private TimelinePage timelinePage;

    private EventBox eventBox;

    public TimelinePageView(Context context, TimelinePage timelinePage) {
        super(context);
        this.timelinePage = timelinePage;
        init();
    }

    private void init(){

        if (timelinePage.getDirection() == DIRECTION_LEFT){
            inflate(getContext(), R.layout.timeline_page_view_left, this);
        }
        else if (timelinePage.getDirection() == DIRECTION_RIGHT){
            inflate(getContext(), R.layout.timeline_page_view_right, this);
        }
        eventBox = (EventBox)findViewById(R.id.event_box);

        if (timelinePage.isPublished()){
            ((ViewGroup)findViewById(R.id.progress_item1)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)findViewById(R.id.progress_item2)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)findViewById(R.id.progress_item1_2)).getChildAt(1).setVisibility(View.GONE);
            ((ViewGroup)findViewById(R.id.progress_item3)).getChildAt(1).setVisibility(View.GONE);
            eventBox.setBoxColor(EventBox.EVENT_BOX_RED);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("inside on measure of timeline page view");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("inside on layout of timeline page view");
        super.onLayout(changed, l, t, r, b);
    }

    public int getProgressBoxWidth() {
        return findViewById(R.id.progress_item1).getMeasuredWidth() + findViewById(R.id.progress_item1_2).getMeasuredWidth() + findViewById(R.id.progress_item2).getMeasuredWidth();
    }

    public int getPipeWidth() {
        return findViewById(R.id.progress_item3).getMeasuredWidth();
    }

    public EventBox getEventBox() {
        return eventBox;
    }


}
