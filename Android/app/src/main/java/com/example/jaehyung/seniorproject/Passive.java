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
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.estimote.coresdk.cloud.model.Telemetry;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Passive extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mGroscope;
    Bluetooth bt = new Bluetooth();
    ImageButton left, right, up, stop;


    int select = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passive);
        Intent intent = getIntent();

        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_passive);
        getSupportActionBar().setElevation(5);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //버튼 선언부
        left = (ImageButton) findViewById(R.id.left);
        up = (ImageButton) findViewById(R.id.up);
        right = (ImageButton) findViewById(R.id.right);
        stop = (ImageButton) findViewById(R.id.stop);

        //intent
        bt.mTmp = intent.getStringExtra("CARY");

        if (bt.mTmp != "\n") {
            bt.connectToSelectedDevice(bt.mTmp);
            bt.connectToSelectedDevice(bt.mTmp);
        }
        //카메라뷰 선언부
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(255);
        //영상을 폭에 꽉 차게 할려고 했지만 먹히지 않음???
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //이건 최신 버전에서는 사용하지 않게됨
        //webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //영상을 폭을 꽉 차게 하기 위해 직접 html태그로 작성함.
        //webView.loadUrl("http://www.youtube.com");
        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} img{width:100%25;} div{overflow: hidden;} </style></head><body><div><img src='http://192.168.137.115:8080/stream/video.mjpeg'/></div></body></html>", "text/html", "UTF-8");
        //webView.loadUrl("http://192.168.137.115:8080/stream");


        //Layout.addView(myview);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mGroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        /*left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select % 2 == 1) {
                   *//* if (myview.gyroX > 0)
                        myview.gyroX = 0;
                    myview.gyroX -= 30;
                    myview.invalidate();*//*
                    if (bt.mTmp != "\n")
                        bt.sendData("L");
                }
            }
        });*/
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (bt.mTmp != "\n")
                            bt.sendData("HL");
                        break;
                    case MotionEvent.ACTION_UP:
                        if (bt.mTmp != "\n")
                            bt.sendData("stop");
                        break;
                }
                return true;
            }
        });
        /*right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select % 2 == 1) {
                   *//* if (myview.gyroX < 0)
                        myview.gyroX = 0;
                    myview.gyroX += 30;
                    myview.invalidate();*//*
                    if (bt.mTmp != "\n")
                        bt.sendData("R");
                }
            }
        });*/
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (bt.mTmp != "\n")
                            bt.sendData("HR");
                        break;
                    case MotionEvent.ACTION_UP:
                        if (bt.mTmp != "\n")
                            bt.sendData("stop");
                        break;
                }
                return true;
            }
        });
        /*up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select % 2 == 1) {
                   *//* myview.gyroY += 15;
                    myview.invalidate();*//*
                    if (bt.mTmp != "\n")
                        bt.sendData("F");
                }
            }
        });*/
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (bt.mTmp != "\n")
                            bt.sendData("F");
                        break;
                    case MotionEvent.ACTION_UP:
                        if (bt.mTmp != "\n")
                            bt.sendData("stop");
                        break;
                }
                return true;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* myview.gyroX = 0;
                myview.gyroY = 0;
                myview.invalidate();*/
                if (bt.mTmp != "\n")
                    bt.sendData("stop");
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
        Intent intent;
        if (item.getItemId() != R.id.passive)
            finish();
        switch (item.getItemId()) {
            case R.id.auto:
                intent = new Intent(this, Auto.class);
                intent.putExtra("CARY", bt.mTmp);
                startActivity(intent);
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


    //자이로 센서 부분
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_GYROSCOPE && select % 2 == 0) {
           /* myview.gyroX += Math.round(event.values[0] * 100);
            myview.gyroY += Math.round(event.values[1] * 100);
            myview.gyroZ += Math.round(event.values[2] * 100);
            myview.invalidate();*/
            /*System.out.println("gyroX =" + myview.gyroX);
            System.out.println("gyroY =" + myview.gyroY);
            System.out.println("gyroZ =" + myview.gyroZ);*/
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
