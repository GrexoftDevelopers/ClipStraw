package widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.clipstraw.gx.clipstraw.model.Timeline;
import com.clipstraw.gx.clipstraw.timeline.TimelinePage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by tahir on 1/19/2016.
 */
public class TimelineView extends LinearLayout{

    Context mContext;

    private int year, month;

    private boolean isLoggedInUser;

    private int yOffset, xOffset;

    private int level, totalLevels;

    private ArrayList<TimelinePage> timelinePages;

    private ProgressBar progressBar;

    private boolean pagesAdded;

    private Timeline timeline;

    TimelineListener timelineListener;

    public TimelineView(Context context, boolean isLoggedInUser) {
        super(context);
        this.isLoggedInUser = isLoggedInUser;
        Date date = Calendar.getInstance().getTime();
        this.year = date.getYear();
        this.month = date.getMonth();
        init(context);
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimelineListener(TimelineListener timelineListener) {
        this.timelineListener = timelineListener;
    }

    private void init(Context context){
        mContext = context;
        yOffset = 0;
        xOffset = 0;
        level = 1;
        setTotalLevelsAccordingToScreenSize();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(50, 0, 0, 0);
        setLayoutParams(layoutParams);
        setOrientation(LinearLayout.VERTICAL);
        timelinePages = new ArrayList<TimelinePage>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("inside on measure of timeline view");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //System.out.println("inside on layout of timelineview. changed : " + changed);
        if(pagesAdded && timelinePages != null && !timelinePages.isEmpty()){
            byte previousDirection = -1;
            int previousWidth = 0;
            int previousMarginLeft = 0;
            for(TimelinePage timelinePage : timelinePages){
                System.out.println("width of the page : " + timelinePage.getTimelinePageView().getMeasuredWidth());
                int realWidth = timelinePage.getTimelinePageView().getProgressBoxWidth();
                System.out.println("real width of the page : " + realWidth);
                //timelinePage.getPipedView().getLayoutParams().width = realWidth;
                //timelinePage.getPipedView().requestLayout();
                if (timelinePage.getDirection() == previousDirection){
                    if(previousDirection == TimelinePage.DIRECTION_LEFT){
                        previousMarginLeft = (previousMarginLeft - previousWidth) + timelinePage.getTimelinePageView().getPipeWidth();
                    }
                    else if(previousDirection == TimelinePage.DIRECTION_RIGHT){
                        previousMarginLeft = previousMarginLeft + previousWidth - timelinePage.getTimelinePageView().getPipeWidth();
                    }
                }
                //System.out.println("new width : " + timelinePage.getPipedView().getWidth());
                //System.out.println("setting margin left : " + previousMarginLeft);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(realWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = previousMarginLeft;
                timelinePage.getTimelinePageView().setLayoutParams(layoutParams);
                //timelinePage.getTimelinePageView().getLayoutParams().width = realWidth;
                //((LayoutParams)timelinePage.getTimelinePageView().getLayoutParams()).leftMargin = previousMarginLeft;
                //System.out.println("new left margin : " + ((LayoutParams) timelinePage.getPipedView().getLayoutParams()).leftMargin);
                previousDirection = timelinePage.getDirection();
                previousWidth = realWidth;
            }
            pagesAdded = false;
        }


    }

    public void setYear(int year) {
        this.year = year;
        new EventPageLoader().execute("");
    }

    public void setMonth(int month){
        this.month = month;
        new EventPageLoader().execute("");
    }

    public void show(){
        new EventPageLoader().execute("");
    }

    private void setTotalLevelsAccordingToScreenSize(){
        if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            totalLevels = 5;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            totalLevels = 3;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            totalLevels = 3;
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            totalLevels = 6;
        }
    }



    private boolean isLeapYear(){
        if (year % 4 == 0){
            if (year % 100 == 0){
                if (year % 400 == 0){
                    return true;
                }
                else{
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    private byte getDirection(){
        byte direction = 0;
        if(level == 1){
            direction = TimelinePage.DIRECTION_RIGHT;
        }
        else if (level == totalLevels){
            direction = TimelinePage.DIRECTION_LEFT;
        }
        else if (level > 1 && level < totalLevels){
            int probability = new Random().nextInt(100);
            //System.out.println("probability is  : " + probability);
            direction = probability <= 50 ? TimelinePage.DIRECTION_LEFT : TimelinePage.DIRECTION_RIGHT;
        }
        return direction;
    }

    class EventPageLoader extends AsyncTask<String,View,String>{

        private void renderTimeline(){
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month == 0 ? 1 : month,1);
            Date date;
            int limit = isLeapYear() ? 366 : 365;
            for(int i = 1 ; i <= 30; i++){
                date = calendar.getTime();
                byte direction = getDirection();
                final TimelinePage timelinePage = new TimelinePage(mContext, direction,date,"Event #" + i,isLoggedInUser&&i%2!=0?false:true,true);

                TimelinePageView pipedView = timelinePage.getTimelinePageView();
                //System.out.println("pipedView width : " + pipedView.getProgressBoxWidth());
                pipedView.getEventBox().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timelineListener.onTimelinePageSelected(timelinePage);
                    }
                });
                publishProgress(pipedView);
                //System.out.println("getLeft : " + pipedView.getLeft());
                if (direction == TimelinePage.DIRECTION_RIGHT){
                    xOffset = xOffset + pipedView.getMeasuredWidth();
                    level++;
                }
                else if (direction == TimelinePage.DIRECTION_LEFT){
                    xOffset = xOffset - pipedView.getMeasuredWidth();
                    level--;
                }
                calendar.add(GregorianCalendar.DATE,1);
                timelinePages.add(timelinePage);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pagesAdded = false;
            if(((ViewGroup)TimelineView.this).getChildCount() > 0) {
                TimelineView.this.removeAllViews();
                timelinePages.clear();
            }
            if(timelineListener != null){
                timelineListener.onTimelineFetchStarted();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            renderTimeline();
            return null;
        }

        @Override
        protected void onProgressUpdate(View... values) {
            super.onProgressUpdate(values);
            //System.out.println("adding page");
            //values[0].setVisibility(GONE);
            //addView(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(timelineListener != null){
                timelineListener.onTimelineFetchFinished();
            }
            //removeView(progressBar);
            addTimelinePages();
            //System.out.println("requesting layout");

            pagesAdded = true;
            //requestLayout();
        }

        private void addTimelinePages(){
            if(timelinePages != null && !timelinePages.isEmpty()){
                for(TimelinePage timelinePage : timelinePages){

                    addView(timelinePage.getTimelinePageView());
                }

            }
        }
    }

    public interface TimelineListener{

        public void onTimelineFetchStarted();

        public void onTimelineFetchFinished();

        public void onTimelinePageSelected(TimelinePage timelinePage);
    }


}
