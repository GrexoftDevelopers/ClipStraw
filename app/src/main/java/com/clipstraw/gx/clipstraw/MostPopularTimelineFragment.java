package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import widgets.TimelinePage;

import widgets.TimelineView;

/**
 * Created by tahir on 2/1/2016.
 */
public class MostPopularTimelineFragment extends Fragment {


    private View fragmentView;
    private TimelineView timelineView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.layout_most_popular_timeline,container,false);
        timelineView = new TimelineView(getActivity());
        RelativeLayout timelineScroller = (RelativeLayout)fragmentView.findViewById(R.id.timeline_container);

        final ProgressBar progressBar = (ProgressBar)timelineScroller.findViewById(R.id.timeline_progress);

        timelineView.setTimelineViewListener(new TimelineView.TimelineViewListener() {
            @Override
            public void onTimelineFetchStarted() {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
            }

            @Override
            public void onTimelineFetchFinished() {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onTimelinePageSelected(TimelinePage timelinePage) {

            }
        });
        timelineView.setYear(2015);
        timelineScroller.addView(timelineView);
        return fragmentView;
    }
}
