package com.veronica.myjournal.app;

import android.app.Application;

import com.veronica.myjournal.database.NotesDbManager;
import com.veronica.myjournal.managers.AuthorizationManager;
import com.veronica.myjournal.database.UsersDbManager;
import com.veronica.myjournal.managers.DialogManager;

/**
 * Created by Veronica on 9/28/2016.
 */
public class MyJournalApplication extends Application {

    private AuthorizationManager authorizationManager;
    private DialogManager mDialogManager;
    private UsersDbManager usersDbManager;
    private NotesDbManager notesDbManager;


    public void setDialogManager() {
        this.mDialogManager = new DialogManager(getApplicationContext());
    }

    public DialogManager getDialogManager() {
        return this.mDialogManager;
    }

    public void setUsersDbManager(){
        this.usersDbManager = new UsersDbManager(getApplicationContext());
    }

    public NotesDbManager getNotesDbManager(){
        return this.notesDbManager;
    }

    public void setNotesDbManager(){
        this.notesDbManager = new NotesDbManager(getApplicationContext());
    }

    public UsersDbManager getUserDbManager() {
        return this.usersDbManager;
    }

    public void setAuthManager(){
        this.authorizationManager = new AuthorizationManager(getApplicationContext());
    }

    public AuthorizationManager getAuthManager() {
        return this.authorizationManager;
    }

}
