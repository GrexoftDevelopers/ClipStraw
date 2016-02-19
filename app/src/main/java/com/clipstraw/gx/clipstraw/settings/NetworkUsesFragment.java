package com.clipstraw.gx.clipstraw.settings;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;

/**
 * Created by Faizzy on 20-01-2016.
 */
public class NetworkUsesFragment extends SettingsFragment {

    public NetworkUsesFragment() {
        super("Network Usage", R.mipmap.ic_network_usage_small_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.network_usage_page;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void save() {

    }
}
