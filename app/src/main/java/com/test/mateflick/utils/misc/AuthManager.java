package com.test.mateflick.utils.misc;

import android.content.Context;

import com.test.mateflick.utils.network.request.response.LoginResponse;
import com.test.mateflick.utils.preference.PreferenceManager;

import static com.test.mateflick.utils.preference.IPreference.*;

/**
 * Created by Xtron005 on 04-07-2016.
 */
public class AuthManager {
    private static AuthManager ourInstance = new AuthManager();

    public static AuthManager getInstance() {
        return ourInstance;
    }

    private AuthManager() {
    }


    public void setUserLoggedIn(Context context, LoginResponse loginResponse) {
        PreferenceManager.saveBooleanPreference(context, KEY_LOGGED_IN, true);
        PreferenceManager.saveStringPreference(context, KEY_USER_ID, loginResponse.getId());
        PreferenceManager.saveStringPreference(context, KEY_EMAIL, loginResponse.getEmail());
        PreferenceManager.saveStringPreference(context, KEY_USER_NAME, loginResponse.getName());
        PreferenceManager.saveStringPreference(context, KEY_COUNTRY, loginResponse.getCountry());
        PreferenceManager.saveStringPreference(context, KEY_BIRTHDAY, loginResponse.getDob());
    }

    public void setUserLoggedOut(Context context) {
        PreferenceManager.saveBooleanPreference(context, KEY_LOGGED_IN, false);
    }

}
