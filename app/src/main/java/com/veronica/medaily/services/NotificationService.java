package com.veronica.medaily.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;

import com.veronica.medaily.R;

/**
 * Created by Veronica on 10/10/2016.
 */
public class NotificationService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
