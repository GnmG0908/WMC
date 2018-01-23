package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Passive extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGroscope;
    private Sensor accSensor;
    Myview myview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passive);
        LinearLayout Layout = (LinearLayout) findViewById(R.id.Myview);
        myview = new Myview(this);
        Layout.addView(myview);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.auto:
                startActivity(new Intent(Passive.this, Auto.class));
                break;
            case R.id.passive:
                break;
            case R.id.secure:
                startActivity(new Intent(Passive.this, Secure.class));
                break;
            case R.id.voice:
                startActivity(new Intent(Passive.this, Voice.class));
                break;
            case R.id.main:
                startActivity(new Intent(Passive.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGroscope, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            myview.gyroX += Math.round(event.values[0] * 100);
            myview.gyroY += Math.round(event.values[1] * 100);
            myview.gyroZ += Math.round(event.values[2] * 100);
            myview.invalidate();
            /*System.out.println("gyroX =" + myview.gyroX);
            System.out.println("gyroY =" + myview.gyroY);
            System.out.println("gyroZ =" + myview.gyroZ);*/
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
