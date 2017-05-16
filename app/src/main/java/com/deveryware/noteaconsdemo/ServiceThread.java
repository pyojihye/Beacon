package com.deveryware.noteaconsdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.deveryware.noteacons.Noteacons;
import com.deveryware.noteacons.notifier.BeaconNotifier;
import com.uriio.beacons.Beacons;
import com.uriio.beacons.model.iBeacon;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by PYOJIHYE on 2017-02-01.
 */

public class ServiceThread extends Thread {
    private final String TAG = "ServiceThread";
    Handler handler;
    boolean isRun = true;

    BluetoothLeAdvertiser advertiser =  BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        while (isRun) {
//            handler.sendEmptyMessage(0);
            try {
                new com.deveryware.noteaconsdemo.Beacon();
                Noteacons.setBeaconNotifier(new MyBeaconNotifier());
                Noteacons.setCampaignNotifier(new MyCampaignNotifier());
                Noteacons.setActionNotifier(new MyActionNotifier());
                Advertising();
                sleep(1000);
            } catch (Exception e) {

            }
        }
    }

    public void Advertising() {

        Beacon beacon = new Beacon.Builder()
                .setId1("cdb7950d-73f1-4d4d-8e47-c090502dbd63") // UUID for beacon
                .setId2("1") // Major for beacon
                .setId3("2") // Minor for beacon
                .setManufacturer(0x004C) // Radius Networks.0x0118  Change this for other beacon layouts//0x004C for iPhone
                .setTxPower(-56) // Power in dB
                .setDataFields(Arrays.asList(new Long[] {0l})) // Remove this for beacon layouts without d: fields
                .build();

        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");

        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(MainActivity.context, beaconParser);
        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

            @Override
            public void onStartFailure(int errorCode) {
                Log.e(TAG, "Advertisement start failed with code: "+errorCode);
            }

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.i(TAG, "Advertisement start succeeded.");
            }
        });

        int result = BeaconTransmitter.checkTransmissionSupported(MainActivity.context);
        Toast.makeText(MainActivity.context, "Device info " + result, Toast.LENGTH_SHORT).show();
    }
}
