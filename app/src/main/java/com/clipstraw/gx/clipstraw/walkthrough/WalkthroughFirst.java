package com.clipstraw.gx.clipstraw.walkthrough;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;


/**
 * Created by FaizZy on 06-01-2016.
 */
public class WalkthroughFirst extends Fragment {

    LinearLayout layoutLogoAppName, layoutDescription;
    View fragmentView;
    TextView tvExplore, tvDescLine1, tvDescline2, tvDescLine3;

//    AnimationListener mCallback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getActivity(), "oncreateView", Toast.LENGTH_SHORT).show();

        fragmentView = inflater.inflate(R.layout.view_pager1, container, false);
      //  layoutLogoAppName = (LinearLayout) fragmentView.findViewById(R.id.layout_logo_app_name);
        layoutDescription = (LinearLayout) fragmentView.findViewById(R.id.layout_description);
        tvExplore = (TextView) fragmentView.findViewById(R.id.tv_explore);
        tvDescLine1 = (TextView) fragmentView.findViewById(R.id.tv_desc_line1);
        tvDescline2 = (TextView) fragmentView.findViewById(R.id.tv_desc_line2);
        tvDescLine3 = (TextView) fragmentView.findViewById(R.id.tv_desc_line3);

//        if (!((MainActivity)getActivity()).isSecondPageSeen()){
//            new Handler().postDelayed(new Runnable() {
//
//                public void run() {
//                    startAnim();
//                }
//
//            }, 3000);
//        //}
//        else{
//            new Handler().post(new Runnable() {
//                @Override
//                public void run() {
//                    animateLogoApp(true);
//                    setDescriptionVisibility(View.VISIBLE);
//                }
//            });
//        }


        return fragmentView;


    }
}
//
//    public void startAnim() {
//        animateLogoApp(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                animatText();
//            }
//        }, 500/* 1sec delay */);
//    }
//
//    private void animateLogoApp(boolean instantAction) {
//
//
//
//        float toDeltaY = dpToPx(100);
//
//        System.out.println("Screen hight  : to deltaY" + toDeltaY);
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -toDeltaY);
//        if (!instantAction) translateAnimation.setDuration(1000);
//        translateAnimation.setFillAfter(true);
//        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        layoutLogoAppName.startAnimation(translateAnimation);
//
//        //Toast.makeText(getActivity(), "animatedLogo", Toast.LENGTH_SHORT).show();
//
//    }
//
//
//
//
//
//    public float dpToPx(float dp) {
//        Resources r = getActivity().getResources();
//        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
//        return px;
//    }
//
//    private void setDescriptionVisibility(int visibility){
//        layoutDescription.setVisibility(visibility);
//        tvExplore.setVisibility(visibility);
//        tvDescLine1.setVisibility(visibility);
//        tvDescline2.setVisibility(visibility);
//        tvDescLine3.setVisibility(visibility);
//    }
//
//    private void animatText() {
//
//
//        //Toast.makeText(getActivity(), "animatingText", Toast.LENGTH_SHORT).show();
//        setDescriptionVisibility(View.VISIBLE);
//
//
//
//        tvExplore.startAnimation(getAlphaTranslateAnimationSet(0));
//        tvDescLine1.startAnimation(getAlphaTranslateAnimationSet(100));
//        tvDescline2.startAnimation(getAlphaTranslateAnimationSet(200));
//        //AnimationSet descAnimationSet3 = getAlphaTranslateAnimationSet(300);
////        descAnimationSet3.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////                mCallback.onAnimationFinish();
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation animation) {
////
////            }
////        });
//        tvDescLine3.startAnimation(getAlphaTranslateAnimationSet(350));
//
//
//        //Toast.makeText(getActivity(), "animated text", Toast.LENGTH_SHORT).show();
//    }
//
//    public AnimationSet getAlphaTranslateAnimationSet(long offset) {
//        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
//        TranslateAnimation translateAnimText = new TranslateAnimation(0, 0, 20, 0);
//        AnimationSet animationSet = new AnimationSet(getActivity(), null);
//        animationSet.addAnimation(alphaAnim);
//        animationSet.addAnimation(translateAnimText);
//        animationSet.setDuration(700);
//        animationSet.setFillAfter(true);
//        animationSet.setInterpolator(new AccelerateInterpolator());
//        animationSet.setStartOffset(offset);
//        return animationSet;
//    }
//
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
////        if (activity instanceof MainActivity){
////            mCallback = (AnimationListener)activity;
////        }
//    }
//
////    public interface AnimationListener{
////        public void onAnimationFinish();
////        public AnimationSet getAlphaTranslateAnimationSet(long offset);
////    }
//
//}
