package com.applink.ford.hellosdlandroid;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DataRequestorIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_DATA_REQUEST = "com.applink.ford.hellosdlandroid.action.DATA_REQUEST";
    public static final String ACTION_BAZ = "com.applink.ford.hellosdlandroid.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.applink.ford.hellosdlandroid.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.applink.ford.hellosdlandroid.extra.PARAM2";

    public DataRequestorIntentService() {
        super("DataRequestorIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startDataRequest(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataRequestorIntentService.class);
        intent.setAction(ACTION_DATA_REQUEST);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataRequestorIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DATA_REQUEST.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleDataRequestAction(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleDataRequestAction(String param1, String param2) {

        SdlService service = SdlService.getInstance();
        service.sampleGetVehicleData();


        StringBuffer sb = new StringBuffer();
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://192.168.43.68/");

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                sb.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        Log.i("CLOUDSERVER", "ESTAMOS " + sb.toString());

        if (sb.toString().startsWith("cerca")) {
            SdlProxyALM proxy = SdlService.getInstance().getProxy();
            try {
                proxy.alert("GUARDA! Acá se la pegaron", "Guarda!", "Acá se la pegaron!!!", null, true, 5000, null, 666);
            } catch (SdlException e) {
                Log.e("CLOUDSERVER", "nos fuimos al pasto", e);
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
