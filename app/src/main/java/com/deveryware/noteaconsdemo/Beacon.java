package com.deveryware.noteaconsdemo;

import android.app.Application;
import android.util.Log;

import com.deveryware.noteacons.Noteacons;
import com.deveryware.noteacons.notifier.NoteaconsNotifier;

import static com.deveryware.noteacons.Noteacons.refreshData;

/**
 * Created by PYOJIHYE on 2017-02-02.
 */

public class Beacon extends Application implements NoteaconsNotifier {
    private static final String TAG = "Beacon";

    @Override
    public void onCreate() {
        super.onCreate();

        Noteacons.initSDK(this);
        Noteacons.setNoteaconNotifier(this);
        Noteacons.fineLocationPermissionGranted();
        onNoteaconsReady();
    }

    @Override
    public void onNoteaconsReady() {
        Log.i(TAG, "Ready, device id: "+ Noteacons.getDeviceId());
    }
}
