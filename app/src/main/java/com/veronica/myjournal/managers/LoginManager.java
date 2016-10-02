package com.veronica.myjournal.managers;

import android.content.Context;

/**
 * Created by Veronica on 9/30/2016.
 */
public class LoginManager {

    private  static LoginManager mInstance = null;
    private Context mContext;

    public static LoginManager getInstance(Context context){
        if(mInstance==null){
            return  new LoginManager(context);
        }
        return mInstance;
    }

    public LoginManager(Context context) {
        this.mContext = context;
    }

    public boolean isLoggedIn(){
        return false;
    }
}
