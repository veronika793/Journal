package com.veronica.medaily.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.veronica.medaily.R;


/**
 * Created by Veronica on 10/4/2016.
 */
public class NotificationHandler {

    private static Context mContext;
    private static Toast currentToast;
    private static TextView toastTextView;
    private static View toastLayout;
    private static NotificationHandler mInstance;

    public synchronized static NotificationHandler getInstance(){
        if(mInstance==null){
            mInstance = new NotificationHandler();
        }
        return mInstance;
    }

    public static void setupNotifier(Context context){
        if(mContext==null) {
            mContext = context;
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        toastLayout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        toastTextView = (TextView) toastLayout.findViewById(R.id.text);
    }

    public void toastWarningNotification(String text){

        if(currentToast!=null && toastTextView.getText().toString().length()>0 ){
            currentToast.cancel();
        }
        currentToast = new Toast(mContext);
        toastTextView.setText(text);
        toastLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_warning));
        currentToast.setGravity(Gravity.BOTTOM,0, 20);
        currentToast.setDuration(Toast.LENGTH_SHORT);
        currentToast.setView(toastLayout);
        currentToast.show();
    }

    public void toastSuccessNotification(String text){
        if(currentToast!=null && toastTextView.getText().toString().length()>0 ){
            currentToast.cancel();
        }
        currentToast = new Toast(mContext);
        toastTextView.setText(text);
        toastLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_success));
        currentToast.setGravity(Gravity.BOTTOM,0, 20);
        currentToast.setDuration(Toast.LENGTH_SHORT);
        currentToast.setView(toastLayout);
        currentToast.show();
    }

}
