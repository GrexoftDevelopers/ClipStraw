package com.clipstraw.gx.clipstraw.model.preferences;

/**
 * Created by Rehan on 15-02-2016.
 */
public class EditProfile extends Preferences {

    public static final String SOUND_KEY ="sound";

    public static final String TIMER_COUNT_DOWN_KEY = "timer_count_down";

    public static final String CAMERA_SETTING_KEY = "camera_setting";

    public static final String IMAGE_PROCESSING_KEY = "image_processing";

    public static final String PRELOAD_VIDEOS_KES = "preload_videos";

    public static final String SLOW_MOTION_KEY = "slow_motion";

    public static final String ZOOM_KEY = "zoom";

    private boolean sound;

    private boolean timerCountdown;

    private boolean cameraSetting;

    private boolean imageProcessing;

    private boolean preloadVideos;

    private boolean slowMotion;

    private int zoom;

    public EditProfile() {
        super();
        this.sound = sharedPreferences.getBoolean(SOUND_KEY, false);
        this.timerCountdown = sharedPreferences.getBoolean(TIMER_COUNT_DOWN_KEY, false);
        this.cameraSetting = sharedPreferences.getBoolean(CAMERA_SETTING_KEY, false);
        this.imageProcessing = sharedPreferences.getBoolean(IMAGE_PROCESSING_KEY, false);
        this.preloadVideos = sharedPreferences.getBoolean(PRELOAD_VIDEOS_KES, false);
        this.slowMotion = sharedPreferences.getBoolean(SLOW_MOTION_KEY, false);
        this.zoom = sharedPreferences.getInt(ZOOM_KEY, 0);
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isTimerCountdown() {
        return timerCountdown;
    }

    public boolean isCameraSetting() {
        return cameraSetting;
    }

    public boolean isImageProcessing() {
        return imageProcessing;
    }

    public boolean isPreloadVideos() {
        return preloadVideos;
    }

    public boolean isSlowMotion() {
        return slowMotion;
    }

    public int getZoom() {
        return zoom;
    }

    @Override
    protected void save() {

        editor.putBoolean(SOUND_KEY, sound);
        editor.putBoolean(TIMER_COUNT_DOWN_KEY, timerCountdown);
        editor.putBoolean(CAMERA_SETTING_KEY, cameraSetting);
        editor.putBoolean(IMAGE_PROCESSING_KEY, imageProcessing);
        editor.putBoolean(PRELOAD_VIDEOS_KES, preloadVideos);
        editor.putBoolean(SLOW_MOTION_KEY, slowMotion);
        editor.putInt(ZOOM_KEY, zoom);
        editor.commit();

    }
}
