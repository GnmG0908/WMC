package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Passive extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGroscope;
    Bluetooth bt;
    ImageButton left, right, up, stop;
    Button slt;
    Myview myview;

    int select = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passive);
        LinearLayout Layout = (LinearLayout) findViewById(R.id.Myview);

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_passive);
        getSupportActionBar().setElevation(5);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //버튼 선언부
        left = (ImageButton) findViewById(R.id.left);
        up = (ImageButton) findViewById(R.id.up);
        right = (ImageButton) findViewById(R.id.right);
        stop = (ImageButton) findViewById(R.id.stop);
        slt = (Button) findViewById(R.id.slt);

        //intent
        Intent intent = getIntent();
        bt.mTmp = intent.getStringExtra("CARY");
        bt.connectToSelectedDevice(bt.mTmp);
        bt.connectToSelectedDevice(bt.mTmp);
        //카메라뷰 선언부
        myview = new Myview(this);
        Layout.addView(myview);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        right.setEnabled(false);
        left.setEnabled(false);
        up.setEnabled(false);

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (select % 2 == 1) {
                    if(myview.gyroX>0)
                        myview.gyroX = 0;
                    myview.gyroX -= 30;
                    myview.invalidate();
                    bt.sendData("L");
                }
                return false;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (select % 2 == 1) {
                    if(myview.gyroX<0)
                        myview.gyroX =0;
                    myview.gyroX += 30;
                    myview.invalidate();
                    bt.sendData("R");
                }
                return false;
            }
        });
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (select % 2 == 1) {
                    myview.gyroY += 15;
                    myview.invalidate();
                    bt.sendData("F");
                }
                return false;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.gyroX = 0;
                myview.gyroY=0;
                myview.invalidate();
                bt.sendData("stop");
            }
        });
        slt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select++;
                myview.gyroX = 0;
                myview.gyroY = 0;
                myview.gyroZ = 0;
                if(select%2==1)
                {
                    right.setEnabled(true);
                    left.setEnabled(true);
                    up.setEnabled(true);
                }
                else {
                    right.setEnabled(false);
                    left.setEnabled(false);
                    up.setEnabled(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()!=R.id.passive)
            finish();
        switch (item.getItemId()) {
            case R.id.auto:
                startActivity(new Intent(Passive.this, Auto.class));
                break;
            case R.id.passive:
                break;
            case R.id.main:
                //startActivity(new Intent(Passive.this, MainActivity.class));
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
        if (sensor.getType() == Sensor.TYPE_GYROSCOPE && select % 2 == 0) {
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
