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
import org.altbeacon.beacon.service.RssiFilter;

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

    //파일 출력

    //Bluetooth class 선언
    Bluetooth bt = new Bluetooth();
    String test;

    //알고리즘 부
    Cary_algorithm cary_algorithm = null;
    KalmanFilter cary2_algorithm = new KalmanFilter();
    algo algo = new algo();

    //FLAG
    boolean flag = false;
    boolean cnt_flag = false;

    //비콘 부분
    private BeaconManager beaconManager;
    BeaconRegion region;

    //비콘 값 정제
    double frontRssi = 0, rightRssi = 0, leftRssi = 0;

    ImageButton btn;
    TextView ment;
    TextView caryR, caryL, caryF, testname, direction;
    Button btn1;
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

        beaconManager = new BeaconManager(this);

        bt.mTmp = intent.getStringExtra("CARY");
        if (bt.mTmp == "\n") {
            bt.connectToSelectedDevice(bt.mTmp);
            bt.connectToSelectedDevice(bt.mTmp);
        }
        ment = (TextView) findViewById(R.id.ment);
        btn = (ImageButton) findViewById(R.id.onoff);
        caryF = (TextView) findViewById(R.id.caryF);
        caryL = (TextView) findViewById(R.id.caryL);
        caryR = (TextView) findViewById(R.id.caryR);
        testname = (TextView) findViewById(R.id.testname);
        direction = (TextView) findViewById(R.id.direction);
        btn1 = (Button) findViewById(R.id.save);

        testname.setText("Name:" + bt.mTmp);
        beaconManager.setBackgroundScanPeriod(200, 0);
        beaconManager.setForegroundScanPeriod(200, 0);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                if (!beacons.isEmpty()) {
                    Beacon caryf = null, caryr = null, caryl = null;
                    //Beacon nearestBeacon = beacons.get(0);

                    //비콘 매칭
                    for (int i = 0; i < beacons.size(); i++) {
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:DA:A9]"))
                            caryf = beacons.get(i);
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:B3:F7]"))
                            caryr = beacons.get(i);
                        if (beacons.get(i).getMacAddress().toString().equals("[D4:36:39:D8:B3:0B]"))
                            caryl = beacons.get(i);
                    }
                    if (caryf != null) {
                        frontRssi = caryf.getRssi();
                        //frontRssi=);
                        caryF.setText("CaryFront \n " + frontRssi);
                    }
                    if (caryr != null) {
                        rightRssi = caryr.getRssi();
                        caryR.setText("CaryRight \n " + rightRssi);
                    }
                    if (caryl != null) {
                        //leftRssi = caryl.getRssi();
                        leftRssi=algo.applyFilter(caryl.getRssi());
                        caryL.setText("CaryLeft \n " + leftRssi);
                    }
                    if (caryf != null && caryl != null && caryr != null) {
                        flag = true;
                       /*
                            bt.sendData(cary_algorithm.value_refine(leftRssi, frontRssi, rightRssi));
                            direction.setText(cary_algorithm.value_refine(leftRssi, frontRssi, rightRssi));
                        */
                        if (mode == true) {
                            //블루투스 송신 알고리즘
                            /*if (cnt_flag == false) {
                                cary_algorithm = new Cary_algorithm(leftRssi, frontRssi, rightRssi);
                                cnt_flag = true;
                            }
                            //알고리즘 첨부 부분
                            bt.sendData(cary_algorithm.value_refine(leftRssi,frontRssi,rightRssi));
                            direction.setText(cary_algorithm.value_refine(leftRssi,frontRssi,rightRssi));*/

                            //이하 삭제 부분
                            //정면 강할시
                            if (frontRssi >= rightRssi && frontRssi >= leftRssi) {
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
                            }
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", true));
                                bw.write("L:" + leftRssi + "F:" + frontRssi + "R:" + rightRssi);
                                bw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        flag = false;
                    }
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //자율주행 실행
                if (mode == false) {
                    if (flag == true) {
                        mode = true;
                        ment.setText("자율주행을 종료할 경우 전원버튼을 눌러주세요.");
                        btn.setImageResource(R.drawable.blue_start);
                    } else
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
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "test.txt"));
                    String readStr = "";
                    String str = null;
                    while (((str = br.readLine()) != null)) {
                        readStr += str + "\n";
                    }
                    br.close();

                    //클립보드에 복사
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", readStr.substring(0, readStr.length() - 1));
                    clipboard.setPrimaryClip(clip);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            region = new BeaconRegion("iBeacon", UUID.fromString("74278bda-b644-4520-8f0c-720eaf059935"), 4660, 64001);//본인이 연결할 비콘의 아이디와 메이저(0x1234)/마이너(0xfa01 코드를 알아야 한다.);
        } catch (Exception e) {
        }
    }

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

    @Override
    protected void onPause() {
        super.onPause();
    }

    //옵션 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //옵션 메뉴
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