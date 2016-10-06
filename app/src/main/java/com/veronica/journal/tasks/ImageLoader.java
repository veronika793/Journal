package com.veronica.journal.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Veronica on 10/6/2016.
 */
public class  ImageLoader extends AsyncTask<Void,Integer,Bitmap>{

    ProgressBar progressBar;
    ImageView imageView;
    Bitmap bitmap;

    public ImageLoader(ProgressBar progressBar,ImageView view, Bitmap bitmap) {
        this.progressBar = progressBar;
        this.imageView = view;
        this.bitmap = bitmap;
    }


    @Override
    protected Bitmap doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
}
