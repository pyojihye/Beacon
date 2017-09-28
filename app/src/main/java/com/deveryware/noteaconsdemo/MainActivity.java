package com.deveryware.noteaconsdemo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deveryware.noteacons.Noteacons;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart, buttonEnd;
    private final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int REQUEST_ENABLE_BT = 3;

    private BluetoothAdapter myBluetoothAdapter;
    private LocationManager locationManager;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonEnd = (Button) findViewById(R.id.buttonEnd);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        askForPermissions();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Smart Door Service Start!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                if (myBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothOn();
                    gpsOn();
                    new Beacon();
                }
            }
        });

        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Smart Door Service End!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);

                if (myBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Bluetooth is not available!!", Toast.LENGTH_SHORT).show();
                } else {
                    blueToothOff();
                }
            }
        });
    }

    public void blueToothOn() {
        if (!myBluetoothAdapter.isEnabled()) {
            Intent IntentEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(IntentEnable, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }

    public void gpsOn() {
        if (android.os.Build.VERSION.SDK_INT < 23) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                Toast.makeText(this, "GPS is already on", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please check the GPS use.", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                } else {
//                    Toast.makeText(this, "GPS is already on", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                }
            }
        }
    }

    public void blueToothOff() {
        if (myBluetoothAdapter.isEnabled()) {
            myBluetoothAdapter.disable();
        }
    }

    private void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Fine location permission granted");
                    Noteacons.fineLocationPermissionGranted();
                } else {
                    Log.w(TAG, "Fine location permission not granted");
                }
            }
        }
    }
}
