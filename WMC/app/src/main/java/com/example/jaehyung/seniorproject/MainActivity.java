package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int BackButtonCounter =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackButtonCounter =0;
        setContentView(R.layout.activity_main);
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
