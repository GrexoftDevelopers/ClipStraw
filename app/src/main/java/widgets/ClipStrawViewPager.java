package widgets;

/**
 * Created by FaizZy on 06-01-2016.
 */
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ClipStrawViewPager extends ViewPager {

    private boolean swipeEnabled;

    public ClipStrawViewPager(Context context) {
        super(context);
        swipeEnabled = true;
    }

    public ClipStrawViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeEnabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if (swipeEnabled) return super.onInterceptTouchEvent(event);
        else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if (swipeEnabled) return super.onTouchEvent(event);
        else return false;
    }

    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }
}
