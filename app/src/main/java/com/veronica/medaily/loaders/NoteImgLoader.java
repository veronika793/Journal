package com.veronica.medaily.loaders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.veronica.medaily.Constants;
import com.veronica.medaily.helpers.BitmapHelper;

/**
 * Created by Veronica on 10/14/2016.
 */
public class NoteImgLoader extends AsyncTask<Uri,Integer,Void>{
    Activity activity;
    ImageView imageView;
    Bitmap result;
    TextView mask;

    public NoteImgLoader(Activity activity, ImageView view,TextView mask) {
        this.activity = activity;
        this.imageView = view;
        this.mask = mask;
    }

    @Override
    protected Void doInBackground(Uri... params) {

        try {
            Bitmap image = BitmapHelper.decodeUri(activity.getContentResolver(),params[0]);
            int imageX = BitmapHelper.dpToPx(activity.getApplicationContext(), Constants.PICK_WITH);
            int imageY = BitmapHelper.dpToPx(activity.getApplicationContext(), Constants.PICK_HEIGHT);
            result = BitmapHelper.getResizedBitmap(image,imageY,imageX);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.imageView.setImageBitmap(result);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }
}
