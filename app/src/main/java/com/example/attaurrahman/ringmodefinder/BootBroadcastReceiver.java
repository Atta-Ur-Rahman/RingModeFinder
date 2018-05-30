package com.example.attaurrahman.ringmodefinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by AttaUrRahman on 5/30/2018.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // BOOT_COMPLETED” start Service
        if (intent.getAction().equals(ACTION)) {
            //Service
            Intent serviceIntent = new Intent(context, MyService.class);
            context.startService(serviceIntent);
        }
    }
}