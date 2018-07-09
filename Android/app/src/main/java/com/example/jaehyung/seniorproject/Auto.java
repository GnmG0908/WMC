package com.example.jaehyung.seniorproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.altbeacon.beacon.BeaconConsumer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Auto extends AppCompatActivity implements BeaconConsumer{

    //Bluetooth class 선언
    Bluetooth bt = new Bluetooth();

    //비콘 필터 부
    Filter Lfilter = new Filter();
    Filter Rfilter = new Filter();
    Filter Ffilter = new Filter();

    //알고리즘 부
    int counter=0;
    Algorithm cary_algorithm = new Algorithm();

    //FLAG
    boolean flag = false;

    //비콘 부분
    private BeaconManager beaconManager;
    BeaconRegion region;

    //비콘 값 정제
    double frontRssi = 0, rightRssi = 0, leftRssi = 0;

    //시작 버튼
    ImageButton btn;
    //버튼 밑 조그마한 안내 메세지
    TextView ment;
    //testname = Name: ,direction = Send
    TextView caryR, caryL, caryF, testname, direction;
    boolean mode = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto);

        Intent intent = getIntent();
        //넘겨받은 값
        /*
        "CARY"=페어링 기기로 선택한 블루투스 이름
        */

        //액션바 부분
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_auto);
        getSupportActionBar().setElevation(5);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //비콘 컨트롤 하는 클래스 선언
        beaconManager = new BeaconManager(this);

        //액티비티간 연결되는 블루투스 이름 파라미터
        bt.mTmp = intent.getStringExtra("CARY");
        if (bt.mTmp != "\n") {
            bt.connectToSelectedDevice(bt.mTmp);
            bt.connectToSelectedDevice(bt.mTmp);
        }

        //디자인 포인트 선언
        ment = (TextView) findViewById(R.id.ment);
        btn = (ImageButton) findViewById(R.id.onoff);
        caryF = (TextView) findViewById(R.id.caryF);
        caryL = (TextView) findViewById(R.id.caryL);
        caryR = (TextView) findViewById(R.id.caryR);
        testname = (TextView) findViewById(R.id.testname);
        direction = (TextView) findViewById(R.id.direction);

        //기기 이름 설정
        testname.setText("Name:" + bt.mTmp);
        //비콘 수신 주기 설정
        beaconManager.setBackgroundScanPeriod(200, 0);
        beaconManager.setForegroundScanPeriod(200, 0);
        //비콘 값 읽는 방법 설정
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                if (!beacons.isEmpty()) {
                    Beacon caryf = null, caryr = null, caryl = null;
                    //Beacon nearestBeacon = beacons.get(0);

                    //비콘 매칭
                    for (int i = 0; i < beacons.size(); i++) {
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:DA:A9]"))
                            caryr = beacons.get(i);
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:B3:F7]"))
                            caryf = beacons.get(i);
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:B3:0B]"))
                            caryl = beacons.get(i);
                    }
                    //비콘 F 인식
                    if (caryf != null) {
                        frontRssi = Ffilter.applyFilter(caryf.getRssi());
                        caryF.setText("CaryFront \n " +String.format("%.2f",frontRssi));
                    }
                    //비콘 R 인식
                    if (caryr != null) {
                        rightRssi = Rfilter.applyFilter(caryr.getRssi());
                        caryR.setText("CaryRight \n " + String.format("%.2f",rightRssi));
                    }
                    //비콘 L 인식
                    if (caryl != null) {
                        leftRssi=Lfilter.applyFilter(caryl.getRssi());
                        caryL.setText("CaryLeft \n " +String.format("%.2f",leftRssi));
                    }
                    //필요한 비콘 모두 인식
                    if (caryf != null && caryl != null && caryr != null) {
                        //비콘 On,Off 스위치 플래그
                        flag = true;
                        //비콘 필터 적용시 사용되는 값
                        counter=(Lfilter.counter+Rfilter.counter+Ffilter.counter)/3;
                        direction.setText(cary_algorithm.algorithm(frontRssi,leftRssi,rightRssi));
                        if (mode == true) {
                            //알고리즘 적용
                            bt.sendData(cary_algorithm.algorithm(frontRssi,leftRssi,rightRssi));

                            // 임시방편
                            /*if (rightRssi-leftRssi>-3.5&&rightRssi-leftRssi<3.5){
                                bt.sendData("F");
                                direction.setText("F");
                            }
                            else{
                                if(rightRssi>leftRssi){
                                    bt.sendData("R");
                                    direction.setText("R");
                                }
                                if (rightRssi<leftRssi){
                                    bt.sendData("L");
                                    direction.setText("L");
                                }
                            }*/

                            //기본 처리
                            /*if (frontRssi >= rightRssi && frontRssi >= leftRssi) {
                                bt.sendData("F");
                                direction.setText("F");
                            }
                            //왼쪽 강할시
                            else if (leftRssi > rightRssi) {
                                bt.sendData("L");
                                direction.setText("L");
                            }
                            //오른쪽 강할시
                            else if (rightRssi > leftRssi) {
                                bt.sendData("R");
                                direction.setText("R");
                            }*/
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        });

        //ON OFF 버튼 이벤트
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //자율주행 실행
                if (mode == false) {
                    if (flag == true&&counter==10) {
                        mode = true;
                        ment.setText("자율주행을 종료할 경우 전원버튼을 눌러주세요.");
                        btn.setImageResource(R.drawable.blue_start);
                    }
                    else if(counter<10&&counter>2)
                        Toast.makeText(getApplicationContext(),"센서값 점검중입니다. 잠시만 기다려주세요.",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "비콘이 인식되지 않습니다, \nCARY근처로 이동해 주세요", Toast.LENGTH_LONG).show();
                }
                //자율주행 종료
                else {
                    mode = false;
                    ment.setText("자율주행을 시작할 경우 전원버튼을 눌러주세요.");
                    btn.setImageResource(R.drawable.red_start);
                    bt.sendData("stop");
                }
            }
        });
        try {
            region = new BeaconRegion("iBeacon", UUID.fromString("74278bda-b644-4520-8f0c-720eaf059935"), 4660, 64001);//본인이 연결할 비콘의 아이디와 메이저(0x1234)/마이너(0xfa01 코드를 알아야 한다.);
        } catch (Exception e) {
        }
    }

    //오픈소스
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }
    //오픈소스
    @Override
    protected void onPause() {
        super.onPause();
    }

    //옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //옵션 메뉴 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() != R.id.auto)
            finish();
        switch (item.getItemId()) {
            case R.id.auto:
                break;
            case R.id.passive:
                intent = new Intent(this, Passive.class);
                intent.putExtra("CARY", bt.mTmp);
                startActivity(intent);
                //startActivity(new Intent(Auto.this, Passive.class));
                break;
            case R.id.main:
                //startActivity(new Intent(Auto.this, MainActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //뒤로가기 제어
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //비콘 필수 함수
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Toast.makeText(getApplicationContext(), "접근", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Toast.makeText(getApplicationContext(), "끊어짐", Toast.LENGTH_LONG).show();
            }
        });
    }

}