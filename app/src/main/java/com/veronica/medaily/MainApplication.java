package com.veronica.medaily;

import android.content.ContextWrapper;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.veronica.medaily.dbmodels.Category;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.dbmodels.User;
import com.veronica.medaily.kinvey.KinveyConnector;
import com.veronica.medaily.managers.AuthorizationManager;

import java.io.File;

/**
 * Created by Veronica on 10/6/2016.
 */
public class MainApplication extends SugarApp {

    private AuthorizationManager authorizationManager;


    public void setAuthManager(){
        this.authorizationManager = new AuthorizationManager(getApplicationContext());
    }

    public AuthorizationManager getAuthManager() {
        return this.authorizationManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(getApplicationContext());

        initializeSingletons();
        if(!doesDatabaseExist((ContextWrapper) getApplicationContext(),Constants.DB_NAME)){
            User.findById(User.class, (long) 1);
            Note.findById(Note.class, (long) 1);
            NoteReminder.findById(NoteReminder.class, (long) 1);
            Category.findById(Category.class, (long) 1);

        }else{
//            recreates database..
//            SugarDb sugarDb = new SugarDb(getApplicationContext());
//            new File(sugarDb.getDB().getPath()).delete();
            User.findById(User.class, (long) 1);
            Note.findById(Note.class, (long) 1);
            NoteReminder.findById(NoteReminder.class, (long) 1);
            Category.findById(Category.class, (long) 1);
        }
    }

    private boolean doesDatabaseExist(ContextWrapper context, String dbName){
        File dbfile = context.getDatabasePath(dbName);
        return dbfile.exists();
    }

    private void initializeSingletons() {

        KinveyConnector.setupContext(getApplicationContext());
        KinveyConnector.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
