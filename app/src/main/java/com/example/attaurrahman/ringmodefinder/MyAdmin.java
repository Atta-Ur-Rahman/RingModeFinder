package com.example.attaurrahman.ringmodefinder;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by AttaUrRahman on 5/29/2018.
 */

public class MyAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Device Admin Enable", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDisabled(Context context, Intent intent) {

        Toast.makeText(context, "Device Admin Disable", Toast.LENGTH_SHORT).show();
    }
}
