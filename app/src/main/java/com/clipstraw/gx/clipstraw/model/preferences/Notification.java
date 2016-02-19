package com.clipstraw.gx.clipstraw.model.preferences;

/**
 * Created by Rehan on 15-02-2016.
 */
public class Notification extends Preferences {

    public static final String CONVERSATION_TONS_KEY = "conversation_tons";

    public static final String VIBRATE_KEY = "vibrate";

    public static final String CHAT_AND_CALLS_KEY = "chat_and_calls";

    public static final String TEXT_SIZE = "text_size";

    public static final String GENDER_SPINNER = "gender";

    public static final String PROMOTION_SPINNER = "promotion";

    public static final String BIRTHDAY_ALERT_SPINNER = "birthday_alert";

    private boolean conversationTons;

    private boolean vibrate;

    private boolean chatAndCall;

    private int textSize;

    private String gender;

    private String promotion;

    private String birthdayAlert;

    public Notification() {
        super();
        this.conversationTons = sharedPreferences.getBoolean(CONVERSATION_TONS_KEY, false);
        this.vibrate = sharedPreferences.getBoolean(VIBRATE_KEY, false);
        this.chatAndCall = sharedPreferences.getBoolean(CHAT_AND_CALLS_KEY, false);
        this.textSize = sharedPreferences.getInt(TEXT_SIZE, 0);
        this.gender = sharedPreferences.getString(GENDER_SPINNER, null);
        this.promotion = sharedPreferences.getString(PROMOTION_SPINNER, null);
        this.birthdayAlert = sharedPreferences.getString(BIRTHDAY_ALERT_SPINNER, null);
    }

    public boolean isConversationTons() {
        return conversationTons;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public boolean isChatAndCall() {
        return chatAndCall;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getGender() {
        return gender;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getBirthdayAlert() {
        return birthdayAlert;
    }

    @Override
    protected void save() {

        editor.putBoolean(CONVERSATION_TONS_KEY, conversationTons);
        editor.putBoolean(VIBRATE_KEY, vibrate);
        editor.putBoolean(CHAT_AND_CALLS_KEY, chatAndCall);
        editor.putInt(TEXT_SIZE, textSize);
        editor.putString(GENDER_SPINNER, gender);
        editor.putString(PROMOTION_SPINNER, promotion);
        editor.putString(BIRTHDAY_ALERT_SPINNER, birthdayAlert);
        editor.commit();

    }
}
