package com.veronica.myjournal.helpers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

/**
 * Created by Veronica on 10/2/2016.
 */
public final class SmsSender{

    public static void SendSms(Context context,String phone, String content) {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SENT";

        sentPI = PendingIntent.getBroadcast(context, 0,new Intent(SENT), 0);
        TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        sms.sendTextMessage(phone, null, content, sentPI, null);
    }


}
