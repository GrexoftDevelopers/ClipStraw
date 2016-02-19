package com.clipstraw.gx.clipstraw.model.preferences;

/**
 * Created by Rehan on 15-02-2016.
 */
public class Ads extends Preferences {

    private static final String DISABLE_ADS_KEY = "disable_ads";

    private final static String BLOCK_VIDEO_SHARE_KEY = "block_video_share";

    private boolean disableAds;

    private boolean blockVideoShare;

    public Ads() {
        super();
        this.disableAds = sharedPreferences.getBoolean(DISABLE_ADS_KEY,false);
        this.blockVideoShare = sharedPreferences.getBoolean(BLOCK_VIDEO_SHARE_KEY,false);
    }

    public boolean isDisableAds() {
        return disableAds;
    }

    public boolean isBlockVideoShare() {
        return blockVideoShare;
    }

    @Override
    protected void save() {
        editor.putBoolean(DISABLE_ADS_KEY, disableAds);
        editor.putBoolean(BLOCK_VIDEO_SHARE_KEY,blockVideoShare);
        editor.commit();
    }
}
