package widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by tahir on 1/30/2016.
 */
public class IconTextView extends TextView{

    public static final int ICON_LEFT = 0;
    public static final int ICON_RIGHT = 1;

    public static final int NO_MIPMAP = -1;

    Context mContext;
    private LayerDrawable mLayerDrawable;
    private int iconId;
    private int iconPosition;
    private int iconSize;

    public IconTextView(Context context) {
        super(context);
        this.mContext = context;
        this.iconId = NO_MIPMAP;
    }

    public IconTextView(Context context, int iconId, int iconPosition, int iconSize){
        super(context);
        this.mContext = context;
        this.iconSize = iconSize;
        this.iconPosition = iconPosition;
        this.iconId = iconId;
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        if (attrs != null){
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.IconTextView);
            iconId = typedArray.getResourceId(R.styleable.IconTextView_icon_mipmap_txt, NO_MIPMAP);
            iconPosition = typedArray.getInt(R.styleable.IconTextView_icon_position_txt, ICON_LEFT);
            iconSize = typedArray.getDimensionPixelSize(R.styleable.IconTextView_icon_size_txt,0);
        }
        adjustLayout();
    }

    private void adjustLayout(){
        if(iconId != NO_MIPMAP){
            Drawable drawable = getResources().getDrawable(iconId);
            float scaleWidth = (float)iconSize / (float)drawable.getIntrinsicWidth();
            float scaleHeight = (float)iconSize / (float)drawable.getIntrinsicHeight();
            drawable = scaleImage(drawable,scaleWidth);
            if(iconPosition == ICON_LEFT){
                setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            }
            else{
                setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
            }
        }
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
        invalidate();
    }

    public int getIconPosition() {
        return iconPosition;
    }

    public void setIconPosition(int iconPosition) {
        this.iconPosition = iconPosition;
        invalidate();
    }

    public int getIconSize() {
        return iconSize;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        if(iconId != NO_MIPMAP){
//            Drawable drawable = getResources().getDrawable(iconId);
//            float scaleWidth = (float)iconSize / (float)drawable.getIntrinsicWidth();
//            float scaleHeight = (float)iconSize / (float)drawable.getIntrinsicHeight();
//            drawable = scaleImage(drawable,scaleWidth);
//            if(iconPosition == ICON_LEFT){
//                setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
//            }
//            else{
//                setCompoundDrawablesWithIntrinsicBounds(null,null,drawable,null);
//            }
//        }
        adjustLayout();
    }

    private Drawable scaleImage (Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable)image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getResources(), bitmapResized);

        return image;

    }
}
