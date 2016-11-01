package com.applink.ford.hellosdlandroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SdlApplication extends Application {

    private static final String TAG = SdlApplication.class.getSimpleName();

    private static int CONNECTION_TIMEOUT = 180 * 1000;

    private static SdlApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        LockScreenActivity.registerActivityLifecycle(this);

        mInstance = this;
        Intent proxyIntent = new Intent(this, com.applink.ford.hellosdlandroid.SdlService.class);
        startService(proxyIntent);


        try {
            AlarmManager alarms = (AlarmManager) this
                    .getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getApplicationContext(),
                    AlarmReceiver.class);

            intent.putExtra(AlarmReceiver.ACTION_ALARM,
                    AlarmReceiver.ACTION_ALARM);

            final PendingIntent pIntent = PendingIntent.getBroadcast(this,
                    1234567, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), 4000, pIntent);

            Toast t = Toast.makeText(getApplicationContext(), "Iniciando polleo de vehicleData!", Toast.LENGTH_SHORT);
            t.show();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
