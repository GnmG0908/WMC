package com.example.jaehyung.seniorproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class MainActivity extends Activity {

    static  final  int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount= 0;
    Set<BluetoothDevice> mBluetoothAdapterSet;
    BluetoothAdapter mBluetoothAdapter;
    Button conbtn;
    BluetoothDevice mRemoteDevie;
    BluetoothSocket mSocket= null;
    OutputStream mOutputStream= null;
    InputStream mInputStream = null;
    String mStrDlimiter = "\n";
    char mCharDlimiter = '\n';

    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;

    int BackButtonCounter =0;   // 뒤로가기 키 제어

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackButtonCounter =0;
        setContentView(R.layout.activity_main);
        conbtn=(Button)findViewById(R.id.conbtn);
        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
            }
        });
    }

    BluetoothDevice getDeviceFromBondedList(String name){

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.auto:
                startActivity(new Intent(MainActivity.this,Auto.class));
                break;
            case R.id.passive:
                startActivity(new Intent(MainActivity.this,Passive.class));
                break;
            case R.id.secure:
                startActivity(new Intent(MainActivity.this,Secure.class));
                break;
            case R.id.voice:
                startActivity(new Intent(MainActivity.this,Voice.class));
                break;
            case R.id.main:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (BackButtonCounter ==1)
            finish();
        Toast.makeText(this,"한번 더 뒤로가기를 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
        BackButtonCounter++;
    }
}
