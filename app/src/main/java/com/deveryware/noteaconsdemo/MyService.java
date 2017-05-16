package com.deveryware.noteaconsdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.deveryware.noteaconsdemo.R;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyService extends Service {
    NotificationManager notificationManager;
    ServiceThread thread;
    Notification notification;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(getString(R.string.app_context))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Notification")
                    .setContentIntent(pendingIntent)
                    .build();

            notification.defaults = Notification.DEFAULT_SOUND;
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(777, notification);
        }
}
    }
