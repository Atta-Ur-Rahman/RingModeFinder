package com.example.attaurrahman.ringmodefinder;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by AttaUrRahman on 5/30/2018.
 */
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    private static final int CAMERA_REQUEST = 1888;
    private SurfaceView sv;
    private boolean safeToTakePicture = false;
    private int count = 0;

    /**
     * method to show toast
     *
     * @param context the application context on which the toast has to be displayed
     * @param msg     the message which will be displayed in the toast
     */
    private void showToast(Context context, CharSequence msg) {
        Log.e("MyDeviceAdminRec...", "::>>>>1 ");
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.e("MyDeviceAdminRec...", "::>>>>2 ");
        showToast(context, "Sample Device Admin: enabled");
       context.startService(new Intent(context, MyService.class));


    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        Log.e("MyDeviceAdminRec...", "::>>>>3 ");
        context.stopService(new Intent(context, MyService.class));
        return "This is an optional message to warn the user about disabling.";

    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.e("MyDeviceAdminRec...", "::>>>>4 ");
        context.stopService(new Intent(context, MyService.class));

        showToast(context, "Sample Device Admin: disabled");
    }
//
//    @Override
//    public void onPasswordChanged(Context context, Intent intent) {
//        Log.e("MyDeviceAdminRec...", "::>>>>5 ");
//        showToast(context, "Sample Device Admin: pw changed");
//    }
//
//    @Override
//    public void onPasswordFailed(Context context, Intent intent) {
//        Log.e("MyDeviceAdminRec...", "::>>>>6 ");
//        showToast(context, "Sample Device Admin: pw failed");
//    }
//
//    @Override
//    public void onPasswordSucceeded(Context context, Intent intent) {
//        Log.e("MyDeviceAdminRec...", "::>>>>7 ");
//        showToast(context, "Sample Device Admin: pw succeeded");
//    }
}