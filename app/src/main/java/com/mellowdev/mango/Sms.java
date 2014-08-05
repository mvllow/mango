package com.mellowdev.mango;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;

public class Sms extends BroadcastReceiver {
    public static String num = "";
    public static String body = "";
    public static ArrayList<String> people = new ArrayList<String>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bun = intent.getExtras();
        SmsMessage[] msgs;

        if (bun != null) {
            Object[] pdus = (Object[]) bun.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i=0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                num = msgs[i].getOriginatingAddress();
                body = msgs[i].getMessageBody();
            }

            if (!people.contains(num) && !Main.reply.equals("")) {
                PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
                SmsManager sm = SmsManager.getDefault();

                people.add(num);

                if (Main.sel.equals("Nerd")) {
                    if (Sms.body.contains("weather")) {
                        Main.reply = "It is always sunny in Philadelphia.";
                    }
                }

                sm.sendTextMessage(num, null, Main.reply, pi, null);
                Toast.makeText(context, "Replied to " + num, Toast.LENGTH_LONG).show();
            }
        }
    }
}