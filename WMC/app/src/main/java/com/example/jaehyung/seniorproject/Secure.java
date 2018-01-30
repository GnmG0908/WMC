package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Secure extends AppCompatActivity {

    Button OnBtn;
    Button OffBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secure);
        OnBtn=(Button)findViewById(R.id.OnBtn);
        OnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"보안모드가 켜집니다.",Toast.LENGTH_SHORT).show();
            }
        });
        OffBtn=(Button)findViewById(R.id.OffBtn);
        OffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"보안모드가 꺼집니다.",Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            case R.id.auto:
                startActivity(new Intent(Secure.this,Auto.class));
                break;
            case R.id.passive:
                startActivity(new Intent(Secure.this,Passive.class));
                break;
            case R.id.secure:
                break;
            case R.id.voice:
                startActivity(new Intent(Secure.this,Voice.class));
                break;
            case R.id.main:
                startActivity(new Intent(Secure.this,MainActivity.class));
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
}
