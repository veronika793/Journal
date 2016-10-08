package com.veronica.medaily.broadcasts;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.veronica.medaily.R;

/**
 * Created by Veronica on 10/8/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // create notification bar

        if(intent.hasExtra("alarm_id")){
            int alarmId = intent.getIntExtra("alarm_id",0);
            String title = intent.getStringExtra("alarm_title");
            String content = intent.getStringExtra("alarm_content");
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Log.d("DEBUG", alarmSound.toString());
            android.support.v4.app.NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.icon_add_note)
                            .setContentTitle(title)
                            .setContentText(content)
                            .setSound(alarmSound);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(alarmId, mBuilder.build());
        }

    }


}
