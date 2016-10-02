package com.veronica.myjournal.app;

import android.app.Application;

import com.veronica.myjournal.managers.DialogManager;

/**
 * Created by Veronica on 9/28/2016.
 */
public class MyJournalApplication extends Application {

    private DialogManager mDialogManager;

    public DialogManager getDialogManager() {
        return mDialogManager;
    }

    public void setDialogManager(DialogManager dialogManager) {
        this.mDialogManager = dialogManager;
    }

}
