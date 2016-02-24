package com.clipstraw.gx.clipstraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.Chat.ChatActivity;
import com.clipstraw.gx.clipstraw.Chat.MessageActivity;
import com.clipstraw.gx.clipstraw.Chat.SearchActivity;
import com.clipstraw.gx.clipstraw.helper.session.SessionManager;
import com.clipstraw.gx.clipstraw.model.Timeline;
import com.clipstraw.gx.clipstraw.model.session.ClipstrawSession;
import com.clipstraw.gx.clipstraw.model.user.UserSkeleton;
import widgets.TimelinePage;

import widgets.OnSwipeTouchListener;
import widgets.TimelineView;

/**
 * Created by Faizzy on 12-01-2016.
 */
public class TimelineFragment extends Fragment {


    private LinearLayout layoutFooterContainer;

    private View footer;
    private float startY, endY;
    private View layoutParent;
    private View fragmentView;

    private TimelineView timelineView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.profile_page,container,false);
        layoutFooterContainer = (LinearLayout)fragmentView.findViewById(R.id.layout_footer_container);
        footer = inflater.inflate(R.layout.footer, null);
        layoutFooterContainer.addView(footer);
        layoutFooterContainer.setVisibility(View.GONE);
        layoutParent = (View)fragmentView.findViewById(R.id.home_page);
        View layoutHeader = (View)fragmentView.findViewById(R.id.layout_parent);

        ClipstrawSession session = SessionManager.getInstance().getActiveSession();
        Timeline timeline = new Timeline(new UserSkeleton(session.getUserId(),session.getUserName(),"abc"));

        timelineView = new TimelineView(getActivity());
        timelineView.setTimeline(timeline);
        RelativeLayout timelineScroller = (RelativeLayout)fragmentView.findViewById(R.id.timeline_container);

        final ProgressBar progressBar = (ProgressBar)timelineScroller.findViewById(R.id.timeline_progress);

        timelineView.setTimelineViewListener(new TimelineView.TimelineViewListener() {
            @Override
            public void onTimelineFetchStarted() {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
                Toast.makeText(getContext(),"timeline fetched started.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTimelineFetchFinished() {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onTimelinePageSelected(TimelinePage timelinePage) {
                Intent intent = new Intent(getActivity(), EventsAcivity.class);
                intent.putExtra("is_published", timelinePage.isPublished());
                getActivity().startActivity(intent);
            }
        });
        timelineScroller.addView(timelineView);
        timelineView.show();


        final View swipLeftPop= (View)fragmentView.findViewById(R.id.right_header_slide_popup);


        layoutHeader.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeRight() {
                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0,100,0,0);
                mTranslateAnimation.setFillAfter(false);
                mTranslateAnimation.setDuration(300);
                swipLeftPop.startAnimation(mTranslateAnimation);
                swipLeftPop.setVisibility(View.GONE);
            }
            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
                TranslateAnimation mTranslateAnimation = new TranslateAnimation(100, 0, 0, 0);
                mTranslateAnimation.setFillAfter(true);
                try {
                    mTranslateAnimation.setDuration(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                swipLeftPop.setVisibility(View.INVISIBLE);
                swipLeftPop.startAnimation(mTranslateAnimation);
            }
            public void onSwipeBottom() {
                Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();

            }

        });
        layoutParent.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, 100, 0);
                mTranslateAnimation.setFillAfter(true);
                mTranslateAnimation.setDuration(300);

                layoutFooterContainer.setVisibility(View.INVISIBLE);
                layoutFooterContainer.startAnimation(mTranslateAnimation);
            }

            public void onSwipeRight() {
                Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, 0, 200);
                mTranslateAnimation.setFillAfter(true);
                mTranslateAnimation.setDuration(300);
                layoutFooterContainer.startAnimation(mTranslateAnimation);
                layoutFooterContainer.setVisibility(View.GONE);
            }

        });

        ImageButton chatAcitivity = (ImageButton)fragmentView.findViewById(R.id.btn_chat);
        chatAcitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(),ChatActivity.class);
                startActivity(mIntent);
            }
        });

        fragmentView.findViewById(R.id.icon_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        fragmentView.findViewById(R.id.icon_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });


        return  fragmentView;
    }

    //    @Override
//    protected void onCreateView(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_page);
//        layoutFooterContainer = (LinearLayout) findViewById(R.id.layout_footer_container);
//        footer = getLayoutInflater().inflate(R.layout.footer, null);
//        layoutFooterContainer.addView(footer);
//        layoutFooterContainer.setVisibility(View.GONE);
//        layoutParent = (View) findViewById(R.id.home_page);
//        View layoutHeader = (View)findViewById(R.id.layout_parent);
//        final View swipLeftPop= (View)findViewById(R.id.right_header_slide_popup);
//
//
//        layoutHeader.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
//            public void onSwipeTop() {
//                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
//
//            }
//
//            public void onSwipeRight() {
//                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
//                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0,100,0,0);
//                mTranslateAnimation.setFillAfter(false);
//                mTranslateAnimation.setDuration(300);
//                swipLeftPop.startAnimation(mTranslateAnimation);
//                swipLeftPop.setVisibility(View.GONE);
//
//
//            }
//            public void onSwipeLeft() {
//                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
//                TranslateAnimation mTranslateAnimation = new TranslateAnimation(100, 0, 0, 0);
//                mTranslateAnimation.setFillAfter(true);
//                try {
//                    mTranslateAnimation.setDuration(300);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                swipLeftPop.setVisibility(View.INVISIBLE);
//                swipLeftPop.startAnimation(mTranslateAnimation);
//            }
//            public void onSwipeBottom() {
//                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
//
//        layoutParent.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
//            public void onSwipeTop() {
//                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
//                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, 100, 40);
//                    mTranslateAnimation.setFillAfter(true);
//                    mTranslateAnimation.setDuration(300);
//
//                    layoutFooterContainer.setVisibility(View.INVISIBLE);
//                    layoutFooterContainer.startAnimation(mTranslateAnimation);
//            }
//
//            public void onSwipeRight() {
//                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeLeft() {
//                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
//            }
//            public void onSwipeBottom() {
//                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
//                TranslateAnimation mTranslateAnimation = new TranslateAnimation(0,0,0,100);
//                    mTranslateAnimation.setFillAfter(true);
//                    mTranslateAnimation.setDuration(300);
//                    layoutFooterContainer.startAnimation(mTranslateAnimation);
//                    layoutFooterContainer.setVisibility(View.GONE);
//            }
//
//        });
//
//
//
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.fragment_in_reverse, R.anim.fragment_out_reverse);
//    }
//
////
////    @Override
////    public boolean onTouchEvent(MotionEvent event) {
////        switch (event.getAction()){
////            case MotionEvent.ACTION_DOWN:
////           startY =event.getY();
////                break;
////            case  MotionEvent.ACTION_UP:
////                 endY = event.getY();
////                if (endY < startY) {
////                    System.out.println("Move UP");
////                    TranslateAnimation mTranslateAnimation = new TranslateAnimation(0,0,100,40);
////                    mTranslateAnimation.setFillAfter(true);
////                    mTranslateAnimation.setDuration(300);
////                    layoutFooterContainer.setVisibility(View.INVISIBLE);
////                    layoutFooterContainer.startAnimation(mTranslateAnimation);
////                }else{
////                    System.out.println("Move Down");
////                    TranslateAnimation mTranslateAnimation = new TranslateAnimation(0,0,0,100);
////                    mTranslateAnimation.setFillAfter(false);
////                    mTranslateAnimation.setDuration(300);
////                    layoutFooterContainer.startAnimation(mTranslateAnimation);
////                    layoutFooterContainer.setVisibility(View.GONE);
////                }
////
////                break;
////
////
////        }
////        return true ;
////    }
//

}

