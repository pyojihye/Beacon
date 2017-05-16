package com.deveryware.noteaconsdemo;

import android.util.Log;

import com.deveryware.noteacons.campaign.NoteaconsContext;
import com.deveryware.noteacons.notifier.CampaignNotifier;

/**
 * Created by PYOJIHYE on 2017-02-02.
 */

public class MyCampaignNotifier implements CampaignNotifier {

    private static final String TAG = "MyCampaignNotifier";

    @Override
    public boolean willLaunchCampaign(NoteaconsContext context) {
        Log.d(TAG, "willLaunchCampaign");
        return  true;
    }

    @Override
    public void didLaunchCampaign(NoteaconsContext context) {
        Log.d(TAG, "didLaunchCampaign");
    }
}