package com.clipstraw.gx.clipstraw.pipedprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;

/**
 * Created by AhmedOsama on 06-01-2016.
 */
class PipedProgressBlock extends RelativeLayout {

    public static final int BLOCK_HORIZONTAL_RIGHT = 0;
    public static final int BLOCK_VERTICAL_UP = 1;
    public static final int BLOCK_RIGHT_TOP = 2;
    public static final int BLOCK_RIGHT_BOTTOM = 3;
    public static final int BLOCK_TOP_RIGHT = 4;
    public static final int BLOCK_BOTTOM_RIGHT = 5;
    public static final int BLOCK_HORIZONTAL_LEFT = 6;
    public static final int BLOCK_VERTICAL_DOWN = 7;
    public static final int BLOCK_LEFT_TOP = 8;
    public static final int BLOCK_LEFT_BOTTOM = 9;
    public static final int BLOCK_TOP_LEFT = 10;
    public static final int BLOCK_BOTTOM_LEFT = 11;

    private float contribution;

    private int blockType;

    private View coverBlock;

    private float contributionUtilised;

    public static final float ANIMATION_SPEED = 200.0f;

    public PipedProgressBlock(Context context) {
        super(context);
        init(context, null);
    }

    public PipedProgressBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PipedProgressBlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
            if (attrs != null){
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PipedProgressBlock);
                //System.out.println("typed array : " + a);
                blockType = a.getInt(R.styleable.PipedProgressBlock_block_type, BLOCK_HORIZONTAL_RIGHT);
            }
            else {
                blockType = BLOCK_HORIZONTAL_RIGHT;
            }
            contributionUtilised = 0.0f;

    }

    public int getBlockType() {
        return blockType;
    }

    public View getCoverBlock() {
        return coverBlock;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        coverBlock = ((ViewGroup)this).getChildAt(1);
        switch(blockType){
            // in case the block is a horizontal pipe, the contribution of block is equal to
            // the width of the block
            case BLOCK_HORIZONTAL_RIGHT:
            case BLOCK_HORIZONTAL_LEFT:
                setContribution(getMeasuredWidth());
                //System.out.println("getMeasuredWidth : " + getMeasuredWidth());
                break;

            // in case the block is a vertical pipe, the contribution of block is equal to
            // the height of the block
            case BLOCK_VERTICAL_UP:
            case BLOCK_VERTICAL_DOWN:
                setContribution(getMeasuredHeight());
               // System.out.println("getMeasuredHeight : " + getMeasuredHeight());
                break;

            // in case the block is an angle connecting two pipes, the contribution of block is equal to
            // the perimeter of the arc (quarter circle) which the block represents
            case PipedProgressBlock.BLOCK_BOTTOM_RIGHT :
            case PipedProgressBlock.BLOCK_TOP_RIGHT :
            case PipedProgressBlock.BLOCK_RIGHT_BOTTOM :
            case PipedProgressBlock.BLOCK_RIGHT_TOP :
            case PipedProgressBlock.BLOCK_BOTTOM_LEFT:
            case PipedProgressBlock.BLOCK_TOP_LEFT :
            case PipedProgressBlock.BLOCK_LEFT_BOTTOM :
            case PipedProgressBlock.BLOCK_LEFT_TOP :
                setContribution( 1.57f * getMeasuredWidth());
                //System.out.println("getMeasuredWidth : " + getMeasuredWidth());
                break;
        }
    }

    public float utilizeContribution(float progressPercentage, float totalContribution, ArrayList<PipedProgress.PipedAnimationViewPair> animationList){
        float contributionDue = contribution - contributionUtilised;
        //System.out.println("contribution due : " + contributionDue);
        //System.out.println("total contribution : " + totalContribution);
        float newContribution = 0.0f;
        float contributionDuePercentage = ( contributionDue / totalContribution ) * 100;
       // System.out.println("contribution due percentage : " + contributionDuePercentage);
        if (contributionDuePercentage > progressPercentage){
            newContribution = (progressPercentage * totalContribution) / 100 ;
            progressPercentage = 0;
        }
        else{
            newContribution = (contributionDuePercentage * totalContribution) / 100 ;
            progressPercentage -= contributionDuePercentage;
        }
        ///System.out.println("new contribution : " + newContribution);
        //System.out.println("progress percentage : " + progressPercentage);
        addAnimation(newContribution, animationList);


        return progressPercentage;
    }

    private void addAnimation(float newContribution, ArrayList<PipedProgress.PipedAnimationViewPair> animationList){
        Animation animation = null;
        PipedProgress parent = (PipedProgress)this.getParent();
        switch(blockType){
            case BLOCK_HORIZONTAL_RIGHT:
                animation = new TranslateAnimation(contributionUtilised, contributionUtilised + newContribution, 0, 0);
                break;

            case BLOCK_HORIZONTAL_LEFT:
                animation = new TranslateAnimation(-contributionUtilised, - ( contributionUtilised + newContribution ), 0, 0);
                break;

            case BLOCK_VERTICAL_UP:
                animation = new TranslateAnimation(0, 0, -contributionUtilised, -(contributionUtilised + newContribution));
                break;

            case BLOCK_VERTICAL_DOWN:
                animation = new TranslateAnimation(0, 0, contributionUtilised, contributionUtilised + newContribution);
                break;

            case BLOCK_TOP_RIGHT :
                animation = new RotateAnimation(getDegreeFromContribution(contributionUtilised), getDegreeFromContribution(contributionUtilised + newContribution), this.getMeasuredWidth(), this.getMeasuredHeight());
                break;

            case BLOCK_BOTTOM_LEFT :
                animation = new RotateAnimation(getDegreeFromContribution(contributionUtilised), getDegreeFromContribution(contributionUtilised + newContribution), 0, 0);
                break;

            case BLOCK_RIGHT_BOTTOM :
                animation = new RotateAnimation(getDegreeFromContribution(contributionUtilised), getDegreeFromContribution(contributionUtilised + newContribution), 0, this.getMeasuredHeight());
                break;

            case BLOCK_LEFT_TOP :
                animation = new RotateAnimation(getDegreeFromContribution(contributionUtilised), getDegreeFromContribution(contributionUtilised + newContribution), this.getMeasuredWidth(), 0);
                break;

            case BLOCK_BOTTOM_RIGHT :
                animation = new RotateAnimation(-getDegreeFromContribution(contributionUtilised), -getDegreeFromContribution(contributionUtilised + newContribution), this.getMeasuredWidth(), 0);
                break;

            case BLOCK_TOP_LEFT :
                animation = new RotateAnimation(-getDegreeFromContribution(contributionUtilised), -getDegreeFromContribution(contributionUtilised + newContribution), 0, this.getMeasuredHeight());
                break;

            case BLOCK_RIGHT_TOP :
                animation = new RotateAnimation(-getDegreeFromContribution(contributionUtilised), -getDegreeFromContribution(contributionUtilised + newContribution), 0, 0);
                break;

            case BLOCK_LEFT_BOTTOM :
                animation = new RotateAnimation(-getDegreeFromContribution(contributionUtilised), -getDegreeFromContribution(contributionUtilised + newContribution), this.getMeasuredWidth(), this.getMeasuredHeight());
                break;

        }

        float duration = newContribution * (parent.getAnimationDuration() / parent.getTotalContribution());
        animation.setDuration((long) duration);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animationList.add(((PipedProgress)(this.getParent())).new PipedAnimationViewPair(this, animation));
        animationList.get(animationList.size()-1).setAnimationNumber(animationList.size());


        contributionUtilised += newContribution;
    }

    private float getDegreeFromContribution(float contribution){
        return ( contribution / this.contribution ) * 90;
    }

    public float getContribution() {
        return contribution;
    }

    public void setContribution(float contribution) {
        this.contribution = contribution;
    }
}
