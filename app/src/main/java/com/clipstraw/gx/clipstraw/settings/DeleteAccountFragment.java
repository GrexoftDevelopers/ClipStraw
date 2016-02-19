package com.clipstraw.gx.clipstraw.settings;

import com.clipstraw.gx.clipstraw.R;
import com.clipstraw.gx.clipstraw.SettingsFragment;

/**
 * Created by Faizzy on 20-01-2016.
 */
public class DeleteAccountFragment extends SettingsFragment {

    public DeleteAccountFragment(){
        super("Delete Account",R.mipmap.ic_delete_small_white);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.delete_account_page;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void save() {

    }
}
