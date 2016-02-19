package widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.helper.CommonUtilities;
import com.clipstraw.gx.clipstraw.timeline.TimelinePage;

/**
 * Created by tahir on 2/9/2016.
 */
public class EventBox extends LinearLayout {

    public static final int EVENT_BOX_GREY = 0;

    public static final int EVENT_BOX_RED = 1;

    private int boxColor;

    private int direction;

    private String mTitle, mDate;

    private TextView txtDate, txtTitle;

    public EventBox(Context context, String date, String title, int direction, int boxColor) {
        super(context);
        this.boxColor = boxColor;
        this.direction = direction;
        this.mTitle = title;
        this.mDate = date;
        init(null);
    }

    public EventBox(Context context, String date, String title) {
        super(context);
        this.boxColor = EVENT_BOX_RED;
        this.direction = TimelinePage.DIRECTION_LEFT;
        this.mTitle = title;
        this.mDate = date;
        init(null);
    }

    public EventBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EventBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EventBox);
            direction = typedArray.getInt(R.styleable.EventBox_eventbox_direction, TimelinePage.DIRECTION_LEFT);
            boxColor = typedArray.getInt(R.styleable.EventBox_eventbox_color, EVENT_BOX_RED);
            mDate = typedArray.getString(R.styleable.EventBox_eventbox_date);
            mTitle = typedArray.getString(R.styleable.EventBox_eventbox_title);
            typedArray.recycle();
        }

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBoxBg();

        inflateContents();

    }

    public String getDate() {
        return txtDate.getText().toString();
    }

    public String getTitle() {
        return txtTitle.getText().toString();
    }

    public void setDate(String date) {
        this.mDate = date;
        this.txtDate.setText(date);
    }

    public int getBoxColor() {
        return boxColor;
    }

    public void setBoxColor(int boxColor) {
        this.boxColor = boxColor;
        setBoxBg();
    }

    private void setBoxBg() {
        if (boxColor == EVENT_BOX_GREY) {
            if (direction == TimelinePage.DIRECTION_LEFT)
                setBackgroundResource(R.mipmap.logo_box_grey_flex);
            else if (direction == TimelinePage.DIRECTION_RIGHT)
                setBackgroundResource(R.mipmap.logo_box_grey_right_flex);
        } else if (boxColor == EVENT_BOX_RED) {
            if (direction == TimelinePage.DIRECTION_LEFT)
                setBackgroundResource(R.mipmap.logo_box_flex);
            else if (direction == TimelinePage.DIRECTION_RIGHT)
                setBackgroundResource(R.mipmap.logo_box_right_flex);
        }
    }

    public void setTitle(String title) {
        this.mTitle = title;
        this.txtTitle.setText(title);
    }

    private void inflateContents() {

        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(CommonUtilities.dpToPx(getContext(), 40), ViewGroup.LayoutParams.WRAP_CONTENT);

        txtDate = new TextView(getContext());
        txtDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        txtDate.setTextColor(Color.WHITE);
        txtDate.setAllCaps(true);
        txtDate.setGravity(Gravity.CENTER_HORIZONTAL);
        txtDate.setText(mDate);
        txtDate.setLayoutParams(txtParams);

        txtTitle = new TextView(getContext());
        txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
        txtTitle.setTextColor(Color.WHITE);
        txtTitle.setAllCaps(true);
        txtTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        txtTitle.setText(mTitle);
        txtTitle.setLayoutParams(txtParams);

        View seperator = new View(getContext());
        seperator.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams seperatorParams = new LinearLayout.LayoutParams(CommonUtilities.dpToPx(getContext(), 1), CommonUtilities.dpToPx(getContext(), 45));
        seperatorParams.setMargins(0, CommonUtilities.dpToPx(getContext(), 5), 0, CommonUtilities.dpToPx(getContext(), 5));
        seperator.setLayoutParams(seperatorParams);

        if (direction == TimelinePage.DIRECTION_LEFT) {
            addView(txtDate);
            addView(seperator);
            addView(txtTitle);
        } else if (direction == TimelinePage.DIRECTION_RIGHT) {
            addView(txtTitle);
            addView(seperator);
            addView(txtDate);
        }


//        if (direction == TimelinePage.DIRECTION_LEFT){
//            inflate(getContext(),R.layout.eventbox_left,this);
//        }
//        else if(direction == TimelinePage.DIRECTION_RIGHT){
//            inflate(getContext(),R.layout.eventbox_right,this);
//        }
//
//        txtDate = (TextView)findViewById(R.id.txt_date);
//        txtDate.setText(mDate);
//
//        txtTitle = (TextView)findViewById(R.id.txt_title);
//        txtTitle.setText(mTitle);

    }
}
