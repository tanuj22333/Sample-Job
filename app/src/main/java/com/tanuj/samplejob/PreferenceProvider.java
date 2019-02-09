package com.tanuj.samplejob;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

class PreferenceProvider {

    private static final PreferenceProvider instance = new PreferenceProvider();
    private static final String PREFS_NAME = "Sample_Job_Preference";
    public static final String LAST_NOTIFICATION_ID = "last_notification_id";

    static PreferenceProvider getInstance() {
        return instance;
    }

    private PreferenceProvider() {
    }

    public void saveNextNotificationId(int id) {
        SharedPreferences.Editor editor = App.BASE_CONTEXT.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(LAST_NOTIFICATION_ID, id);
        editor.apply();
    }

    public int getNotificationId() {
        SharedPreferences prefs = App.BASE_CONTEXT.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(LAST_NOTIFICATION_ID, 1);
    }
}
