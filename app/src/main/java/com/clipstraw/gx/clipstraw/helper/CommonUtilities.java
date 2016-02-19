package com.clipstraw.gx.clipstraw.helper;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by tahir on 2/1/2016.
 */
public class CommonUtilities {

    public static int dpToPx(Context context,int dp){
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int)px;
    }

    public static float spToPixel(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }
}
