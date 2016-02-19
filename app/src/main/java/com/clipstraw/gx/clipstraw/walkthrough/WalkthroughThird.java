package com.clipstraw.gx.clipstraw.walkthrough;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by FaizZy on 06-01-2016.
 */
public class WalkthroughThird extends Fragment {


    View fragmentView;
    LinearLayout viewPagerContent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView=inflater.inflate(R.layout.view_pager3,container,false);
        //viewPagerContent=(LinearLayout)fragmentView.findViewById(R.id.view_pager_content);

        //startEnterAnimation();

        return  fragmentView;
    }

//    public void startEnterAnimation(){
//        AlphaAnimation alphaAnimation=new AlphaAnimation(0.2f,1.0f);
//        alphaAnimation.setDuration(1000);
//        alphaAnimation.setFillAfter(true);
//        alphaAnimation.setStartOffset(200);
//        viewPagerContent.startAnimation(alphaAnimation);
//    }


    }

