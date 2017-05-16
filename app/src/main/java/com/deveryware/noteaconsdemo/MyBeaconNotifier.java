package com.deveryware.noteaconsdemo;

import android.util.Log;

import com.deveryware.noteacons.action.NoteaconsAction;
import com.deveryware.noteacons.beacon.NoteaconsBeacon;
import com.deveryware.noteacons.notifier.BeaconNotifier;

/**
 * Created by PYOJIHYE on 2017-02-03.
 */
public class MyBeaconNotifier implements BeaconNotifier {

    private static final String TAG = "MyBeaconNotifier";

    @Override
    public void onEnterBeacon(NoteaconsBeacon beacon) {
        Log.d(TAG, beacon.toString());
    }

    @Override
    public void onExitBeacon(NoteaconsBeacon beacon) {
        Log.d(TAG, beacon.toString());
    }
}
