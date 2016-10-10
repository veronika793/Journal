package com.veronica.medaily.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Veronica on 10/10/2016.
 */
public class NotesLoader extends AsyncTask<Uri,Integer,Void> {

    Activity activity;
    ImageView imageView;
    Bitmap croppedImage;
    ProgressBar progressBar;

    public NotesLoader(Activity activity, ImageView view, ProgressBar progressBar) {
        this.activity = activity;
        this.imageView = view;
        this.progressBar = progressBar;
    }


    @Override
    protected Void doInBackground(Uri... params) {


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
}

