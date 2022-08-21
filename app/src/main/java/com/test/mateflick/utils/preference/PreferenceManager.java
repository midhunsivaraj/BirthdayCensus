package com.test.mateflick.utils.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Handles all the {@link android.preference.Preference} related functions
 */
public abstract class PreferenceManager implements IPreference {

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context)
                .getBoolean(key, defaultValue);
    }


    public static void saveBooleanPreference(Context context, String key, boolean value) {
        getSharedPreferences(context)
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    public static void saveStringPreference(Context context, String key, String value) {
        getSharedPreferences(context)
                .edit()
                .putString(key, value)
                .commit();
    }

    public static String getStringPreference(Context context, String key, String defaultValueIfFailed) {
        return getSharedPreferences(context)
                .getString(key, defaultValueIfFailed);
    }



}
