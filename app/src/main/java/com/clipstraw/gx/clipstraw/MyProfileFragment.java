package com.clipstraw.gx.clipstraw;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.clipstraw.gx.clipstraw.request.RequestManager;

import widgets.CircularImageView;

/**
 * Created by Faizzy on 19-01-2016.
 */


public class MyProfileFragment extends Fragment {
    private View fragmentView;
    CircularImageView imgProfile;
    private ImageRequest imgRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.my_profile_page, container, false);

        imgProfile = (CircularImageView) fragmentView.findViewById(R.id.user_img_circle);

        String url = "https://avatars2.githubusercontent.com/u/16339484?v=3&s=460";
         imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgProfile.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               imgProfile.setBackgroundResource(R.drawable.circle);
                error.printStackTrace();
            }
        });

        RequestManager.getRequestManagerInstance(getActivity()).addToRequestQueue(imgRequest);


        return fragmentView;
        }
        }
