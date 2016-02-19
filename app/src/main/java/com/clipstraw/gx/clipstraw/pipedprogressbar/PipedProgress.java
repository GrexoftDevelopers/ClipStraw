    package com.clipstraw.gx.clipstraw.pipedprogressbar;

    import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
    import android.view.View;
    import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

    /**
     * Created by AhmedOsama on 06-01-2016.
     */
    public class PipedProgress extends RelativeLayout {

        Context context;

        private float totalContribution;

        private float progressPercentage;

        private int currentProgressBlock;

        private ArrayList<PipedAnimationViewPair> animationList;

        private int currentAnimationOffset;

        private long progressStartSnapshot;

        private int animationsStarted;
        private int animationsFinished;

        private long animationDuration;

        private ProgressListener progressListener;

        public PipedProgress(Context context) {
            super(context);
            initContributions(context);
        }

        public PipedProgress(Context context, AttributeSet attrs) {
            super(context, attrs);
            initContributions(context);
        }

        public PipedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initContributions(context);
        }

        private void initContributions(Context context)
        {
            this.context = context;
            totalContribution = 0;
            animationList = new ArrayList<PipedAnimationViewPair>();
            progressStartSnapshot = 0;
            animationsFinished = 0;
            animationsStarted = 0;

        }

        public int getAnimationsStarted() {
            return animationsStarted;
        }

        public int getAnimationsFinished() {
            return animationsFinished;
        }

        public void setProgressListener(ProgressListener progressListener) {
            this.progressListener = progressListener;
        }

        public long getAnimationDuration() {
            return animationDuration;
        }

        public void setAnimationDuration(long animationDuration) {
            this.animationDuration = animationDuration;
        }



        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            //System.out.println("inside onlayout");
            if (!isInEditMode() && changed){
                int children = ((ViewGroup)this).getChildCount();
                //Toast.makeText(context, "no of children : " + children, Toast.LENGTH_SHORT).show();
                totalContribution = 0.0f;
                for (int i = 0 ; i < children ; i++){
                    try {
                        View view = ((ViewGroup)this).getChildAt(i);
                        if (!(view instanceof PipedProgressBlock)) continue;
                        PipedProgressBlock child = (PipedProgressBlock)view;
                        //System.out.println("contribution : " + child.getContribution());
                        this.totalContribution += child.getContribution();
                    }
                    catch (ClassCastException ex){
                        ex.printStackTrace();
                    }
                }
                //System.out.println("total contribution : " + this.totalContribution);
            }
        }

        public void setProgressPercentage(float progressPercentage) {
            if (progressPercentage > 100 || progressPercentage <= this.progressPercentage) return;
            float deltaProgress = progressPercentage - this.progressPercentage;
            while(deltaProgress > 0 && currentProgressBlock < ((ViewGroup) this).getChildCount()){
                //System.out.println("calling utilize contribution : ");
                //System.out.println("delta progress : " + deltaProgress);
                //System.out.println("total contribution : " + totalContribution);
                deltaProgress = ((PipedProgressBlock)(((ViewGroup) this).getChildAt(currentProgressBlock))).utilizeContribution(deltaProgress, totalContribution, animationList);
                if (deltaProgress > 0) currentProgressBlock++;
            }
            this.progressPercentage = progressPercentage;
            if (animationsFinished == animationsStarted) addAnimationSet();
        }

        private void addAnimationSet(){
            progressStartSnapshot = System.currentTimeMillis();
            //System.out.println("progressStartSnapshot : " + progressStartSnapshot);
           // System.out.println("inside addAnimationSet");
            //System.out.println("currentAnimationOffset : " + currentAnimationOffset);
            //System.out.println("animationList.size() : " + animationList.size());
            if (currentAnimationOffset == animationList.size()) return;
            int i;
            long startOffset = 0;
            for ( i = currentAnimationOffset ; i < animationList.size() ; i++){
                final PipedAnimationViewPair pipedAnimationViewPair = animationList.get(i);
                //pipedAnimationViewPair.getAnimation().setAnimationListener(new ProgressAnimationListener());
                pipedAnimationViewPair.setBlockAnimationListener(new BlockAnimationListenerClass());


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pipedAnimationViewPair.getPipedProgressBlock().getCoverBlock().startAnimation(pipedAnimationViewPair.getAnimation());
                        }
                    }, startOffset);


                //System.out.println("animation offset : " + startOffset);
               // System.out.println("currentAnimationOffset after adding animations: " + currentAnimationOffset);

                startOffset = startOffset + pipedAnimationViewPair.getAnimation().getDuration();
            }
            currentAnimationOffset = i;
        }

        public int getCurrentAnimationOffset() {
            return currentAnimationOffset;
        }

        public long getProgressStartSnapshot() {
            return progressStartSnapshot;
        }

        public void setProgressStartSnapshot(long progressStartSnapshot) {
            this.progressStartSnapshot = progressStartSnapshot;
        }

        public float getTotalContribution() {
            return totalContribution;
        }

        public void setChildrenVisibility(int visibility) {
            for(int i = 0 ; i < ((ViewGroup)this).getChildCount(); i++){
                ((ViewGroup)this).getChildAt(i).setVisibility(visibility);
            }
        }

        class BlockAnimationListenerClass implements BlockAnimationListener{

            @Override
            public void onBlockAnimationFinished(int animationNumber) {
                animationsFinished = animationNumber;
                //System.out.println("inside on animation end, Animations finished : " + animationsFinished);
                if (animationsFinished == animationsStarted) {
                    if (currentAnimationOffset < animationList.size()){
                        addAnimationSet();
                    }
                    else if (animationNumber == animationList.size() && progressPercentage > 99.99 && progressListener != null){
                        progressListener.onProgressComplete();
                       // System.out.println("inside on animation end , current animation offset : " + currentAnimationOffset);
                        //System.out.println("inside on animation end , animation list.size : " + animationList.size());
                       // System.out.println("inside on animation end , progress percentage : " + progressPercentage);
                    }

                }

            }

            @Override
            public void onBlockAnimationStarted(int animationNumber) {
                animationsStarted = animationNumber;
                //System.out.println("inside on animation start, Animations started : " + animationsStarted);
            }
        }





        class PipedAnimationViewPair{

            private PipedProgressBlock pipedProgressBlock;

            private Animation animation;

            private int animationNumber;

            private BlockAnimationListener blockAnimationListener;


            public PipedAnimationViewPair(PipedProgressBlock pipedProgressBlock, Animation animation) {
                this.pipedProgressBlock = pipedProgressBlock;
                this.animation = animation;
            }

            public Animation getAnimation() {
                return animation;
            }

            public PipedProgressBlock getPipedProgressBlock() {
                return pipedProgressBlock;
            }

            public void setAnimationNumber(int animationNumber) {
                this.animationNumber = animationNumber;
            }

            public int getAnimationNumber() {
                return animationNumber;
            }

            public void setBlockAnimationListener(BlockAnimationListener blockAnimationListener) {
                this.blockAnimationListener = blockAnimationListener;
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        PipedAnimationViewPair.this.blockAnimationListener.onBlockAnimationStarted(animationNumber);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        PipedAnimationViewPair.this.blockAnimationListener.onBlockAnimationFinished(animationNumber);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }

        public interface ProgressListener{
            public void onProgressComplete();
        }

        public interface BlockAnimationListener{
            public void onBlockAnimationFinished(int animationNumber);
            public void onBlockAnimationStarted(int animationNumber);
        }




    }
