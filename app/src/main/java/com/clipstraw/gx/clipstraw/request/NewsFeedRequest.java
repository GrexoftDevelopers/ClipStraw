package com.clipstraw.gx.clipstraw.request;

import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rehan on 23-02-2016.
 */
public class NewsFeedRequest extends Request {

    private static final String API_NAME = "news";

    public static final String GET_COMMENTS = "get_comments";

    public static final String ADD_COMMENT = "add_comment";

    public NewsFeedRequest(String apiEndPoint, RequestCallback callback) {
        super(API_NAME, apiEndPoint);
        this.callback = callback;
    }

    @Override
    protected void onRawResponse(String response) {

        JSONObject jsonResponse = new JSONObject();
        switch (apiEndPoint) {

            case GET_COMMENTS:
                try {

                    JSONArray data = new JSONArray();

                    for (int i = 0; i < 25; i++) {

                        JSONObject comment = new JSONObject();
                        comment.put("id", "cmnt123");
                        comment.put("content", "content");
                        comment.put("is_liked", false);
                        comment.put("like_count", 20);

                        JSONObject user = new JSONObject();
                        user.put("name", "User Name");
                        user.put("user_id", "user123");
                        user.put("profile_image_url", "abc");
                        comment.put("user", user);

                        JSONObject date = new JSONObject();
                        date.put("date", "23 FEB 2016");
                        comment.put("date", date);

                        data.put(comment);
                    }

                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            case ADD_COMMENT:

                try {
                    JSONObject data = new JSONObject();
                    data.put("id", parameters.getString("id"));
                    jsonResponse.put("data", data);
                    callback.onCompleted(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }
}