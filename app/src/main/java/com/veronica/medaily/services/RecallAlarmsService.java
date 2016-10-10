package com.veronica.medaily.services;

import android.app.IntentService;
import android.content.Intent;

import com.veronica.medaily.managers.AlarmsManager;
import com.veronica.medaily.Constants;
import com.veronica.medaily.dbmodels.NoteReminder;
import com.veronica.medaily.helpers.DateHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Veronica on 10/10/2016.
 */
public class RecallAlarmsService extends IntentService {

    private AlarmsManager alarmsManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RecallAlarmsService() {
        super(RecallAlarmsService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmsManager = new AlarmsManager(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction().equals(Constants.ACTION_BOOT_COMPLETED)){

            List<NoteReminder> reminders = getAllReminders();

            for (int i = 0; i < reminders.size(); i++) {

                NoteReminder currentRemind = reminders.get(i);
                String reminderDateAsString = currentRemind.getStartTime();
                Date reminderDate = DateHelper.fromStringToDate(reminderDateAsString);
                if(reminderDate.after(new Date())){
                    alarmsManager.setupAlarm(currentRemind);
                }else{
                    NoteReminder.delete(currentRemind);
                }
            }
        }
    }

    private List<NoteReminder> getAllReminders(){
        List<NoteReminder> reminders = NoteReminder.listAll(NoteReminder.class);
        return reminders;
    }
}

