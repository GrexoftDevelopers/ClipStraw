package com.clipstraw.gx.clipstraw.walkthrough;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by FaizZy on 06-01-2016.
 */
public class WalkthroughSecond extends Fragment {


    View fragmentView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView =inflater.inflate(R.layout.view_pager2,container,false);


        return fragmentView;
    }
}
