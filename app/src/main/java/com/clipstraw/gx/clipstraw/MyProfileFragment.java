package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import widgets.CircularImageView;

/**
 * Created by Faizzy on 19-01-2016.
 */


public class MyProfileFragment extends Fragment {
    private View fragmentView;
    CircularImageView imgProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.my_profile_page, container, false);

        imgProfile = (CircularImageView) fragmentView.findViewById(R.id.user_img_circle);

        String url = "https://avatars2.githubusercontent.com/u/16339484?v=3&s=460";

        return fragmentView;
        }
        }
