package com.clipstraw.gx.clipstraw.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Faizzy on 16-02-2016.
 */

public class RequestManager {
    private static RequestManager mRequestManagerInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;

    public RequestManager(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);

            }
        });
    }

    public static synchronized RequestManager getRequestManagerInstance(Context mContext) {

        if (mRequestManagerInstance == null) {
            mRequestManagerInstance = new RequestManager(mContext);
        }
        return mRequestManagerInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue( com.android.volley.Request<T> addTRequest){

        getRequestQueue().add(addTRequest);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
