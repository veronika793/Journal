package com.veronica.myjournal.database;

import android.content.Context;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.veronica.myjournal.Constants;
import com.veronica.myjournal.app.MyJournalApplication;
import com.veronica.myjournal.models.User;

/**
 * Created by Veronica on 10/4/2016.
 */
public class KinveyClient {

    private static KinveyClient mInstance;
    private  MyJournalApplication appJournal;
    private  Client mClient;

    public KinveyClient() {

    }

    public synchronized static KinveyClient getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new KinveyClient(context);
        }
        return mInstance;
    }

    private KinveyClient(Context context) {
        final Client mKinveyClient = new Client.Builder(Constants.APP_KEY_KINVEY, Constants.APP_SECRET_KINVER
                , context).build();

        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e(Constants.DEBUG_TAG, "Kinvey Ping Failed", t);
            }
            public void onSuccess(Boolean b) {
                Log.d(Constants.DEBUG_TAG, "Kinvey Ping Success");
            }
        });
        mClient = mKinveyClient;
    }


    public void addCurrentUser(User user){
        mClient.user().create(user.get_email(),user.get_password(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        mClient.user().put("user_name", user.get_name());
        mClient.user().put("user_pic", user.get_profilePicUri());
        mClient.user().put("is_fb_user", user.is_isFacebookUser());
        mClient.user().update(new KinveyUserCallback() {

            @Override
            public void onSuccess(com.kinvey.java.User user) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void loginUser(User user){
        mClient.user().login(user.get_email(),user.get_password(), new KinveyUserCallback() {
            @Override
            public void onSuccess(com.kinvey.java.User user) {
            }
            @Override
            public void onFailure(Throwable throwable) {
            }
        });
    }

    public void logoutUser(){
        mClient.user().logout().execute();
    }

    public void addNotes(){

    }

}
