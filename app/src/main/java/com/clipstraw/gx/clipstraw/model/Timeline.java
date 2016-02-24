package com.clipstraw.gx.clipstraw.model;

import android.os.Bundle;

import com.clipstraw.gx.clipstraw.model.feedback.newsfeed.newsfeeditem.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.EventRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import widgets.TimelineView;

/**
 * Created by tahir on 2/22/2016.
 */
public class Timeline {

    private ArrayList<ClipstrawEvent> events;

    private int year, month;

    private UserSkeleton user;

    private TimelineListener  listener;

    public Timeline(UserSkeleton user) {
        this.user = user;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        System.out.println("year is : " + year);
        System.out.println("month is : " + month);
    }

    public void setListener(TimelineListener listener) {
        this.listener = listener;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public UserSkeleton getUser() {
        return user;
    }

    public ArrayList<ClipstrawEvent> getEvents() {
        return events;
    }

    public void setYear(int year) {
        this.year = year;
        fetchTimeline();
    }

    public void setMonth(int month) {
        this.month = month;
        fetchTimeline();
    }

    public void fetchTimeline() {

        Request timelineRequest = new EventRequest(EventRequest.TIMELINE, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {
                try {
                    if (!response.has("error")) {
                        System.out.println("inside on completed");
                        JSONArray data = response.getJSONArray("data");
                        events = new ArrayList<ClipstrawEvent>();
                        if (data.length() > 0) {
                            JSONObject eventJson;
                            for (int i = 0; i < data.length(); i++) {
                                eventJson = data.getJSONObject(i);
                                String id = eventJson.getString("id");
                                String date = eventJson.getString("date");
                                String title = eventJson.getString("title");
                                events.add(new ClipstrawEvent(id, title, date));
                            }
                        }
                        if (listener != null) {
                            listener.onTimelineFetched();
                        }
                    } else {
                        if (listener != null) {
                            listener.onError(ClipstrawError.createError(response.getJSONObject("error")));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putInt("year",year);
        parameters.putInt("month",month);
        parameters.putString("user_id", user.getUserId());
        timelineRequest.setParameters(parameters);
        timelineRequest.execute();

    }

    public interface TimelineListener{
        public void onTimelineFetched();
        public void onError(ClipstrawError error);
    }
}
