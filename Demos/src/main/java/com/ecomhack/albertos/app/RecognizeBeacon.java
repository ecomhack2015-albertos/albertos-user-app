package com.ecomhack.albertos.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.examples.demos.R;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class RecognizeBeacon extends Activity {

    private BeaconManager beaconManager;
    private NotificationManager notificationManager;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        region = new Region("regionId", "b9407f30-f5f8-466e-aff9-25556b57fe6d", 48735, 25);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        beaconManager = new BeaconManager(this);
        beaconManager.setBackgroundScanPeriod(java.util.concurrent.TimeUnit.SECONDS.toMillis(1), 0);
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> beacons) {
                //postNotification("Beacon found!");

                new OrderUpdateTask().execute("abcd");
            }

            @Override
            public void onExitedRegion(Region region) {
               //empty
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        notificationManager.cancel(123);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startMonitoring(region);
                } catch (RemoteException e) {
                    Log.d(RecognizeBeacon.class.getSimpleName(), "Error while starting monitoring");
                }
            }
        });
    }

    private void postNotification(String msg) {
        Intent notifyIntent = new Intent(RecognizeBeacon.this, RecognizeBeacon.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(
                RecognizeBeacon.this,
                0,
                new Intent[]{notifyIntent},
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(RecognizeBeacon.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alfredo's")
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notificationManager.notify(123, notification);
    }

    //
    private class OrderUpdateTask extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... orderIDs) {
            try {
                URL url = new URL(AlfredosUserApplication.ApiURL + "/orders/" + orderIDs[0]);
                HttpClient client = new DefaultHttpClient();
                HttpPut put = new HttpPut(url.toString());

                    /*List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("key1", "value1"));
                    pairs.add(new BasicNameValuePair("key2", "value2"));
                    put.setEntity(new UrlEncodedFormEntity(pairs));*/

                HttpResponse response = client.execute(put);

                return(response.getStatusLine().getStatusCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        protected void onPostExecute(Integer result) {
            if (result == 200) {
                postNotification("Your meal will be prepared!");
            }
        }
    }
}
