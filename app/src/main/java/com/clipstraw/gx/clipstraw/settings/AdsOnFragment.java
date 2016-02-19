package com.clipstraw.gx.clipstraw.settings;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;

/**
 * Created by Faizzy on 20-01-2016.
 */
public class AdsOnFragment extends SettingsFragment {

    public AdsOnFragment(){
        super("Ads",R.mipmap.ic_ads_samll_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ads_page;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void save() {

    }
}
