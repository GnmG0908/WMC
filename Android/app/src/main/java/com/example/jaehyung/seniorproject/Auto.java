package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Auto extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto);
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
                break;
            case R.id.passive:
                startActivity(new Intent(Auto.this,Passive.class));
                break;
            case R.id.secure:
                startActivity(new Intent(Auto.this,Secure.class));
                break;
            case R.id.voice:
                startActivity(new Intent(Auto.this,Voice.class));
                break;
            case R.id.main:
                startActivity(new Intent(Auto.this,MainActivity.class));
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
