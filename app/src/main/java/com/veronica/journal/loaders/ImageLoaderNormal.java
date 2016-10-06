package com.veronica.journal.loaders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.veronica.journal.Constants;
import com.veronica.journal.helpers.BitmapHelper;

/**
 * Created by Veronica on 10/6/2016.
 */
public class ImageLoaderNormal extends AsyncTask<Uri,Integer,Void> {

    Activity activity;
    ImageView imageView;
    Bitmap image;
    ProgressBar progressBar;

    public ImageLoaderNormal(Activity activity, ImageView view, ProgressBar progressBar) {
        this.activity = activity;
        this.imageView = view;
        this.progressBar = progressBar;
    }

    @Override
    protected Void doInBackground(Uri... params) {

        try {
            image = BitmapHelper.decodeUri(activity.getContentResolver(),params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.imageView.setImageBitmap(image);
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
}
