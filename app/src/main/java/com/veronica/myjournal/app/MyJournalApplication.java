package com.veronica.myjournal.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

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

    @Override
    public void onCreate() {
        super.onCreate();
        this.authorizationManager = AuthorizationManager.getInstance(getApplicationContext());
        this.dbManager = new DatabaseHelper(getApplicationContext());
    }

}
