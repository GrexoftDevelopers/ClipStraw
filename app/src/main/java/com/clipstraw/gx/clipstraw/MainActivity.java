package com.clipstraw.gx.clipstraw;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.model.session.ClipstrawSession;
import com.clipstraw.gx.clipstraw.pipedprogressbar.PipedProgress;
import com.clipstraw.gx.clipstraw.helper.session.SessionManager;
import com.clipstraw.gx.clipstraw.walkthrough.WalkthroughFirst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import widgets.LogoView;

public class MainActivity extends FragmentActivity implements
        PipedProgress.ProgressListener,
        View.OnClickListener,
        SessionManager.SessionListener,
        WalkThroughFragment.WalkThroughListener {

    public static final int FRAGMENT_LOGIN = 0;
    public static final int FRAGMENT_REGISTER = 1;
    public static final int FRAGMENT_WALK_THROUGH = 2;

    public static final int NO_FRAGMENT = -1;

    private int topFragment;

    public static final int CONTAINER = R.id.layer_fragment_container;

    private LinearLayout thumbnailLayout, layoutFragmentContainer;


    private LinearLayout layoutLogoView;

    private int logoLayoutOriginalWidth, logoLayoutOriginalHeight;

    private static final float LOGO_SCALE_FACTOR = 0.7f;

    private LogoView logoView;

    private View layerMask;

    public static final String SHARED_PREFRENCE = "MY PREFERENCE";

    private DisplayMetrics metrics;

    private PipedProgress pipedProgress;

    private ClipstrawApplication mClipstrawApplication;

    private Fragment[] fragments;

    //private boolean isWalktrhoughStart;
    private boolean pipedProgressFinished;
    private boolean thumbnailAnimationFinished;

    TextView tvAppName;

    public static final long TOTAL_LOADING_DURATION = 5000;

    private static final int RC_PROFILE_ACTIVITY = 10001;

    private int nextActivityLevel;
    private static final String ACTIVITY_LEVEL_KEY = "activity_level";

    private static final int LEVEL_START_FIRST = 0;
    private static final int LEVEL_PIPE_SHOWN = 1;
    private static final int LEVEL_THUMBNAILS_SHOWN = 2;
    private static final int LEVEL_LOGO_SHOWN = 3;
    private static final int LEVEL_PIPE_ANIMATION_FINISH = 4;
    private static final int LEVEL_FRAGMENTS_SHOWN = 5;
    private static final int LEVEL_WLAKTHROUGH_SHOWN = 6;

    SessionManager sessionManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance();

        fragments = new Fragment[3];

        topFragment = -1;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        initializeWidgets();

        if (savedInstanceState != null) {
            nextActivityLevel = savedInstanceState.getInt(ACTIVITY_LEVEL_KEY);
        } else {
            nextActivityLevel = LEVEL_START_FIRST;
        }

        sessionManager.setSessionListener(this);

        if (sessionManager.getActiveSession() != null) {
            switchToProfileActivity();
        }

    }

    private void initializeWidgets() {
        thumbnailLayout = (LinearLayout) findViewById(R.id.thumbnail_layout);

        layerMask = findViewById(R.id.layer_view);

        layoutLogoView = (LinearLayout) findViewById(R.id.layout_logo_name);
        layoutLogoView.setVisibility(View.INVISIBLE);
        logoView = (LogoView) findViewById(R.id.logo_view);

        layoutFragmentContainer = (LinearLayout) findViewById(R.id.layer_fragment_container);

        tvAppName = (TextView) findViewById(R.id.txt_app_name);


        pipedProgress = (PipedProgress) findViewById(R.id.piped_progress);
        pipedProgress.setChildrenVisibility(View.INVISIBLE);
        pipedProgress.setAnimationDuration(TOTAL_LOADING_DURATION);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("inside on start");
        processNextActivityLevel();

    }

    private void processNextActivityLevel() {
        System.out.println("inside process activity Level. activity level : " + nextActivityLevel);
        switch (nextActivityLevel) {
            case LEVEL_START_FIRST:
                layoutLogoView.setVisibility(View.GONE);
                layoutFragmentContainer.setVisibility(View.GONE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        progressAnimation(true);
                    }
                });
                break;

            case LEVEL_PIPE_SHOWN:
                layoutLogoView.setVisibility(View.GONE);
                layoutFragmentContainer.setVisibility(View.GONE);
                pipedProgress.setVisibility(View.VISIBLE);
                pipedProgress.setProgressListener(MainActivity.this);
                pipedProgress.setProgressPercentage(100);
                new ThumbnailAnimation().execute("");
                break;

            case LEVEL_THUMBNAILS_SHOWN:
                layoutFragmentContainer.setVisibility(View.GONE);
                layoutLogoView.setVisibility(View.INVISIBLE);
                logoAnimation(false);
                break;

            case LEVEL_LOGO_SHOWN:
                logoLayoutOriginalWidth = layoutLogoView.getMeasuredWidth();
                logoLayoutOriginalHeight = layoutLogoView.getMeasuredHeight();
                System.out.println("initial height before animation : " + layoutLogoView.getMeasuredHeight());
                layoutFragmentContainer.setVisibility(View.GONE);
                layoutLogoView.setVisibility(View.VISIBLE);
                if (pipedProgress.getVisibility() == View.VISIBLE) {
                    progressAnimation(false);
                } else {
                    nextActivityLevel = LEVEL_PIPE_ANIMATION_FINISH;
                    processNextActivityLevel();
                }
                break;

            case LEVEL_PIPE_ANIMATION_FINISH:
                layoutFragmentContainer.setVisibility(View.VISIBLE);
                layoutLogoView.setVisibility(View.VISIBLE);
                logoAnimation(true);
                switchToFragment(FRAGMENT_LOGIN);
                break;

            case LEVEL_FRAGMENTS_SHOWN:
                layoutFragmentContainer.setVisibility(View.VISIBLE);
                final int left = (int) (metrics.widthPixels - logoLayoutOriginalWidth * LOGO_SCALE_FACTOR) / 2;
                final int top = 0;
                final int width = (int) (logoLayoutOriginalWidth * LOGO_SCALE_FACTOR);
                final int height = (int) (logoLayoutOriginalHeight * LOGO_SCALE_FACTOR);

                System.out.println("get y before layout change : " + layoutLogoView.getY());

                layoutLogoView.layout(left,top,width,height);
                layoutLogoView.requestLayout();

                System.out.println("get y after layout change : " + layoutLogoView.getY());
                switchToFragment(FRAGMENT_LOGIN);
                break;
        }
        nextActivityLevel++;
    }

    private void switchToProfileActivity() {
        nextActivityLevel = LEVEL_FRAGMENTS_SHOWN;
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivityForResult(intent, RC_PROFILE_ACTIVITY);
        //overridePendingTransition(R.anim.fragment_in, R.anim.fragment_out);
    }

    private void switchToFragment(int fragmentId) {

        if (topFragment == fragmentId) return;

        int iconId = -1;

        switch (fragmentId) {

            case FRAGMENT_LOGIN:
                fragments[fragmentId] = new LoginFragment();
                iconId = R.mipmap.ic_timeline_large_white;
                break;

            case FRAGMENT_REGISTER:
                fragments[fragmentId] = new RegistrationFragment();
                iconId = R.mipmap.ic_user_large_white;
                break;

            case FRAGMENT_WALK_THROUGH:
                fragments[fragmentId] = new WalkThroughFragment();
                iconId = R.mipmap.ic_timeline_large_white;
                break;

        }

        if (fragments[fragmentId] != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (fragmentId > topFragment) {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);
                logoView.setIconSrc(iconId, LogoView.ANIMATION_LEFT);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in_reverse, R.anim.fragment_out_reverse);
                logoView.setIconSrc(iconId, LogoView.ANIMATION_RIGHT);
            }
            if (topFragment == NO_FRAGMENT) {
                fragmentTransaction.add(CONTAINER, fragments[fragmentId]);
            } else {
                fragmentTransaction.replace(CONTAINER, fragments[fragmentId]);
            }
            fragmentTransaction.commit();
            topFragment = fragmentId;

        }

    }

    @Override
    public void onProgressComplete() {
        pipedProgressFinished = true;
        if (thumbnailAnimationFinished) {
            processNextActivityLevel();
        }

    }

    @Override
    public void onBackPressed() {
        if (topFragment == FRAGMENT_REGISTER) {
            onClick(findViewById(R.id.btn_alt_login));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.btn_alt_register:
                fragments[FRAGMENT_REGISTER] = new RegistrationFragment();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out).replace(R.id.layer_fragment_container, fragments[FRAGMENT_REGISTER]).commit();
                logoView.setIconSrc(R.mipmap.ic_user_large_white, LogoView.ANIMATION_LEFT);
                topFragment = FRAGMENT_REGISTER;
                break;

            case R.id.btn_alt_login:
                fragments[FRAGMENT_LOGIN] = new LoginFragment();
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in_reverse, R.anim.fragment_out_reverse).replace(R.id.layer_fragment_container, fragments[FRAGMENT_LOGIN]).commit();
                logoView.setIconSrc(R.mipmap.ic_timeline_large_white, LogoView.ANIMATION_RIGHT);
                topFragment = FRAGMENT_LOGIN;
                break;

            case R.id.btn_register:
                Bundle registerCred = ((RegistrationFragment) fragments[FRAGMENT_REGISTER]).getUserCredentials();
                if (registerCred != null) {
                    SessionManager.getInstance().register(registerCred.getString("user_id"), registerCred.getString("email"), registerCred.getString("password"));
                    mProgressDialog.setMessage("Registering....");
                    mProgressDialog.show();
                }
                break;
            case R.id.btn_login:
                System.out.println("get y after long time from animation: " + layoutLogoView.getY());
                Bundle loginCred = ((LoginFragment) fragments[FRAGMENT_LOGIN]).getUserCredentials();
                if (loginCred != null) {
                    SessionManager.getInstance().login(loginCred.getString("user_id"), loginCred.getString("password"));
                    mProgressDialog.setMessage("Signing in....");
                    mProgressDialog.show();
                }
                break;
            case R.id.layout_skip_walkthrough:
            case R.id.layout_lets_go:
                switchToProfileActivity();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVITY_LEVEL_KEY, nextActivityLevel);
        System.out.println("inside onSaveInstanceState. activity level : " + nextActivityLevel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("in On activityResult");

        if (requestCode == RC_PROFILE_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("resultCode== result ok : " + "rq= " + requestCode + " rc= " + resultCode + " data " + data);
                super.onActivityResult(requestCode, resultCode, data);
                //String result=data.getStringExtra("logout");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("resultCode== result cancel : " + "rq= " + requestCode + " rc= " + resultCode + " data " + data);
                finish();
                //Write your code if there's no result
            }

        }


    }

    @Override
    public void onSessionCreated(ClipstrawSession session) {
        if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
        switch (topFragment) {
            case FRAGMENT_LOGIN:
                switchToProfileActivity();
                break;

            case FRAGMENT_REGISTER:
                switchToFragment(FRAGMENT_WALK_THROUGH);
                break;
        }

    }

    @Override
    public void onSessionDestroyed() {

    }

    @Override
    public void onSessionError(JSONObject error) {
        try {
            if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
            Toast.makeText(MainActivity.this, error.getString("error_msg"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWalkThroughInitialized(ViewPager viewPager) {

        int deltaY = (int) ((layoutLogoView.getY() - viewPager.getY() - ((RelativeLayout.LayoutParams)viewPager.getLayoutParams()).topMargin + layoutLogoView.getMeasuredHeight()));

        System.out.println("delta y : " + deltaY);

        System.out.println("get y afer register : " + layoutLogoView.getY());
        System.out.println("height afer register : " + layoutLogoView.getMeasuredHeight());

        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,-layoutLogoView.getY(),deltaY);
        translateAnimation.setDuration(700);

        ScaleAnimation scaleAnimation = new ScaleAnimation(LOGO_SCALE_FACTOR,1.0f,LOGO_SCALE_FACTOR,1.0f,layoutLogoView.getMeasuredWidth() / 2, layoutLogoView.getMeasuredHeight() / 2);
        scaleAnimation.setDuration(700);

        AnimationSet animationSet = new AnimationSet(getApplicationContext(),null);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);


        layoutLogoView.startAnimation(animationSet);


    }


    class ThumbnailAnimation extends AsyncTask<String, View, String> {

        private long interval;

        private ArrayList<View> thumbnails;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ArrayList<Integer> images = getImages();
            thumbnails = new ArrayList<View>();
            LinearLayout thumbnailRow;
            int j = 0, i = 0;
            System.out.println("images : " + images.size());
            while (i < 7) {
                thumbnailRow = (LinearLayout) getLayoutInflater().inflate(R.layout.thumbnail_row, thumbnailLayout, true);
                i++;
            }
            for (i = 0; i < ((ViewGroup) thumbnailLayout).getChildCount(); i++) {
                thumbnailRow = (LinearLayout) (((ViewGroup) thumbnailLayout).getChildAt(i));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_1));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_2));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_3));
                addToImageList(thumbnailRow.findViewById(R.id.thumbnail_col_4));
            }
            interval = TOTAL_LOADING_DURATION / thumbnails.size();
            //Toast.makeText(MainActivity.this, "thumbnail size : " + thumbnails.size(), Toast.LENGTH_SHORT).show();
        }

        private void addToImageList(View img) {
            if (thumbnails != null) {
                System.out.println("view is : " + img);
                img.setVisibility(View.INVISIBLE);
                thumbnails.add(img);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            while (thumbnails.size() > 0) {
                try {
                    Thread.currentThread().sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int rand = new Random().nextInt(thumbnails.size());
                publishProgress(thumbnails.get(rand));
                thumbnails.remove(rand);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(View... values) {
            //Toast.makeText(getApplicationContext(), "onProgressUpdate", Toast.LENGTH_SHORT).show();
            super.onProgressUpdate(values);
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, values[0].getMeasuredWidth() / 2, values[0].getMeasuredHeight() / 2);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setDuration(200);
            scaleAnimation.setInterpolator(new AccelerateInterpolator());
            values[0].startAnimation(scaleAnimation);
            // layerMask.setAlpha(layerMask.getAlpha() + 0.03f);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            thumbnailAnimationFinished = true;
            if (pipedProgressFinished) {
                processNextActivityLevel();
            }

        }

        private ArrayList<Integer> getImages() {

            ArrayList<Integer> images = new ArrayList<Integer>();
            for (int i = 0; i < 40; i++) {
                images.add(R.drawable.fr1);
            }
            return images;
        }

    }

    private void logoAnimation(Boolean isUp) {
        Toast.makeText(getApplicationContext(), "logo animation", Toast.LENGTH_SHORT).show();

        if (isUp) {

            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(700);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());

            tvAppName.startAnimation(alphaAnimation);

            ScaleAnimation mScaleAnimation = new ScaleAnimation(1.0f, LOGO_SCALE_FACTOR, 1.0f, LOGO_SCALE_FACTOR, layoutLogoView.getMeasuredWidth() / 2, layoutLogoView.getMeasuredHeight() / 2);
            mScaleAnimation.setDuration(700);

            final float deltaY = layoutLogoView.getY();// - dpToPx(50);
            Toast.makeText(getApplicationContext(), "get y : " + deltaY, Toast.LENGTH_SHORT).show();
            TranslateAnimation mTranslateAnimation = new TranslateAnimation(0, 0, 0, -deltaY);
            mTranslateAnimation.setDuration(700);

            AnimationSet mAnimationSet = new AnimationSet(getApplicationContext(), null);
            mAnimationSet.addAnimation(mScaleAnimation);
            mAnimationSet.addAnimation(mTranslateAnimation);
            mAnimationSet.setDuration(700);
            mAnimationSet.setFillAfter(true);
            mAnimationSet.setFillEnabled(true);
            mAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    int left = (int) (layoutLogoView.getX() + (layoutLogoView.getMeasuredWidth() * (1-LOGO_SCALE_FACTOR)) / 2);
//                    int top = (int) (layoutLogoView.getY() - deltaY);
//                    int width = (int) (layoutLogoView.getMeasuredWidth() * LOGO_SCALE_FACTOR);
//                    int height = (int) (layoutLogoView.getMeasuredHeight() * LOGO_SCALE_FACTOR);

                    processNextActivityLevel();


                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            System.out.println("get y before animation : " + layoutLogoView.getY());
            layoutLogoView.startAnimation(mAnimationSet);



        } else {

            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(700);
            alphaAnimation.setFillAfter(true);

            AnimationSet animationSet = new AnimationSet(getApplicationContext(), null);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setFillAfter(true);


            layoutLogoView.setVisibility(View.VISIBLE);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    processNextActivityLevel();

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            layoutLogoView.startAnimation(animationSet);


        }

    }

    private void progressAnimation(final Boolean show) {
        float dy = 0;
        long offset = 0;
        int screenHeight = metrics.heightPixels;
        TranslateAnimation translateAnimation = null;
        for (int i = 0; i < ((ViewGroup) pipedProgress).getChildCount(); i++) {
            View progressBlock = ((ViewGroup) pipedProgress).getChildAt(i);
            dy = screenHeight - progressBlock.getY();


            if (!show) {
                translateAnimation = new TranslateAnimation(0, 0, 0, dy);
                translateAnimation.setDuration(700);
                translateAnimation.setInterpolator(new AccelerateInterpolator());
            } else {
                translateAnimation = new TranslateAnimation(0, 0, dy, 0);
                translateAnimation.setDuration(800);
                translateAnimation.setInterpolator(new DecelerateInterpolator());
            }

            translateAnimation.setFillAfter(true);

            translateAnimation.setStartOffset(offset);

            progressBlock.startAnimation(translateAnimation);
            offset = offset + 100;
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                processNextActivityLevel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public float dpToPx(float dp) {
        Resources r = getApplicationContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    public void secondAct(View v) {
        Intent i = new Intent(this, EventsAcivity.class);
        startActivity(i);
    }


}
