package com.deveryware.noteaconsdemo;

import com.deveryware.noteacons.action.NoteaconsAction;
import com.deveryware.noteacons.notifier.ActionNotifier;

/**
 * Created by PYOJIHYE on 2017-02-02.
 */

public class MyActionNotifier implements ActionNotifier {

    @Override
    public boolean onExecuteActionCustomCallback(NoteaconsAction action, String functionName, String params) {
        return true;
    }

    @Override
    public boolean onExecuteActionMessage(NoteaconsAction action, String title, String message) {
        //Return false to use the default implementation.
        return false;
    }
}