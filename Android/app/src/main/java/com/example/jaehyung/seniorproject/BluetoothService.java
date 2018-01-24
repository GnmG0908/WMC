package com.example.jaehyung.seniorproject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import java.util.logging.Handler;

/**
 * Created by Jaehyung on 2018-01-24.
 */

public class BluetoothService {
    private static final String TAG = "BluetoothService";

    private BluetoothAdapter btAdapter;

    private Activity mActivity;
    private Handler mHandler;

    public BluetoothService(Activity ac, Handler h){
        mActivity = ac;
        mHandler = h;

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }
}
