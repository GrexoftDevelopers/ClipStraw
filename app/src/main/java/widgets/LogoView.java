package widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.helper.CommonUtilities;

/**
 * Created by tahir on 1/28/2016.
 */
public class LogoView extends LinearLayout {

    private static final int DEFAULT_ICON_SRC = R.mipmap.ic_timeline_large_white;

    private static final String DEFAULT_LABEL = "Clipstraw";

    public static final byte ANIMATION_LEFT = 0;

    public static final byte ANIMATION_RIGHT = 1;

    private static final long ANIMATION_DURATION = 700;

    private static final int DEFAULT_LABEL_TEXT_SIZE = 12;

    private static final int DEFAULT_ICON_SIZE = 24;

    private static final int BOX_SIZE_LARGE = 0;
    private static  final int BOX_SIZE_SMALL = 1;

    Context context;
    private TextView txtLabel;
    private ImageView imgIcon;
    private RelativeLayout iconContainer;

    private String label;
    private int iconSrc;
    private int iconSize;
    private int labelTextSize;
    private int boxSize;

    private boolean isLabelPresent;

    public LogoView(Context context) {
        super(context);
        init(context,null);
    }

    public LogoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public LogoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.context = context;
        if (attrs == null){
            label = DEFAULT_LABEL;
            iconSrc = DEFAULT_ICON_SRC;
            iconSize = CommonUtilities.dpToPx(getContext(),DEFAULT_ICON_SIZE);
            labelTextSize = DEFAULT_LABEL_TEXT_SIZE;
            isLabelPresent = false;
            boxSize = BOX_SIZE_SMALL;
        }
        else{
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.LogoView);
            if(typedArray != null){
                label = typedArray.getString(R.styleable.LogoView_label);
                iconSrc = typedArray.getResourceId(R.styleable.LogoView_icon_src, DEFAULT_ICON_SRC);
                isLabelPresent = typedArray.getBoolean(R.styleable.LogoView_isLabelPresent, false);
                labelTextSize = (int) typedArray.getDimension(R.styleable.LogoView_label_text_size, DEFAULT_LABEL_TEXT_SIZE);
                iconSize = typedArray.getDimensionPixelSize(R.styleable.LogoView_icon_size,DEFAULT_ICON_SIZE);
                boxSize = typedArray.getInt(R.styleable.LogoView_box_size,BOX_SIZE_LARGE);
            }
        }

        inflate(context,R.layout.item_logo_view,this);

        txtLabel = (TextView)findViewById(R.id.txt_label);
        imgIcon = (ImageView)findViewById(R.id.img_icon);
        if(!isLabelPresent){
            txtLabel.setVisibility(View.GONE);
        }
        else{
            txtLabel.setText(label);
            //txtLabel.setTextSize(labelTextSize);
        }
        imgIcon.setImageResource(iconSrc);
        imgIcon.getLayoutParams().width = iconSize;
        imgIcon.getLayoutParams().height = iconSize;
        iconContainer = (RelativeLayout)findViewById(R.id.icon_container);
        if(boxSize == BOX_SIZE_LARGE){
            this.setBackgroundResource(R.mipmap.logo_box_flex);
        }
        else {
            this.setBackgroundResource(R.mipmap.logo_box_flex_small);
        }

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int oldWidth = oldRight - oldLeft;
                int oldHeight = oldBottom - oldTop;
                int newWidth = right - left;
                int newHeight = bottom - top;
                ScaleAnimation scaleAnimation = new ScaleAnimation(oldWidth,newWidth,oldHeight,newHeight,oldLeft + oldWidth/2,oldTop + oldHeight/2);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setDuration(ANIMATION_DURATION);
                //startAnimation(scaleAnimation);
            }
        });



    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        System.out.println("inside on layout of logo view");
        if(!isLabelPresent){
            ((RelativeLayout.LayoutParams)iconContainer.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        }System.out.println("padding left : " + getPaddingLeft());
        System.out.println("padding top : " + getPaddingTop());
        System.out.println("padding right : " + getPaddingRight());
        System.out.println("padding bottom : " + getPaddingBottom());
        System.out.println("width : " + getMeasuredWidth());
        System.out.println("height : " + getMeasuredHeight());
//        if(exceptionalPadding != DEFAULT_EXCEPTIONAL_PADDING){
//            float factor = DEFAULT_EXCEPTIONAL_PADDING / getPaddingRight();
//            //System.out.println("");
//            setPadding((int)(getPaddingLeft() * factor),(int)(getPaddingTop() * factor),(int)(getPaddingRight() * factor),(int)(getPaddingBottom() * factor));
//        }
        iconContainer.getLayoutParams().width = imgIcon.getMeasuredWidth() * 2;
        if(isLabelPresent){
            iconContainer.getLayoutParams().height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - txtLabel.getMeasuredHeight();
        }
        else{
            iconContainer.getLayoutParams().height = imgIcon.getMeasuredHeight() * 2;
        }


        //setPadding(0,0,0,0);
        //iconContainer.getLayoutParams().width = txtLabel.getMeasuredWidth();
        //int difference = iconContainer.getLayoutParams().height - txtLabel.getLayoutParams().width;
        //iconContainer.getLayoutParams().height = iconContainer.getLayoutParams().width;
        //getLayoutParams().height = getLayoutParams().height - difference;
//        int paddingLeft = (int) (getWidth()*0.2);
//        int padding = (int) (getWidth() * 0.1);
//        setPadding(paddingLeft,padding,padding,padding);
//        imgIcon.getLayoutParams().height = (int) (getWidth() * 0.3);
//        imgIcon.getLayoutParams().width = (int) (getWidth() * 0.3);
        //imgIcon.setLayoutParams(new RelativeLayout.LayoutParams((int) (getWidth() * 0.3), (int) (getWidth() * 0.3)));
        //imgIcon.setTop(iconContainer.getWidth() - imgIcon.getWidth() / 2);
        //imgIcon.setLeft(iconContainer.getHeight() - imgIcon.getHeight() / 2);
        //System.out.println("top be :" + (iconContainer.getHeight() - imgIcon.getHeight() / 2));
        //System.out.println("left be :" + (iconContainer.getWidth() - imgIcon.getWidth() / 2));
        //imgIcon.setBackgroundColor(Color.BLUE);
        //Toast.makeText(context,"onLayout",Toast.LENGTH_SHORT).show();
    }

    public void setIconSrc(int iconSrc) {
        this.iconSrc = iconSrc;
        this.imgIcon.setImageResource(iconSrc);
    }

    public void setIconSrc(final int iconSrc, byte animationDirection){
        final ImageView imgIconNew = new ImageView(context);
        imgIconNew.setImageResource(iconSrc);
        //imgIconNew.setY(imgIcon.getY());
        //imgIconNew.setX(imgIcon.getX());
        imgIconNew.setLayoutParams(imgIcon.getLayoutParams());
        //imgIconNew.setVisibility(INVISIBLE);
        iconContainer.addView(imgIconNew);

//        iconContainer.removeView(imgIcon);
//        LogoView.this.iconSrc = iconSrc;
//        LogoView.this.imgIcon = imgIconNew;

        TranslateAnimation translateAnimation = null;
        if(animationDirection == ANIMATION_LEFT){
           translateAnimation = new TranslateAnimation(0,-300,0,0);
        }
        else {
            translateAnimation = new TranslateAnimation(0,300,0,0);
        }
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setDuration(ANIMATION_DURATION);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iconContainer.removeView(imgIcon);
                LogoView.this.iconSrc = iconSrc;
                LogoView.this.imgIcon = imgIconNew;
                //imgIconNew.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgIcon.startAnimation(translateAnimation);

        if (animationDirection == ANIMATION_LEFT){
            translateAnimation = new TranslateAnimation(300,0,0,0);
        }
        else{
            translateAnimation = new TranslateAnimation(-300,0,0,0);
        }

        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setDuration(ANIMATION_DURATION);
        imgIconNew.startAnimation(translateAnimation);
    }

    public void setLabel(String label) {
        this.label = label;
        this.txtLabel.setText(label);
    }
}
