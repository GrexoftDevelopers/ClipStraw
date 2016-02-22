package com.clipstraw.gx.clipstraw.model.feedback.newsfeed.newsfeeditem;


import com.clipstraw.gx.clipstraw.model.ClipstrawError;
import com.clipstraw.gx.clipstraw.model.ClipstrawMedia;
import com.clipstraw.gx.clipstraw.model.SharedMessage;
import com.clipstraw.gx.clipstraw.model.feedback.newsfeed.NewsFeedItem;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.EventRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;

/**
 * Created by tahir on 2/15/2016.
 */
public class ClipstrawEvent extends NewsFeedItem {

    //title of the event
    private String title;

    //short description of the event
    private String descriptionShort;

    //long description of the event
    private String descriptionLong;

    //stores the list of photos/videos
    private ArrayList<ClipstrawMedia> mediaList;

    //stores the checked in place for the event
    private String place;

    private ArrayList<SharedMessage> sharedMessages;

    private static ClipstrawEventListener listener;

    public ClipstrawEvent(String id, String title, String date) {
        this.id = id;
        this.title = title;
        try {
            this.date = new SimpleDateFormat("DD MM YYYY").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public String getPlace() {
        return place;
    }

    public ArrayList<SharedMessage> getSharedMessages() {
        return sharedMessages;
    }



    public static void getEvent(String id) {

        Request eventRequest = new EventRequest(EventRequest.GET_BY_ID, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if (!response.has("error")) {
                    try {
                        JSONObject eventJson = response.getJSONObject("event");
                        String id = eventJson.getString("id");
                        String date = eventJson.getString("date");
                        String title = eventJson.getString("title");
                        String desc_short = eventJson.getString("desc_short");
                        String desc_long = eventJson.getString("desc_long");
                        String place = eventJson.getString("place");

                        JSONArray taggedUsersJson = eventJson.getJSONArray("tagged_users");
                        if (taggedUsersJson.length() > 0) {
                            ArrayList<UserSkeleton> taggedUsers = new ArrayList<UserSkeleton>();
                            JSONObject userJson;
                            for (int j = 0; j < taggedUsersJson.length(); j++) {
                                userJson = taggedUsersJson.getJSONObject(j);
                                String userId = userJson.getString("user_id");
                                String name = userJson.getString("name");
                                String profileImageUrl = userJson.getString("profile_image_url");
                                taggedUsers.add(new UserSkeleton(userId,name,profileImageUrl));
                            }
                        }


                    } catch (JSONException ex) {

                    }

                }

            }
        });

    }

    public interface ClipstrawEventListener {

        public void onTimelineFetched(ArrayList<ClipstrawEvent> timeline);

        public void onEventFetched(ClipstrawEvent event);

        public void onError(ClipstrawError error);

    }

}
