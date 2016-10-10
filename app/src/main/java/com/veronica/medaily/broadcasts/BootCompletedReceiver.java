package com.veronica.medaily.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.veronica.medaily.Constants;
import com.veronica.medaily.services.RecallAlarmsService;

/**
 * Created by Veronica on 10/10/2016.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Constants.ACTION_BOOT_COMPLETED)){
            Intent notificationService = new Intent(context, RecallAlarmsService.class);
            notificationService.setAction(Constants.ACTION_BOOT_COMPLETED);
            context.startService(notificationService);
        }
    }
}
