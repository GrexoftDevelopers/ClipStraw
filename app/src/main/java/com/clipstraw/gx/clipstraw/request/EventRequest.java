package com.clipstraw.gx.clipstraw.request;

import com.clipstraw.gx.clipstraw.model.ClipstrawMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tahir on 2/12/2016.
 */
public class EventRequest extends Request {

    private static final String API_NAME = "events";

    public static final String TIMELINE = "timeline";

    public static final String GET_BY_ID = "get_by_id";

    public static final String DELETE = "delete";

    public EventRequest(String apiEndPoint, RequestCallback callback) {
        super(API_NAME, apiEndPoint);
        this.callback = callback;
    }


    @Override
    protected void onRawResponse(String response) {
        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint) {
            case TIMELINE:
                try {
                    String userId = parameters.getString("user_id");
                    JSONArray data = new JSONArray();
                    for (int i = 0; i < 30; i++) {
                        JSONObject event = new JSONObject();
                        if (i % 2 == 0 || userId != null) {
                            event.put("date", i + " 01 2016");
                            event.put("id", i + "");
                            event.put("title", "JOHN'S PARTY");
                            data.put(event);
                        }
                    }
                    jsonResponse.put("data", data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case GET_BY_ID:
                try{
                    JSONObject event = new JSONObject();
                    event.put("id",parameters.getString("id"));
                    event.put("date", "01 01 2016");
                    event.put("title","John's party");
                    event.put("desc_short","This is short description.");
                    event.put("desc_long","This is the long description of the event.");
                    event.put("place", "Canaught place, New Delhi");

                    JSONArray mediaList = new JSONArray();
                    JSONObject media;
                    for(int i = 1; i <= 5 ; i++){
                        media = new JSONObject();
                        media.put("id","media " + i);
                        media.put("type", ( i % 2 == 0 ? ClipstrawMedia.MEDIA_IMAGE : ClipstrawMedia.MEDIA_VIDEO));
                        media.put("url","media url");
                        media.put("caption","This is the caption of the media");
                        mediaList.put(media);
                    }
                    event.put("media_list",mediaList);

                    JSONArray taggedUsers = new JSONArray();
                    JSONObject user;
                    for(int i = 1 ; i <= 5 ; i++){
                        user = new JSONObject();
                        user.put("id", i + "");
                        user.put("name","user " + i);
                        user.put("profile_image_url","profile image url");
                        taggedUsers.put(user);
                    }
                    event.put("tagged_users", taggedUsers);

                    jsonResponse.put("event",event);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case DELETE:
                try {
                    jsonResponse.put("id",parameters.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


        }
        callback.onCompleted(jsonResponse);
    }


}
