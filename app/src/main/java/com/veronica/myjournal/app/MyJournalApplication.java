package com.veronica.myjournal.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.veronica.myjournal.Constants;
import com.veronica.myjournal.managers.AuthorizationManager;
import com.veronica.myjournal.managers.DatabaseHelper;
import com.veronica.myjournal.managers.DialogManager;

/**
 * Created by Veronica on 9/28/2016.
 */
public class MyJournalApplication extends Application {

    private AuthorizationManager authorizationManager;
    private DialogManager mDialogManager;
    private DatabaseHelper dbManager;
    private Client kinvClient;

    public void setDialogManager(DialogManager dialogManager) {
        this.mDialogManager = dialogManager;

    }

    public DatabaseHelper getDbManager() {
        return dbManager;
    }
    public DialogManager getDialogManager() {
        return mDialogManager;
    }
    public AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public Client getKinveyClient(){
        return kinvClient;
    }

    public void initializeKinveyClient(){
        final Client mKinveyClient = new Client.Builder(Constants.APP_KEY_KINVEY, Constants.APP_SECRET_KINVER
                , getApplicationContext()).build();

        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e(Constants.DEBUG_TAG, "Kinvey Ping Failed", t);
            }
            public void onSuccess(Boolean b) {
                Log.d(Constants.DEBUG_TAG, "Kinvey Ping Success");
            }
        });
        kinvClient = mKinveyClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.authorizationManager = AuthorizationManager.getInstance(getApplicationContext());
        this.dbManager = new DatabaseHelper(getApplicationContext());

    }

}
