package com.clipstraw.gx.clipstraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clipstraw.gx.clipstraw.R;

/**
 * Created by tahir on 2/5/2016.
 */
public abstract class SettingsFragment extends Fragment {

    protected int mIconId;

    protected String mTitle;

    protected View fragmentView;

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void save();

    public SettingsFragment(String mTitle, int mIconId){
        this.mTitle = mTitle;
        this.mIconId = mIconId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        fragmentView = inflater.inflate(layoutId,container,false);
        System.out.println("fragmentView : " + fragmentView);
        System.out.println("layout id : " + getLayoutId());
        init();
        return fragmentView;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIconId() {
        return mIconId;
    }
}
