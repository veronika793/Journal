package com.veronica.journal.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthorizationManager
{
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context _context;

    // Shared mSharedPreferences mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "session_manager";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";

    public AuthorizationManager(Context context)
    {
        this._context = context;
        mSharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();

    }

    public void logoutUser(){
        mEditor.putBoolean(KEY_IS_LOGGED_IN, false);
        mEditor.putString(KEY_USER_ID, null);
        mEditor.commit();

    }

    public void loginUser(String id)
    {
        mEditor.putBoolean(KEY_IS_LOGGED_IN, true);
        mEditor.putString(KEY_USER_ID, id);
        mEditor.commit();
    }

    public boolean isLoggedIn()
    {
        return mSharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUser(){
        return mSharedPreferences.getString(KEY_USER_ID, null);
    }
}
