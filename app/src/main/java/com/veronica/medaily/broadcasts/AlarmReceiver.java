package com.veronica.medaily.broadcasts;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.AuthorizationManager;

import java.util.List;

/**
 * Created by Veronica on 10/8/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    AuthorizationManager authorizationManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        // create notification bar

        authorizationManager = new AuthorizationManager(context);
        //runs the alarms only if there is logged in user
        if(authorizationManager.isLoggedIn()){

            if(intent.hasExtra("alarm_id")) {
                Long currentUserId = Long.valueOf(authorizationManager.getUser());
                List<NoteReminder> userReminders = NoteReminder.find(NoteReminder.class," user = ?", String.valueOf(currentUserId));
                for (NoteReminder reminder : userReminders) {
                    Long alarmId = intent.getLongExtra("alarm_id",-1);
                    //check if current user has such alarm and can be notified
                    if(reminder.getId() == alarmId){
                        String title = intent.getStringExtra("alarm_title");
                        String content = intent.getStringExtra("alarm_content");
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        android.support.v4.app.NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.drawable.icon_add_note)
                                        .setContentTitle(title)
                                        .setContentText(content)
                                        .setSound(alarmSound);

                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(alarmId.intValue(), mBuilder.build());
                    }
                }
            }
        }

    }
}
