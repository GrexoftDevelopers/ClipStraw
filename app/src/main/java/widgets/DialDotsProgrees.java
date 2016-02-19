package widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.clipstraw.gx.clipstraw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faizzy on 04-02-2016.
 */
public class DialDotsProgrees extends View {

    private static final int DIRECTION_TOP = 0;
    private static final int DIRECTION_BOTTOM = 1;
    private static final int DIRECTION_LEFT = 2;
    private static final int DIRECTION_RIGHT = 3;

    public static final String TAG = DialDotsProgrees.class.getSimpleName();
    public static final double START_DELAY_FACTOR = 0.35;
    private static final float DEFAULT_GROWTH_MULTIPLIER = 1.75f;
    private static final int MIN_SHOW_TIME = 500; // ms
    private static final int MIN_DELAY = 500; // ms
    private int mDotColor;
    private int mDirection;
    private int mAnimationDuration;
    private int mWidthBetweenDotCenters;
    private int mNumberDots;
    private float mDotRadius;
    private float mDotScaleMultiplier;
    private float mDotMaxRadius;
    private float mHorizontalSpacing;
    private long mStartTime = -1;
    private boolean mShouldAnimate;
    private boolean mDismissed = false;
    private ArrayList<DialDotsDrawable> mDrawables = new ArrayList<>();
    private final List<Animator> mAnimations = new ArrayList<>();

    /** delayed runnable to stop the progress */
    private final Runnable mDelayedHide = new Runnable() {
        @Override
        public void run() {
            mStartTime = -1;
            setVisibility(View.GONE);
            stopAnimations();
        }
    };
    /** delayed runnable to start the progress */
    private final Runnable mDelayedShow = new Runnable() {
        @Override
        public void run() {
            if (!mDismissed) {
                mStartTime = System.currentTimeMillis();
                setVisibility(View.VISIBLE);
                startAnimations();
            }
        }
    };

    public DialDotsProgrees(Context context) {
        this(context, null);
    }

    public DialDotsProgrees(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialDotsProgrees(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DialDotsProgressBar);
        mNumberDots = a.getInt(R.styleable.DialDotsProgressBar_no_of_dots, 3);
        mDotRadius = a.getDimension(R.styleable.DialDotsProgressBar_android_radius, 8);
        mDotColor = a.getColor(R.styleable.DialDotsProgressBar_android_color, 0xff9c27b0);
        mDotScaleMultiplier = a.getFloat(
                R.styleable.DialDotsProgressBar_scale_multiplier,
                DEFAULT_GROWTH_MULTIPLIER);
        mAnimationDuration = a.getInt(R.styleable.DialDotsProgressBar_animation_duration, 300);
        mHorizontalSpacing =
                a.getDimension(R.styleable.DialDotsProgressBar_horizontal_spacing, 12);
        mDirection = a.getInt(R.styleable.DialDotsProgressBar_direction,DIRECTION_BOTTOM);
        a.recycle();

        mShouldAnimate = false;
        calculateMaxRadius();
        calculateWidthBetweenDotCenters();

        initDots();
        updateDots();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (computeMaxHeight() != h || w != computeMaxWidth()) {
            updateDots();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(mDelayedHide);
        removeCallbacks(mDelayedShow);
    }

    public void reset() {
        hideNow();
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     */
    @SuppressWarnings ("unused")
    public void hide() {
        hide(MIN_SHOW_TIME);
    }

    public void hide(int delay) {
        mDismissed = true;
        removeCallbacks(mDelayedShow);
        long diff = System.currentTimeMillis() - mStartTime;
        if ((diff >= delay) || (mStartTime == -1)) {
            mDelayedHide.run();
        } else {
            if ((delay - diff) <= 0) {
                mDelayedHide.run();
            } else {
                postDelayed(mDelayedHide, delay - diff);
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     */
    @SuppressWarnings ("unused")
    public void show() {
        show(MIN_DELAY);
    }

    @SuppressWarnings ("unused")
    public void showNow() {
        show(0);
    }

    @SuppressWarnings ("unused")
    public void hideNow() {
        hide(0);
    }

    public void show(int delay) {
        mStartTime = -1;
        mDismissed = false;
        removeCallbacks(mDelayedHide);

        if (delay == 0) {
            mDelayedShow.run();
        } else {
            postDelayed(mDelayedShow, delay);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shouldAnimate()) {
            for (DialDotsDrawable dot : mDrawables) {
                dot.draw(canvas);
            }
        }
    }

    @Override
    protected boolean verifyDrawable(final Drawable who) {
        if (shouldAnimate()) {
            return mDrawables.contains(who);
        }
        return super.verifyDrawable(who);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) computeMaxWidth(), (int) computeMaxHeight());
    }

    private float computeMaxHeight() {
        if (mDirection == DIRECTION_RIGHT || mDirection == DIRECTION_LEFT){
            return mDotMaxRadius * 2;
        }
        return computeWidth() + ((mDotMaxRadius - mDotRadius) * 2);
    }

    private float computeMaxWidth() {
        if (mDirection == DIRECTION_RIGHT || mDirection == DIRECTION_LEFT){
            return computeWidth() + ((mDotMaxRadius - mDotRadius) * 2);
        }
        return mDotMaxRadius * 2 + 2;
    }

    private float computeWidth() {
        return (((mDotRadius * 2) + mHorizontalSpacing) * mDrawables.size()) - mHorizontalSpacing + 2;
    }

    private void calculateMaxRadius() {
        mDotMaxRadius = mDotRadius * mDotScaleMultiplier;
    }

    private void calculateWidthBetweenDotCenters() {
        mWidthBetweenDotCenters = (int) (mDotRadius * 2) + (int) mHorizontalSpacing;
    }

    private void initDots() {
        mDrawables.clear();
        mAnimations.clear();
        for (int i = 1; i <= mNumberDots; i++) {
            final DialDotsDrawable dot = new DialDotsDrawable(mDotColor, mDotRadius, mDotMaxRadius);
            dot.setCallback(this);
            mDrawables.add(dot);

            ValueAnimator growAnimator =
                    ObjectAnimator.ofFloat(dot, "radius", mDotRadius, mDotMaxRadius, mDotRadius);
            growAnimator.setDuration(mAnimationDuration);
            growAnimator.setInterpolator(new AccelerateDecelerateInterpolator());



            if (mDirection == DIRECTION_LEFT || mDirection == DIRECTION_TOP){
                if (i == 1) {
                    growAnimator.addListener(
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (shouldAnimate()) {
                                        startAnimations();
                                    }
                                }
                            });
                }
                int delay = (mNumberDots - i ) * (int) (START_DELAY_FACTOR * mAnimationDuration);
                System.out.println("delay for dot i : " + delay);
                growAnimator.setStartDelay(delay);
            }
            else{
                if (i == mNumberDots) {
                    growAnimator.addListener(
                            new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    if (shouldAnimate()) {
                                        startAnimations();
                                    }
                                }
                            });
                }
                growAnimator.setStartDelay((i - 1) * (int) (START_DELAY_FACTOR * mAnimationDuration));
            }
            mAnimations.add(growAnimator);
        }
    }

    private void updateDots() {
        if (mDotRadius <= 0) {
            mDotRadius = getHeight() / 2 / mDotScaleMultiplier;
        }

        int left = (int) (mDotMaxRadius - mDotRadius);
        int right = (int) (left + mDotRadius * 2)+2;
        int top = 0;
        int bottom = (int) (mDotMaxRadius * 2);

        for (int i = 0; i < mDrawables.size(); i++) {
            final DialDotsDrawable dot = mDrawables.get(i);
            dot.setRadius(mDotRadius);
            dot.setBounds(left, top, right, bottom);
            ValueAnimator growAnimator = (ValueAnimator) mAnimations.get(i);
            growAnimator.setFloatValues(mDotRadius, mDotRadius * mDotScaleMultiplier, mDotRadius);

            if(mDirection == DIRECTION_BOTTOM || mDirection == DIRECTION_TOP){
                top += mWidthBetweenDotCenters;
                bottom += mWidthBetweenDotCenters;
            }
            else if (mDirection == DIRECTION_LEFT || mDirection == DIRECTION_RIGHT){
                left += mWidthBetweenDotCenters;
                right += mWidthBetweenDotCenters;
            }

        }
    }

    protected void startAnimations() {
        mShouldAnimate = true;
        for (Animator anim : mAnimations) {
            anim.start();
        }
    }

    protected void stopAnimations() {
        mShouldAnimate = false;
        removeCallbacks();
        for (Animator anim : mAnimations) {
            anim.cancel();
        }
    }

    protected boolean shouldAnimate() {
        return mShouldAnimate;
    }

    // -------------------------------
    // ------ Getters & Setters ------
    // -------------------------------

    public void setDotRadius(float radius) {
        reset();
        mDotRadius = radius;
        calculateMaxRadius();
        calculateWidthBetweenDotCenters();
        setupDots();
    }

    public void setDotSpacing(float horizontalSpacing) {
        reset();
        mHorizontalSpacing = horizontalSpacing;
        calculateWidthBetweenDotCenters();
        setupDots();
    }

    public void setGrowthSpeed(int growthSpeed) {
        reset();
        mAnimationDuration = growthSpeed;
        setupDots();
    }

    public void setNumberOfDots(int numDots) {
        reset();
        mNumberDots = numDots;
        setupDots();
    }

    public void setDotScaleMultpiplier(float multplier) {
        reset();
        mDotScaleMultiplier = multplier;
        calculateMaxRadius();
        setupDots();
    }

    public void setDotColor(int color) {
        mDotColor = color;
        for (DialDotsDrawable dot : mDrawables) {
            dot.setColor(mDotColor);
        }
    }

    private void setupDots(){
        initDots();
        updateDots();
        showNow();
    }

    public int getDotGrowthSpeed() {
        return mAnimationDuration;
    }

    public float getDotRadius() {
        return mDotRadius;
    }

    public float getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public int getNumberOfDots() {
        return mNumberDots;
    }

    public float getDotScaleMultiplier() {
        return mDotScaleMultiplier;
    }
}
