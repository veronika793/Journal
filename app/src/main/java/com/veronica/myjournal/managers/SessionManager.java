package com.veronica.myjournal.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Veronica on 10/1/2016.
 */
public class SessionManager
{
    private static String TAG = SessionManager.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context _context;

    // Shared mSharedPreferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "session_manager";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    public SessionManager(Context context)
    {
        this._context = context;
        mSharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn)
    {
        mEditor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        mEditor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn()
    {
        return mSharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
