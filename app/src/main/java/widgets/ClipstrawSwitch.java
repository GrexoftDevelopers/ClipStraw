package widgets;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by tahir on 1/18/2016.
 */
public class ClipstrawSwitch extends SwitchCompat {

    public ClipstrawSwitch(Context context) {
        super(context);
        init();
    }

    public ClipstrawSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipstrawSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setTrackResource(R.drawable.track_bg);
        setThumbResource(R.drawable.switch_bg);
    }
}
