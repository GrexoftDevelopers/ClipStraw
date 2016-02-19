package com.clipstraw.gx.clipstraw.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;

/**
 * Created by Faizzy on 20-01-2016.
 */
public class PrivacyPolicyFragment extends SettingsFragment {


    public PrivacyPolicyFragment(){
        super("Privacy",R.mipmap.ic_lock_small_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.privacy_policy_page;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void save() {

    }
}
