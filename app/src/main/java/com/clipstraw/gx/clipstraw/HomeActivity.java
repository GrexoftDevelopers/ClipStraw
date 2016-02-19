package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clipstraw.gx.clipstraw.model.user.User;
import com.clipstraw.gx.clipstraw.newsfeed.EventNewsFeedItem;
import com.clipstraw.gx.clipstraw.newsfeed.NewsFeedItem;
import com.clipstraw.gx.clipstraw.timeline.ClipstrawEvent;
import com.clipstraw.gx.clipstraw.timeline.Comment;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends FragmentActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView)findViewById(R.id.news_feed_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new NewsFeedAdapter(getNewsFeed()));


    }

    private ArrayList<NewsFeedItem> getNewsFeed(){

        ArrayList<NewsFeedItem> newsFeed = new ArrayList<NewsFeedItem>();
        for(int i = 0 ; i < 10 ; i++){
            ClipstrawEvent event = new ClipstrawEvent(null,Calendar.getInstance().getTime(),"Event " + (i+1),"Short description of event " + (i+1), "Long description of event " + (i+1),null);
            User user = new User(i+"","User " + i,null);
            Comment comment = new Comment(i,user,Calendar.getInstance().getTime(),"This is the content of comment", 2);
            ArrayList<Comment> comments = new ArrayList<Comment>();
            comments.add(comment);
            NewsFeedItem newsFeedItem = new EventNewsFeedItem(i,user,10,5,null,comments,event);
            newsFeed.add(newsFeedItem);
        }
        return newsFeed;
    }

    private class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedHolder>{

        private ArrayList<NewsFeedItem> newsFeed;

        public NewsFeedAdapter(ArrayList<NewsFeedItem> newsFeed) {
            this.newsFeed = newsFeed;
        }

        @Override
        public NewsFeedAdapter.NewsFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_feed_info, parent, false);
            return new NewsFeedHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsFeedAdapter.NewsFeedHolder holder, int position) {
            final EventNewsFeedItem eventNewsFeedItem = (EventNewsFeedItem)(newsFeed.get(position));
            System.out.println("news feed user name : " + eventNewsFeedItem.getUser().getUserName());
            System.out.println("news feed commenter name: " + eventNewsFeedItem.getComments().get(0).getUser().getUserName());
            final View newsFeedView = holder.newsFeedView;
            ((TextView)newsFeedView.findViewById(R.id.txt_news_feed_title)).setText(eventNewsFeedItem.getEvent().getTitle());
            ((TextView)newsFeedView.findViewById(R.id.txt_news_feed_desc_long)).setText(eventNewsFeedItem.getEvent().getDescriptionLong());
            ((TextView)newsFeedView.findViewById(R.id.txt_news_feed_desc_short)).setText(eventNewsFeedItem.getEvent().getDescriptionShort());
            ((TextView)newsFeedView.findViewById(R.id.txt_news_feed_date)).setText("2 days ago");//eventNewsFeedItem.getEvent().getCreationDate().toString());
            ((TextView)newsFeedView.findViewById(R.id.txt_news_feed_username_tagged)).setText(eventNewsFeedItem.getUser().getUserName());
            if (eventNewsFeedItem.getUser().getProfileImage() == null){
                ((ImageView)newsFeedView.findViewById(R.id.img_news_feed_profile)).setImageResource(R.mipmap.ic_user_avatar);
                int i;
            }
            newsFeedView.findViewById(R.id.btn_news_feed_comment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View additional = newsFeedView.findViewById(R.id.news_feed_additional);
                    if (additional.getVisibility() == View.GONE){
                        additional.setVisibility(View.VISIBLE);
                        if (eventNewsFeedItem.getCommentCount() > 0) {
                            View commentView;
                            ViewGroup commentContainer = (ViewGroup) newsFeedView.findViewById(R.id.news_feed_comment_container);
                            for(Comment comment : eventNewsFeedItem.getComments()){
                                commentView = LayoutInflater.from(commentContainer.getContext()).inflate(R.layout.item_comment,commentContainer);
                                ((TextView) commentView.findViewById(R.id.txt_commenter_name)).setText(comment.getUser().getUserName());
                                ((TextView) commentView.findViewById(R.id.txt_like_counter)).setText(comment.getLikeCount() + "");
                                ((TextView) commentView.findViewById(R.id.txt_comment_content)).setText(comment.getContent());
                                ((TextView) commentView.findViewById(R.id.txt_comment_date)).setText("June 12"); //comment.getCommentDate().toString());
                                ((ImageView) commentView.findViewById(R.id.img_commenter_profile)).setImageResource(R.mipmap.ic_user_avatar);
                            }

                        }
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class NewsFeedHolder extends RecyclerView.ViewHolder{
            View newsFeedView;

            public NewsFeedHolder(View itemView) {
                super(itemView);
                newsFeedView = itemView;
            }


        }
    }
}
