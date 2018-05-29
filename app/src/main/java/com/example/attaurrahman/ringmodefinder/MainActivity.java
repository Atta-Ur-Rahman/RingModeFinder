package com.example.attaurrahman.ringmodefinder;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String strMsg, strIncomeNumber;
    double callTime, answeredTime, timeTaken;

    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;

    // Interaction with the DevicePolicyManager
    DevicePolicyManager mDPM;
    ComponentName mDeviceAdmin;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // startService(new Intent(MainActivity.this, MyService.class));


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);


        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {

                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    strIncomeNumber = incomingNumber;

                    Toast.makeText(getApplicationContext(), "Phone is Ringing : " + incomingNumber, Toast.LENGTH_LONG).show();

                    callTime = System.currentTimeMillis();


                }
                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                    Toast.makeText(getApplicationContext(), "Phone in a call or call picked", Toast.LENGTH_LONG).show();

                    callTime = 0;

                }
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    //phone is neither ringing nor in a call
                    answeredTime = System.currentTimeMillis();
                    timeTaken = answeredTime - callTime;
                    if (timeTaken >= 2000) {
                        changeRingerMode(MainActivity.this);
                        sendSMS(strIncomeNumber, strMsg);
                    } else {
                        Toast.makeText(MainActivity.this, "Miss Call", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("zama timetaken", String.valueOf(timeTaken));

                }

            }
        };
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    public void changeRingerMode(Context context) {

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                Toast.makeText(context, "normal", Toast.LENGTH_SHORT).show();

                strMsg = "Ringing mode";
                break;
            case AudioManager.RINGER_MODE_SILENT:
                strMsg = "Silent mode";
                Toast.makeText(context, "silent", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                strMsg = "Vib Mode";
                Toast.makeText(context, "vib", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);


            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();


        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


}
