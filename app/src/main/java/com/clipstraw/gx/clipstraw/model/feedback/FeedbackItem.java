package com.clipstraw.gx.clipstraw.model.feedback;

import android.os.Bundle;


import com.clipstraw.gx.clipstraw.model.feedback.feedbackitem.Comment;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import com.clipstraw.gx.clipstraw.request.NewsFeedRequest;
import com.clipstraw.gx.clipstraw.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tahir on 2/15/2016.
 */
public class FeedbackItem {

    private FeedbackListener feedbackListener;

    //stores the content of message
    private String content;

    //stores the unique id of this news feed item
    protected String id;

    // stores the user this news feed item is associated with
    protected UserSkeleton user;

    //stores the time at which this news feed it was created
    protected Date date;

    //stores the number of likes this news feed item has
    protected int likeCount;

    //stores the number of comments this news feed item has
    protected int commentCount;

    //stores the list of comments
    protected ArrayList<Comment> comments;

    //checks whether the item is liked by the logged in user
    protected boolean isLiked;

    public void setFeedbackListener(FeedbackListener feedbackListener){
        this.feedbackListener = feedbackListener;
    }

//    public FeedbackItem(String content, String id, UserSkeleton user, Date date, int likeCount, int commentCount, ArrayList<Comment> comments, boolean isLiked) {
//        this.content = content;
//        this.id = id;
//        this.user = user;
//        this.date = date;
//        this.likeCount = likeCount;
//        this.commentCount = commentCount;
//        this.comments = comments;
//        this.isLiked = isLiked;
//    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserSkeleton getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public boolean isLiked() {
        return isLiked;
    }
    public void fetchComments(int offset, int count){
        Request getCommentRequest = new NewsFeedRequest(NewsFeedRequest.GET_COMMENTS, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if(!response.has("error")){

                    try {
                        ArrayList<Comment> comments = null;
                        JSONArray CommentCountJSON = response.getJSONArray("data");
                        if(CommentCountJSON.length() > 0){
                            comments = new ArrayList<Comment>();
                            for(int i = 0; i < CommentCountJSON.length(); i++){
                                JSONObject countComment = CommentCountJSON.getJSONObject(i);
                                comments.add(Comment.createFromJSON(countComment));
                            }
                        }
                        if(feedbackListener != null){
                            feedbackListener.onCommentsFetched(comments);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else {
                    try {
                        response.getJSONObject("error").getString("error_msg");
                        if(feedbackListener != null){
                            feedbackListener.onError();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("id", id);
        parameters.putInt("offset", offset);
        parameters.putInt("count", count);
        getCommentRequest.setParameters(parameters);
        getCommentRequest.execute();
    }

    public void addComment(final Comment comment){

        Request addCommentRequest = new NewsFeedRequest(NewsFeedRequest.ADD_COMMENT, new Request.RequestCallback() {
            @Override
            public void onCompleted(JSONObject response) {

                if(!response.has("error")){
                    try {
                        JSONObject data = response.getJSONObject("data");
                        comment.setId(data.getString("id"));
                        if(feedbackListener != null){
                            feedbackListener.onCommentAdd(comment);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        response.getJSONObject("error").getString("error_msg");
                        if(feedbackListener != null){
                            feedbackListener.onError();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Bundle parameter = new Bundle();
        parameter.putString("id", id);
        parameter.putString("content", content);
        parameter.putString("user_id", user.getUserId());
        parameter.putString("date", date.toString());
        addCommentRequest.setParameters(parameter);
        addCommentRequest.execute();

    }
    public interface FeedbackListener{

        public void onCommentsFetched(ArrayList<Comment> comments);

        public void onCommentAdd(final Comment comment);

        public void onError();

    }
}
