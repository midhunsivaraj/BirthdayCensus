package com.test.mateflick.utils.preference;

/**
 * Interface containing only the constants used in the {@link PreferenceManager} class.
 * Implement this interface to avoid ugly Static imports
 */
public interface IPreference {
    String PREF_KEY = "birthdayKey";
    String KEY_LOGGED_IN = "loggedIn";
    String KEY_USER_ID = "userId";
    String KEY_EMAIL = "email";
    String KEY_USER_NAME = "userName";
    String KEY_COUNTRY = "country";
    String KEY_BIRTHDAY="brthday";
    String KEY_LAST_KNOWN_LATITUDE = "lastKnownLatitude";
    String KEY_LAST_KNOWN_LONGITUDE = "lastKnownLongitude";
    String KEY_ABOUT = "about";
    String KEY_COVER_NAME ="coverName";
    String KEY_PROFILE_IMAGE="profileImage";
    String KEY_DOB ="dob";
}
