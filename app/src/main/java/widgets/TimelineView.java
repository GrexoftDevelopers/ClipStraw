package widgets;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.helper.CommonUtilities;
import com.clipstraw.gx.clipstraw.helper.session.SessionManager;
import com.clipstraw.gx.clipstraw.model.ClipstrawError;
import com.clipstraw.gx.clipstraw.model.Timeline;
import com.clipstraw.gx.clipstraw.model.feedback.newsfeed.newsfeeditem.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.model.session.ClipstrawSession;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by tahir on 1/19/2016.
 */
public class TimelineView extends LinearLayout implements Timeline.TimelineListener {

    Context mContext;

    private int year, month;

    private int yOffset, xOffset;

    private int level, totalLevels;

    private ArrayList<TimelinePage> timelinePages;

    private ProgressBar progressBar;

    private boolean pagesAdded;

    private Timeline timeline;

    TimelineViewListener timelineViewListener;

    public TimelineView(Context context) {
        super(context);
        Date date = Calendar.getInstance().getTime();
        this.year = date.getYear();
        this.month = date.getMonth();
//        System.out.println("year is : " + year);
//        System.out.println("month is : " + month);
        init(context);
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
        timeline.setListener(this);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimelineViewListener(TimelineViewListener timelineViewListener) {
        this.timelineViewListener = timelineViewListener;
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
       //6 System.out.println("inside on measure of timeline view");
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
                System.out.println("width of the page : " + timelinePage.getPipedView().getMeasuredWidth());
                int realWidth = timelinePage.getProgressBoxWidth();
                System.out.println("real width of the page : " + realWidth);
                //timelinePage.getPipedView().getLayoutParams().width = realWidth;
                //timelinePage.getPipedView().requestLayout();
                if (timelinePage.getDirection() == previousDirection){
                    if(previousDirection == TimelinePage.DIRECTION_LEFT){
                        previousMarginLeft = (previousMarginLeft - previousWidth) + timelinePage.getPipeWidth();
                    }
                    else if(previousDirection == TimelinePage.DIRECTION_RIGHT){
                        previousMarginLeft = previousMarginLeft + previousWidth - timelinePage.getPipeWidth();
                    }
                }
                //System.out.println("new width : " + timelinePage.getPipedView().getWidth());
                //System.out.println("setting margin left : " + previousMarginLeft);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(realWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = previousMarginLeft;
                timelinePage.getPipedView().setLayoutParams(layoutParams);
                ((LayoutParams)timelinePage.getPipedView().getLayoutParams()).leftMargin = previousMarginLeft;
                System.out.println("new width : " + timelinePage.getPipedView().getMeasuredWidth());
                timelinePage.getPipedView().requestLayout();
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
        fetchTimeline();
    }

    public void setMonth(int month){
        this.month = month;
        fetchTimeline();
    }

    public void show(){
        if(timeline != null){
            fetchTimeline();
        }
    }

    private void fetchTimeline(){
        timeline.fetchTimeline();
        timelineViewListener.onTimelineFetchStarted();
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

    @Override
    public void onTimelineFetched() {
        Toast.makeText(getContext(),"timeline fetched. Now constructing.",Toast.LENGTH_SHORT).show();
        new EventPageLoader().execute("");
    }

    @Override
    public void onError(ClipstrawError error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        timelineViewListener.onTimelineFetchFinished();
    }

    private int getLimit(){

        System.out.println("month is : " + month);

        if (isLoggedInUser()){
            switch (month){
                case 0:case 2:case 4:case 6:case 7:case 9:case 11:         return 31;

                case 3:case 5:case 8:case 10:                              return 30;

                case 1:                                                    return isLeapYear() ? 29 : 28;

                default:                                                   return isLeapYear() ? 366 : 365;
            }
        }
        else {
            return timeline.getEvents().size();
        }
    }

    private boolean isLoggedInUser(){
//        ClipstrawSession session = SessionManager.getInstance().getActiveSession();
//        if(timeline.getUser().equals(new UserSkeleton(session.getUserId(),session.getUserName(),"abc"))) return true;
        return true;
    }

    class EventPageLoader extends AsyncTask<String,View,String>{

        private void renderTimeline(){
            Calendar calendar = Calendar.getInstance();
            if (isLoggedInUser()){
                calendar.set(year,month == 0 ? 1 : month,1);
            }
            else{
                System.out.println("event list size : " + timeline.getEvents().size());
                calendar.set(timeline.getEvents().get(0).getDate().getYear(),timeline.getEvents().get(0).getDate().getMonth(),timeline.getEvents().get(0).getDate().getDay());
            }
            Date date;
            int eventIndex = 0;
            int limit = getLimit();
            System.out.println("limit is : " + limit);
            for(int i = 0 ; i < limit; i++){
                date = calendar.getTime();
                byte direction = getDirection();
                ClipstrawEvent event = null;

                System.out.println("date : " + date.toString());

                if (!isLoggedInUser() || isLoggedInUser() && CommonUtilities.matchDate(date, timeline.getEvents().get(eventIndex).getDate())){
                    System.out.println("dates equal");
                    event = timeline.getEvents().get(eventIndex);
                    eventIndex++;
                }
                else{
                    event = new ClipstrawEvent(date);
                }

                final TimelinePage timelinePage = new TimelinePage(getContext(),direction,event);

                timelinePage.getEventBox().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timelineViewListener.onTimelinePageSelected(timelinePage);
                    }
                });
                publishProgress(timelinePage.getPipedView());

                if (direction == TimelinePage.DIRECTION_RIGHT){
                    xOffset = xOffset + timelinePage.getPipedView().getMeasuredWidth();
                    level++;
                }
                else if (direction == TimelinePage.DIRECTION_LEFT){
                    xOffset = xOffset - timelinePage.getPipedView().getMeasuredWidth();
                    level--;
                }

                timelinePages.add(timelinePage);

                if( i < (limit - 1)){
                    if (isLoggedInUser()){
                        calendar.add(GregorianCalendar.DATE, 1);
                    }
                    else
                    {
                        calendar.set(timeline.getEvents().get(i+1).getDate().getYear(),timeline.getEvents().get(i+1).getDate().getMonth(),timeline.getEvents().get(i+1).getDate().getDay());
                    }
                }

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
            if(timelineViewListener != null){
                timelineViewListener.onTimelineFetchFinished();
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
                    addView(timelinePage.getPipedView());
                }

            }
        }
    }

    public interface TimelineViewListener {

        public void onTimelineFetchStarted();

        public void onTimelineFetchFinished();

        public void onTimelinePageSelected(TimelinePage timelinePage);
    }


}
