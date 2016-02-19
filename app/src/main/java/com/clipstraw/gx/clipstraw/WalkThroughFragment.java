package com.clipstraw.gx.clipstraw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.clipstraw.gx.clipstraw.walkthrough.WalkthroughFirst;
import com.clipstraw.gx.clipstraw.walkthrough.WalkthroughSecond;
import com.clipstraw.gx.clipstraw.walkthrough.WalkthroughThird;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by tahir on 2/16/2016.
 */
public class WalkThroughFragment extends Fragment {

    private View fragmentView;

    private ArrayList<Fragment> walkthroughs;
    private ViewPager walkthroughViewPager;
    private CirclePageIndicator walkthroughIndicator;
    private walkthroughAdapter mWalkthroughAdapter;
    private Button layoutSkipWalkthrough, layoutLetsGo;
    private boolean thirdPageSeen;
    private RelativeLayout layoutWalkthroughContainer;

    WalkThroughListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.layout_walk_through,container,false);

        walkthroughViewPager = (ViewPager) fragmentView.findViewById(R.id.walkthrough_view_pager);
        walkthroughIndicator = (CirclePageIndicator) fragmentView.findViewById(R.id.walkthrough_indicator);
        layoutSkipWalkthrough = (Button) fragmentView.findViewById(R.id.layout_skip_walkthrough);
        layoutLetsGo = (Button) fragmentView.findViewById(R.id.layout_lets_go);
        layoutWalkthroughContainer = (RelativeLayout) fragmentView.findViewById(R.id.layout_walkthrough_container);

        layoutSkipWalkthrough.setOnClickListener((View.OnClickListener)getActivity());
        layoutLetsGo.setOnClickListener((View.OnClickListener)getActivity());

        getWalkthroughs();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (mCallback != null) {
                    mCallback.onWalkThroughInitialized(walkthroughViewPager);
                }
            }
        });

        return fragmentView;

    }

    private void getWalkthroughs() {
        initializeWalkThroughs();
        layoutSkipWalkthrough.setVisibility(View.VISIBLE);

        mWalkthroughAdapter = new walkthroughAdapter(getActivity().getSupportFragmentManager());

        walkthroughViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        walkthroughViewPager.setAdapter(mWalkthroughAdapter);

        walkthroughIndicator.setViewPager(walkthroughViewPager);

        walkthroughIndicator.setRadius(15f);
        walkthroughIndicator.setStrokeWidth(2);
        walkthroughIndicator.setFillColor(getResources().getColor(R.color.red));
        walkthroughIndicator.setStrokeColor(getResources().getColor(R.color.grayColor));


        walkthroughViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        thirdPageSeen = false;
                        break;
                    case 1:
                        layoutSkipWalkthrough.setVisibility(View.VISIBLE);
                        if (thirdPageSeen) {
                            buttonSwapAnimation(true);
                        }
                        break;
                    case 2:
                        layoutSkipWalkthrough.setVisibility(View.INVISIBLE);
                        thirdPageSeen = true;
                        buttonSwapAnimation(false);
                        break;

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initializeWalkThroughs() {
        walkthroughs = new ArrayList<Fragment>();
        walkthroughs.add(new WalkthroughFirst());
        walkthroughs.add(new WalkthroughSecond());
        walkthroughs.add(new WalkthroughThird());

    }

    private void buttonSwapAnimation(final boolean right) {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int dx = width - (int) layoutLetsGo.getX();

        TranslateAnimation goButtonAnimation = null;
        if (right) {
            goButtonAnimation = new TranslateAnimation(0, dx, 0, 0);
            goButtonAnimation.setFillAfter(false);

        } else {
            goButtonAnimation = new TranslateAnimation(dx, 0, 0, 0);
            goButtonAnimation.setFillAfter(true);
        }
        goButtonAnimation.setDuration(250);


        layoutLetsGo.startAnimation(goButtonAnimation);


        dx = (int) layoutSkipWalkthrough.getX() + layoutSkipWalkthrough.getMeasuredWidth();
        TranslateAnimation skipButtonAnimation = null;
        if (right) {

            skipButtonAnimation = new TranslateAnimation(-dx, 0, 0, 0);
            skipButtonAnimation.setFillAfter(true);


        } else {
            skipButtonAnimation = new TranslateAnimation(0, -dx, 0, 0);
            skipButtonAnimation.setFillAfter(false);
        }
        skipButtonAnimation.setDuration(250);


        layoutSkipWalkthrough.startAnimation(skipButtonAnimation);


    }

    public class walkthroughAdapter extends FragmentPagerAdapter {


        public walkthroughAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return walkthroughs.size();
        }

        @Override
        public Fragment getItem(int position) {
            return walkthroughs.get(position);
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (WalkThroughListener)activity;
    }

    public interface WalkThroughListener {
        public void onWalkThroughInitialized(ViewPager viewPager);
    }

}
