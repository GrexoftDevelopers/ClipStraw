package widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by tahir on 1/12/2016.
 */
public class IconEditText extends EditText {

    public static final int ICON_LEFT = 0;
    public static final int ICON_RIGHT = 1;

    Context mContext;
    private LayerDrawable mLayerDrawable;
    private int iconId;
    private int iconPosition;

    public IconEditText(Context context) {
        super(context);
        init(context, null);
    }

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IconEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.IconEditText);
        iconPosition = typedArray.getInt(R.styleable.IconEditText_icon_position, ICON_RIGHT);
        iconId = typedArray.getResourceId(R.styleable.IconEditText_icon_mipmap, R.mipmap.icon_place_holder);
        post(new Runnable() {
            @Override
            public void run() {
                setDrawable();
            }
        });
    }

    private void setDrawable(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(iconPosition == ICON_RIGHT){
                setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,iconId,0);
            }
            else if(iconPosition == ICON_LEFT){
                setCompoundDrawablesRelativeWithIntrinsicBounds(iconId,0,0,0);
            }
            setPadding(15,15,15,15);
            setCompoundDrawablePadding(10);
        }
    }


    public void setIconId(int iconId) {
        this.iconId = iconId;
        setDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        LayerDrawable layerDrawable = (LayerDrawable)getResources().getDrawable(R.drawable.icon_edittext_bg);
//
//        BitmapDrawable bitmapDrawable = (BitmapDrawable)layerDrawable.findDrawableByLayerId(R.id.et_ic);
//
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), iconId);
//        int pixels[] = new int[bmp.getWidth() * bmp.getHeight()];
//        bmp.getPixels(pixels,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
//
//        //bitmapDrawable.getBitmap().setPixels(pixels,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
//        System.out.println("bitmap is mutable : " + bitmapDrawable.getBitmap().isMutable());

    }
}
