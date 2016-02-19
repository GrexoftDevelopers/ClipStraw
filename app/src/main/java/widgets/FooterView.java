package widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.HomeActivity;
import com.clipstraw.gx.clipstraw.PopularActivity;
import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.helper.CommonUtilities;

/**
 * Created by tahir on 1/31/2016.
 */
public class FooterView extends RelativeLayout implements View.OnClickListener{

    private static final long ANIMATION_DURATION = 500;

    private View btnHome, btnMostPopularTimenine, btnMostLikedEvenet, btnMostPopularFriendEvent, btnDrag;

    FooterListener footerListener;

    private boolean shown;

    public FooterView(Context context) {
        super(context);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        inflate(context, R.layout.footer,this);
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(this);
        btnMostLikedEvenet = findViewById(R.id.btn_most_liked_events);
        btnMostLikedEvenet.setOnClickListener(this);
        btnMostPopularFriendEvent = findViewById(R.id.btn_most_popular_friend_events);
        btnMostPopularFriendEvent.setOnClickListener(this);
        btnMostPopularTimenine = findViewById(R.id.btn_most_popular_timeline);
        btnMostPopularTimenine.setOnClickListener(this);
        btnDrag = findViewById(R.id.btn_drag);
        btnDrag.setOnClickListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        DisplayMetrics dm = new DisplayMetrics();
//        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
//        setY(dm.heightPixels - ((ViewGroup) btnDrag.getParent()).getMeasuredHeight());
//        Toast.makeText(getContext(),"onlayout footer",Toast.LENGTH_SHORT).show();
    }

    public void setFooterListener(FooterListener footerListener) {
        this.footerListener = footerListener;
    }

    private void show(){
        RotateAnimation rotateAnimation = new RotateAnimation(0,180,btnDrag.getMeasuredWidth()/2,btnDrag.getMeasuredHeight()/2);
        rotateAnimation.setDuration(ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);
        btnDrag.startAnimation(rotateAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,-btnHome.getMeasuredHeight());
        translateAnimation.setDuration(ANIMATION_DURATION);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                setY(getY() - btnHome.getMeasuredHeight());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(translateAnimation);
        shown = true;
    }
    private void hide(){
        RotateAnimation rotateAnimation = new RotateAnimation(-180,0,btnDrag.getMeasuredWidth()/2,btnDrag.getMeasuredHeight()/2);
        rotateAnimation.setDuration(ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);
        btnDrag.startAnimation(rotateAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0,0,0,btnHome.getMeasuredHeight());
        translateAnimation.setDuration(ANIMATION_DURATION);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                setY(getY() + btnHome.getMeasuredHeight());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(translateAnimation);
        shown = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnDrag.getId()){
            if(shown) hide();
            else show();
            return;
        }
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_home:
                intent.setClass(getContext(), HomeActivity.class);
                break;
            case R.id.btn_most_liked_events:
                intent.setClass(getContext(), PopularActivity.class);
                intent.putExtra("fragment_id",PopularActivity.MOST_POPULAR_EVENT);
                break;
            case R.id.btn_most_popular_friend_events:
                intent.setClass(getContext(), PopularActivity.class);
                intent.putExtra("fragment_id", PopularActivity.MOST_POPULAR_FRIEND_EVENT);
                break;
            case R.id.btn_most_popular_timeline:
                intent.setClass(getContext(), PopularActivity.class);
                intent.putExtra("fragment_id",PopularActivity.MOST_POPULAR_TIMELINE);
                break;
        }
        if(footerListener != null){
            footerListener.onStartActivityCommand(intent);
        }
    }

    public interface FooterListener{

        public void onStartActivityCommand(Intent intent);
    }


}
