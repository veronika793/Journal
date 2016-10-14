package com.veronica.medaily.toberemoved;

import android.os.AsyncTask;
import android.widget.TextView;

import com.veronica.medaily.kinvey.KinveyConnector;

/**
 * Created by Veronica on 10/13/2016.
 */
public class KinveyImportLoader extends AsyncTask<Void,Integer,Void> {
    private TextView view;
    private KinveyConnector connector;

    public KinveyImportLoader(TextView view, KinveyConnector connector) {
        this.view = view;
        this.connector = connector;
    }

    @Override
    protected Void doInBackground(Void... params) {
        connector.importData();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        view.setText("Completed");
    }
}
