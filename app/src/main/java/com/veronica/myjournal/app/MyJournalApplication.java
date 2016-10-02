package com.veronica.myjournal.app;

import android.app.Application;

import com.veronica.myjournal.managers.DialogManager;
import com.veronica.myjournal.managers.SessionManager;

/**
 * Created by Veronica on 9/28/2016.
 */
public class MyJournalApplication extends Application {

    private DialogManager mDialogManager;
    private SessionManager mSessionManager;

    public DialogManager getDialogManager() {
        return mDialogManager;
    }

    public SessionManager getSessionManager(){
        return mSessionManager;
    }

    public void setDialogManager(DialogManager dialogManager) {
        this.mDialogManager = dialogManager;
    }

    public void setSessionManager(SessionManager sessionManager){
        this.mSessionManager = sessionManager;
    }
}
