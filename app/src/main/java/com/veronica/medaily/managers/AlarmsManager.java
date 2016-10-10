package com.veronica.medaily.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.veronica.medaily.Constants;
import com.veronica.medaily.broadcasts.NotificationReceiver;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Veronica on 10/10/2016.
 */
public class AlarmsManager {

    private Context context;

    public AlarmsManager(Context context) {
        this.context = context;
    }

    public void setupAlarm(NoteReminder reminder){
        Date date = DateHelper.fromStringToDate(reminder.getStartTime());
        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.setTime(date);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(Constants.START_ALARM_ACTION);
        intent.putExtra("alarm_id", reminder.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder.getId().intValue(), intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, reminderCalendar.getTimeInMillis(), pendingIntent);

    }
}
