package com.example.jaehyung.seniorproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Jaehyung on 2017-12-28.
 */

public class Auto extends AppCompatActivity{

    ImageButton btn;
    TextView ment;
    boolean mode = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto);

        //액션바 부분
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_auto);
        getSupportActionBar().setElevation(5);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ment=(TextView)findViewById(R.id.ment);
        btn=(ImageButton)findViewById(R.id.onoff);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //자율주행 실행
                if (mode==false){
                    mode=true;
                    ment.setText("자율주행을 종료할 경우 전원버튼을 눌러주세요.");
                    btn.setImageResource(R.drawable.blue_start);
                }
                //자율주행 종료
                else{
                    mode=false;
                    ment.setText("자율주행을 시작할 경우 전원버튼을 눌러주세요.");
                    btn.setImageResource(R.drawable.red_start);
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
