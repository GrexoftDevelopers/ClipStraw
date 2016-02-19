package com.clipstraw.gx.clipstraw.model.preferences;

/**
 * Created by Rehan on 15-02-2016.
 */
public class PrivacyPolicy extends Preferences {

    public static final String LAST_SEEN_ONLINE = "last_seen";

    public static final String PROFILE_PHOTO_SEEN = "profile_photo";

    public static final String PROFILE_SEEN = "profile";

    public static final String STATUS_SEEN = "status";

    public static final String CHAT_AND_CALLS_BLOCK_LIST = "chat_and_calls";

    public static final String EVENT_DAY_BLOCK_LIST = "event_day";

    public static final String SHARING_DATA = "sharing";

    public static final String READ_RECEIPT = "read_receipt";

    private String lastSeen;

    private String profilePhoto;

    private String profile;

    private String status;

    private String chatAndCall;

    private String eventDay;

    private String sharing;

    private String readReceipt;

    public PrivacyPolicy() {
        super();
        this.lastSeen = sharedPreferences.getString(LAST_SEEN_ONLINE, null);
        this.profilePhoto = sharedPreferences.getString(PROFILE_PHOTO_SEEN, null);
        this.profile = sharedPreferences.getString(PROFILE_SEEN, null);
        this.status = sharedPreferences.getString(STATUS_SEEN, null);
        this.chatAndCall = sharedPreferences.getString(CHAT_AND_CALLS_BLOCK_LIST, null);
        this.eventDay = sharedPreferences.getString(EVENT_DAY_BLOCK_LIST, null);
        this.sharing = sharedPreferences.getString(SHARING_DATA, null);
        this.readReceipt = sharedPreferences.getString(READ_RECEIPT, null);
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getProfile() {
        return profile;
    }

    public String getStatus() {
        return status;
    }

    public String getChatAndCall() {
        return chatAndCall;
    }

    public String getEventDay() {
        return eventDay;
    }

    public String getSharing() {
        return sharing;
    }

    public String getReadReceipt() {
        return readReceipt;
    }

    @Override
    protected void save() {

        editor.putString(LAST_SEEN_ONLINE, lastSeen);
        editor.putString(PROFILE_PHOTO_SEEN, profilePhoto);
        editor.putString(PROFILE_SEEN, profile);
        editor.putString(STATUS_SEEN, status);
        editor.putString(CHAT_AND_CALLS_BLOCK_LIST, chatAndCall);
        editor.putString(EVENT_DAY_BLOCK_LIST, eventDay);
        editor.putString(SHARING_DATA, sharing);
        editor.putString(READ_RECEIPT, readReceipt);
        editor.commit();

    }
}
