package com.clipstraw.gx.clipstraw;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.settings.AboutUsFragment;
import com.clipstraw.gx.clipstraw.settings.AdsOnFragment;
import com.clipstraw.gx.clipstraw.settings.DeleteAccountFragment;
import com.clipstraw.gx.clipstraw.settings.MainSettingsFragment;
import com.clipstraw.gx.clipstraw.settings.NetworkUsesFragment;
import com.clipstraw.gx.clipstraw.settings.PrivacyPolicyFragment;

import widgets.LogoView;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final byte NO_FRAGMENT = -1;

    private static final byte FRAGMENT_MAIN = 0;

    private static final byte FRAGMENT_ABOUT_US = 1;

    private static final byte FRAGMENT_ADS = 2;

    private static final byte FRAGMENT_TERMS_OF_SERVICE = 3;

    private static final byte FRAGMENT_NETWORK_USAGE = 4;

    private static final byte FRAGMENT_PRIVACY_POLICY = 5;

    private static final byte FRAGMENT_DELETE_ACCOUNT = 6;

    private static final byte FRAGMENT_WALKTHROUGH = 7;

    private SettingsFragment fragments[];

    private int topFragment;

    private Button btnApply, btnBack;

    private LogoView logoView;

    private float logoDeltaY;

    private int fragmentContainerId = R.id.layout_container;
    private DisplayMetrics displayMetrics;
    private View view;

    TextView tvAppName;

    RelativeLayout.LayoutParams containerParams;
    int containerLeftPadding, containerRightPadding, containerTopPadding, containerBottomPadding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        view = findViewById(fragmentContainerId);


        fragments = new SettingsFragment[8];
        topFragment = NO_FRAGMENT;

        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        btnApply = (Button) findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(this);

        logoView = (LogoView) findViewById(R.id.logo_view);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        switchToFragment(FRAGMENT_MAIN, null);

    }

    @Override
    public void onBackPressed() {
        if (topFragment > FRAGMENT_MAIN) {

            if (topFragment == FRAGMENT_ABOUT_US) {
                onAboutAction(false);
            }

            switchToFragment(FRAGMENT_MAIN, null);
            return;
        }
        super.onBackPressed();
    }

    private void switchToFragment(int fragmentId, Bundle args) {
        if (fragmentId == topFragment) return;

        if (fragmentId == FRAGMENT_MAIN && topFragment > fragmentId) {
            getSupportFragmentManager().popBackStack();
            hideButtons();
            logoView.setIconSrc(fragments[fragmentId].getIconId(), LogoView.ANIMATION_LEFT);
            logoView.setLabel(fragments[fragmentId].getTitle());
            topFragment = fragmentId;
            return;
        }

        fragments[fragmentId] = null;
        switch (fragmentId) {
            case FRAGMENT_MAIN:
                fragments[fragmentId] = new MainSettingsFragment();
                break;

            case FRAGMENT_ADS:
                fragments[fragmentId] = new AdsOnFragment();
                break;

            case FRAGMENT_NETWORK_USAGE:
                fragments[fragmentId] = new NetworkUsesFragment();
                break;

            case FRAGMENT_PRIVACY_POLICY:
                fragments[fragmentId] = new PrivacyPolicyFragment();
                break;
            case FRAGMENT_DELETE_ACCOUNT:
                fragments[fragmentId] = new DeleteAccountFragment();
                break;
            case FRAGMENT_ABOUT_US:
                fragments[fragmentId] = new AboutUsFragment();
                break;

        }

        if (fragments[fragmentId] != null) {
            if (args != null) {
                fragments[fragmentId].setArguments(args);
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (topFragment == NO_FRAGMENT) {
                fragmentTransaction.add(fragmentContainerId, fragments[fragmentId]);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out, R.anim.fragment_in_reverse, R.anim.fragment_out_reverse);
                fragmentTransaction.replace(fragmentContainerId, fragments[fragmentId]).addToBackStack(null);
            }
            fragmentTransaction.commit();
            topFragment = fragmentId;
            if (fragmentId == FRAGMENT_MAIN) {
                hideButtons();
                logoView.setIconSrc(fragments[fragmentId].getIconId(), LogoView.ANIMATION_RIGHT);
            }
            if (fragmentId > FRAGMENT_MAIN) {
                if (fragmentId != FRAGMENT_ABOUT_US) {
                    showButtons();
                }


                logoView.setIconSrc(fragments[fragmentId].getIconId(), LogoView.ANIMATION_LEFT);


            }
            logoView.setLabel(fragments[fragmentId].getTitle());

        }
    }

    private void showButtons() {
        btnApply.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        int deltaY = displayMetrics.heightPixels - (int) btnApply.getY();
        buttonAnimation(deltaY, 0);
    }

    private void hideButtons() {
        int deltaY = displayMetrics.heightPixels - (int) btnApply.getY();
        buttonAnimation(0, deltaY);
        btnApply.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
    }

    private void buttonAnimation(int fromDeltaY, int toDeltaY) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, fromDeltaY, toDeltaY);
        animation.setDuration(500);
        //animation.setFillAfter(true);
        btnApply.startAnimation(animation);
        btnBack.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ads:
                switchToFragment(FRAGMENT_ADS, null);
                break;
            case R.id.tv_network_uses:
                switchToFragment(FRAGMENT_NETWORK_USAGE, null);
                break;

            case R.id.tv_privacy_policy:
                switchToFragment(FRAGMENT_PRIVACY_POLICY, null);
                break;

            case R.id.btn_back:
                switchToFragment(FRAGMENT_MAIN, null);
                break;
            case R.id.tv_del_account:
                switchToFragment(FRAGMENT_DELETE_ACCOUNT, null);
                break;

            case R.id.tv_walkthrough:

                Toast.makeText(getApplicationContext(),"walkthrough",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_about_us:


                onAboutAction(true);
                switchToFragment(FRAGMENT_ABOUT_US, null);
                break;
            case R.id.tv_refresh_history:
                AlertDialog.Builder builder =new AlertDialog.Builder(SettingsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.referesh_account_history_popup, null);
                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();
                dialog.setCancelable(false);

                dialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "cleared ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });



                dialog.show();


                break;

        }
    }

    @SuppressLint("NewApi")
    private void onAboutAction(boolean isAbout) {

        ScaleAnimation mScaleAnimation = null;
        TranslateAnimation mTranslateAnimation = null;
        AnimationSet mAnimationSet;
        if (isAbout) {


            containerLeftPadding = view.getPaddingLeft();
            containerRightPadding = view.getPaddingRight();
            containerTopPadding = view.getPaddingTop();
            containerBottomPadding = view.getPaddingBottom();

            containerParams = ((RelativeLayout.LayoutParams) view.getLayoutParams());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, 0);
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(Color.BLACK);
            view.setPadding(0, 0, 0, 0);

            mScaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, logoView.getMeasuredWidth() / 2, logoView.getMeasuredHeight() / 2);


            logoDeltaY = (displayMetrics.heightPixels / 2) - (logoView.getY() + logoView.getHeight() * 1.5f + 30);


            mTranslateAnimation = new TranslateAnimation(0, 0, 0, logoDeltaY);


        } else {

            mScaleAnimation = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, logoView.getMeasuredWidth() / 2, logoView.getMeasuredHeight() / 2);


            //Toast.makeText(getApplicationContext(),"DeltaY : "+deltaY,Toast.LENGTH_SHORT).show();
            mTranslateAnimation = new TranslateAnimation(0, 0, logoDeltaY, 0);

            view.setBackgroundResource(R.drawable.border_profile_pages);

            view.setLayoutParams(containerParams);
            view.setPadding(containerLeftPadding, containerTopPadding, containerRightPadding, containerBottomPadding);
        }

        mScaleAnimation.setFillAfter(true);
        mScaleAnimation.setDuration(700);
        mTranslateAnimation.setDuration(700);
        mAnimationSet = new AnimationSet(getApplicationContext(), null);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);
        mAnimationSet.setDuration(700);
        mAnimationSet.setFillAfter(true);

        mAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        logoView.startAnimation(mAnimationSet);

    }


}
