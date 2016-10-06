package com.veronica.journal;

import android.content.ContextWrapper;
import android.util.Log;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.veronica.journal.dbmodels.Note;
import com.veronica.journal.dbmodels.User;
import com.veronica.journal.kinvey.KinveyConnector;
import com.veronica.journal.managers.AuthorizationManager;

import java.io.File;

/**
 * Created by Veronica on 10/6/2016.
 */
public class JournalApp extends SugarApp {

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

            Log.d("DEBUG", "DOES NOT EXISTS");
            User.findById(User.class, (long) 1);
            Note.findById(Note.class, (long) 1);
        }else{
            Log.d("DEBUG", "DATABASE EXISTS");
//            recreates database..
//            SugarDb sugarDb = new SugarDb(getApplicationContext());
//            new File(sugarDb.getDB().getPath()).delete();
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
