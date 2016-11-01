package com.applink.ford.hellosdlandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    public static String ACTION_ALARM = "com.alarammanager.alaram";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Alarm Receiver", "Entered");
        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        String action = bundle.getString(ACTION_ALARM);
        if (action.equals(ACTION_ALARM)) {
            Log.i("Alarm Receiver", "If loop");
            Intent inService = new Intent(context, DataRequestorIntentService.class);
            inService.setAction(DataRequestorIntentService.ACTION_DATA_REQUEST);
            context.startService(inService);


        } else {
            Log.i("Alarm Receiver", "Else loop");
            Toast.makeText(context, "Else loop", Toast.LENGTH_SHORT).show();
        }
    }

}
