package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Faizzy on 19-01-2016.
 */
public class ActivityFragment extends Fragment {

    private  View fragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.friends_followers_activity_page,container,false);

        return fragmentView;
    }
}
