package com.veronica.myjournal.managers;

import android.content.Context;

import com.veronica.myjournal.models.User;

/**
 * Created by Veronica on 10/2/2016.
 */
public class UsersDbManager {
    private static UsersDbManager mInstance;
    private Context mContext;

    public static UsersDbManager getInstance(Context context){
        if(mInstance==null){
            return new UsersDbManager(context);
        }
        return mInstance;
    }

    private UsersDbManager(Context context) {
        mContext = context;
    }

    public void getUserData(String email){
        //find user by email from db...

    }

    public boolean checkIfUserExists(String email){
        // check if user with the same email exists
        return true;
    }

    public boolean isFacebookUser(){
        return false;
    }

    public void registerUser(User user){
        //register user into database..

        AuthorizationManager.getInstance(mContext).setLogin(true);
    }



}
