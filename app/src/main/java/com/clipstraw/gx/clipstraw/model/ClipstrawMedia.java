package com.clipstraw.gx.clipstraw.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.clipstraw.gx.clipstraw.ClipstrawApplication;
import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.request.RequestManager;

/**
 * Created by tahir on 1/30/2016.
 */
public class ClipstrawMedia {

    public static final byte MEDIA_IMAGE = 0;

    public static final byte MEDIA_VIDEO = 1;

    private byte mediaType;

    private String mediaUrl;

    private String extension;

    private String cacheFilePath;

    private boolean isCached;

    private String caption;

    private ImageRequest imgRequest;

    public ClipstrawMedia(byte mediaType, String mediaUrl) {
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
    }

    public void setWindow(final ImageView imageView){

        imgRequest = new ImageRequest(mediaUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setBackgroundResource(R.drawable.circle);
                error.printStackTrace();
            }
        });
        RequestManager.getRequestManagerInstance(ClipstrawApplication.getInstance().getApplicationContext()).addToRequestQueue(imgRequest);

    }

}
