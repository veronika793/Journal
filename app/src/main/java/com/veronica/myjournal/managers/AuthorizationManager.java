package com.veronica.myjournal.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by Veronica on 10/1/2016.
 */
public class AuthorizationManager
{
    private static AuthorizationManager mInstance;


    public synchronized static AuthorizationManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new AuthorizationManager(context);
        }
        return mInstance;
    }

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context _context;

    // Shared mSharedPreferences mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "session_manager";

    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private AuthorizationManager(Context context)
    {
        this._context = context;
        mSharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();

    }

    public void logoutUser(){
        mEditor.putBoolean(KEY_IS_LOGGED_IN, false);
        mEditor.commit();
        if(AccessToken.getCurrentAccessToken()!=null) {
            LoginManager.getInstance().logOut();
        }
    }

    public void loginUser()
    {
        if(AccessToken.getCurrentAccessToken()==null) {
            mEditor.putBoolean(KEY_IS_LOGGED_IN, true);
            mEditor.commit();
        }

    }

    public boolean isLoggedIn()
    {
        if(AccessToken.getCurrentAccessToken()!=null){
            return true;
        }
        return mSharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
