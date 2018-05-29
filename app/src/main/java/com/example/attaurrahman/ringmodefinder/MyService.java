package com.example.attaurrahman.ringmodefinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    String strMsg, strIncomeNumber;
    double callTime, answeredTime, timeTaken;
    boolean isAvailable;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();

        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);


        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {

                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    strIncomeNumber = incomingNumber;
                    // tts.speak(incomingNumber+" calling", TextToSpeech.QUEUE_FLUSH, null);
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
                    if (timeTaken >= 1000) {
                        changeRingerMode(MyService.this);
                        sendSMS(strIncomeNumber, strMsg);
                    } else {
                        Toast.makeText(MyService.this, "Miss Call", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("zama timetaken", String.valueOf(timeTaken));
                }

            }
        };
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();



        return START_STICKY;
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


//            SimUtils.sendSMS(MainActivity.this,1,phoneNo,null,msg,null,null);
//            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();


        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}
