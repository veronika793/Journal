package com.veronica.medaily.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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

    private Context mContext;

    public NotificationHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void toastSuccessNotificationTop(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_success));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.TOP,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastSuccessNotificationBottom(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_success));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastWarningNotificationTop(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_warning));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.TOP,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastWarningNotificationBottom(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_warning));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastNeutralNotificationTop(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_neutral));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.TOP,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastNeutralNotificationBottom(String text){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_toast_container));
        layout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_toast_neutral));

        TextView toastTxtView = (TextView) layout.findViewById(R.id.text);
        toastTxtView.setText(text);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.BOTTOM,0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }



}
