package com.veronica.myjournal.kinvey;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kinvey.android.Client;
import com.veronica.myjournal.app.MyJournalApplication;

/**
 * Created by Veronica on 10/4/2016.
 */
public class KinveyClient {

    private static KinveyClient mInstance;
    private  MyJournalApplication appJournal;
    private  Client kinveyClient;

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
        appJournal =(MyJournalApplication) ((Activity)context).getApplication();
        appJournal.initializeKinveyClient();
        kinveyClient = appJournal.getKinveyClient();
    }

    public void addCurrentUser(){
    }

    public void addNotes(){

    }

}
