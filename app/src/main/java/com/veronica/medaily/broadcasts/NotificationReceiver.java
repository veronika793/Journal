package com.veronica.medaily.broadcasts;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.veronica.medaily.R;
import com.veronica.medaily.dbmodels.Note;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.managers.AuthorizationManager;

/**
 * @see  if the reminder belongs to curent logged user and if fires notification
 *
 */
public class NotificationReceiver extends BroadcastReceiver {
    private AuthorizationManager authorizationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        // create notification bar
        authorizationManager = new AuthorizationManager(context);
        //runs the alarms only if there is logged in user
        if(authorizationManager.isLoggedIn()){

            if(intent.hasExtra("alarm_id")) {
                Long currentUserId = Long.valueOf(authorizationManager.getUser());
                Long reminderId = intent.getLongExtra("alarm_id",-1);

                NoteReminder reminder = NoteReminder.findById(NoteReminder.class,reminderId);

                if(reminder!=null){
                    Note note = reminder.getNote();
                    if(reminder.getUser().getId()==currentUserId) {
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        android.support.v4.app.NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setSmallIcon(R.drawable.icon_add_reminder)
                                        .setContentTitle(note.getTitle())
                                        .setContentText(note.getContent())
                                        .setSound(alarmSound);

                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.notify(reminder.getId().intValue(), mBuilder.build());
                        reminder.delete();
                    }
                }
            }
        }
    }
}
