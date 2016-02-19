package com.clipstraw.gx.clipstraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class WalkthroughActivity extends FragmentActivity implements View.OnClickListener {

    private ArrayList<Fragment> walkthroughs;
    private ViewPager walkthroughViewPager;
    private CirclePageIndicator walkthroughIndicator;
   // private walkthroughAdapter mWalkthroughAdapter;
    private Button layoutSkipWalkthrough, layoutLetsGo;
    private DisplayMetrics metrics;
    private  boolean thirdPageSeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        walkthroughViewPager = (ViewPager) findViewById(R.id.walkthrough_view_pager);
        walkthroughIndicator = (CirclePageIndicator) findViewById(R.id.walkthrough_indicator);
        layoutSkipWalkthrough = (Button) findViewById(R.id.layout_skip_walkthrough);
        layoutLetsGo = (Button) findViewById(R.id.layout_lets_go);

        layoutSkipWalkthrough.setOnClickListener(this);
        layoutLetsGo.setOnClickListener(this);

//        initializeWalkThroughs();

//        mWalkthroughAdapter = new walkthroughAdapter(getSupportFragmentManager());
//        walkthroughViewPager.setAdapter(mWalkthroughAdapter);
//        walkthroughIndicator.setViewPager(walkthroughViewPager);
//        walkthroughIndicator.setRadius(10f);
//        walkthroughIndicator.setStrokeWidth(2);
//        walkthroughIndicator.setFillColor(getResources().getColor(R.color.colorAccent));
//        walkthroughIndicator.setStrokeColor(getResources().getColor(R.color.colorAccent));

//        walkthroughViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    thirdPageSeen= false;
//                    layoutSkipWalkthrough.setVisibility(View.VISIBLE);
//                    layoutLetsGo.setVisibility(View.INVISIBLE);
//                }
//                if (position == 1) {
//                    if (thirdPageSeen) buttonSwapAnimation(true);
//                }
//                if (position == 2) {
//                    thirdPageSeen=true;
//                    buttonSwapAnimation(false);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


    }


//    private void initializeWalkThroughs() {
//        walkthroughs = new ArrayList<Fragment>();
//        walkthroughs.add(new WalkthroughFirst());
//        walkthroughs.add(new WalkthroughSecond());
//        walkthroughs.add(new WalkthroughThird());
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_skip_walkthrough:
            case R.id.layout_lets_go:
                Intent mIntent = new Intent(getApplicationContext(), TimelineFragment.class);
                startActivity(mIntent);
        }

    }

//    private void buttonSwapAnimation(boolean right) {
//        int width = metrics.widthPixels;
//        int dx = width - (int) layoutLetsGo.getX();
//
//        TranslateAnimation goButtonAnimation = null;
//        if (right) {
//            goButtonAnimation = new TranslateAnimation(0, dx, 0, 0);
//        } else {
//            goButtonAnimation = new TranslateAnimation(dx, 0, 0, 0);
//        }
//        goButtonAnimation.setDuration(250);
//        goButtonAnimation.setFillAfter(true);
//
//        layoutLetsGo.startAnimation(goButtonAnimation);
//
//        dx = (int) layoutSkipWalkthrough.getX() + layoutSkipWalkthrough.getMeasuredWidth();
//        TranslateAnimation skipButtonAnimation = null;
//        if (right) {
//            skipButtonAnimation = new TranslateAnimation(-dx, 0, 0, 0);
//        } else {
//            skipButtonAnimation = new TranslateAnimation(0, -dx, 0, 0);
//        }
//        skipButtonAnimation.setDuration(250);
//        skipButtonAnimation.setFillAfter(true);
//
//        layoutSkipWalkthrough.startAnimation(skipButtonAnimation);
//    }

//    class walkthroughAdapter extends FragmentPagerAdapter {
//
//
//        public walkthroughAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//
//        @Override
//        public Fragment getItem(int position) {
//            return walkthroughs.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return walkthroughs.size();
//        }
//    }
}
