package fr.oc.multilingua.multilingua.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferencesManager {

    private static UserPreferencesManager mInstance;
    private SharedPreferences mPreferences;

    private static final int KEY_ID = 0;
    private static final String KEY_EMAIL = "";
    private static final long KEY_LAST_COURSE = 946681200;
    private static final long KEY_LAST_QUIZ = 946681200;

    private static final String PREFERENCES_ID = "fr.oc.multilingua.userpreferences";

    private UserPreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_ID, Context.MODE_PRIVATE);
    }

    public static UserPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserPreferencesManager(context);
        }
        return mInstance;
    }

    public void saveId(int id) {
        mPreferences.edit().putInt(String.valueOf(KEY_ID), id).apply();
    }

    public int loadId() {
        return mPreferences.getInt(String.valueOf(KEY_ID), 0);
    }

    public void saveEmail(String email) { mPreferences.edit().putString(String.valueOf(KEY_EMAIL), email).apply(); }

    public String loadEmail() { return mPreferences.getString(String.valueOf(KEY_EMAIL), ""); }

    public void saveLastCourse(long timestamp) { mPreferences.edit().putLong(String.valueOf(KEY_LAST_COURSE), timestamp).apply(); }

    public long loadLastCourse() { return mPreferences.getLong(String.valueOf(KEY_LAST_COURSE), 946681200); }

    public void saveLastQuiz(long timestamp) {
        mPreferences.edit().putLong(String.valueOf(KEY_LAST_QUIZ), timestamp).apply();
    }

    public long loadLastQuiz() {
        return mPreferences.getLong(String.valueOf(KEY_LAST_QUIZ), 946681200);
    }
}
