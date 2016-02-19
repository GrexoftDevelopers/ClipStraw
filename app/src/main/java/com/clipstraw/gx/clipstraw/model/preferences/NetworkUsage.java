package com.clipstraw.gx.clipstraw.model.preferences;

/**
 * Created by Rehan on 15-02-2016.
 */
public class NetworkUsage extends Preferences {

    public static final String TOTAL_MEDIA_USAGE = "total_media_usage";

    public static final String TOTAL_MESSAGE_SENT = "total_message_sent";

    public static final String TOTAL_MESSAGE_RECEIVED = "total_message_received";

    public static final String TOTAL_OUTGOING_CALLS = "total_outgoing_calls";

    public static final String TOTAL_INCOMING_CALLS = "total_incoming_calls";

    public static final String TOTAL_MEDIA_SHARED = "total_media_shared";

    public static final String TOTAL_MEDIA_RECEIVED = "total_media_received";

    private long totalMediaUsage;

    private int totalMessageSent;

    private int totalMessageReceived;

    private int totalOutgoingCalls;

    private int totalIncomingCalls;

    private int totalMedaiShared;

    private int totalMediaReceived;

    public NetworkUsage() {
        super();
        this.totalMediaUsage = sharedPreferences.getLong(TOTAL_MEDIA_USAGE, 0);
        this.totalMessageSent = sharedPreferences.getInt(TOTAL_MESSAGE_SENT, 0);
        this.totalMessageReceived = sharedPreferences.getInt(TOTAL_MESSAGE_RECEIVED, 0);
        this.totalOutgoingCalls = sharedPreferences.getInt(TOTAL_OUTGOING_CALLS, 0);
        this.totalIncomingCalls = sharedPreferences.getInt(TOTAL_INCOMING_CALLS, 0);
        this.totalMedaiShared = sharedPreferences.getInt(TOTAL_MEDIA_SHARED, 0);
        this.totalMediaReceived = sharedPreferences.getInt(TOTAL_MEDIA_RECEIVED, 0);
    }

    public long getTotalMediaUsage() {
        return totalMediaUsage;
    }

    public int getTotalMessageSent() {
        return totalMessageSent;
    }

    public int getTotalMessageReceived() {
        return totalMessageReceived;
    }

    public int getTotalOutgoingCalls() {
        return totalOutgoingCalls;
    }

    public int getTotalIncomingCalls() {
        return totalIncomingCalls;
    }

    public int getTotalMedaiShared() {
        return totalMedaiShared;
    }

    public int getTotalMediaReceived() {
        return totalMediaReceived;
    }

    @Override
    protected void save() {

        editor.putFloat(TOTAL_MEDIA_USAGE, totalMediaUsage);
        editor.putInt(TOTAL_MESSAGE_SENT, totalMessageSent);
        editor.putInt(TOTAL_MESSAGE_RECEIVED, totalMessageReceived);
        editor.putInt(TOTAL_OUTGOING_CALLS, totalOutgoingCalls);
        editor.putInt(TOTAL_INCOMING_CALLS, totalIncomingCalls);
        editor.putInt(TOTAL_MEDIA_SHARED, totalMedaiShared);
        editor.putInt(TOTAL_MEDIA_RECEIVED, totalMediaReceived);
        editor.commit();

    }
}
