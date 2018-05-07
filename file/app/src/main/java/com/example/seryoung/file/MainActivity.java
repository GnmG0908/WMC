package com.example.seryoung.file;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView aa;
        aa= (TextView) findViewById(R.id.saaa);
        aa.setText(getFilesDir()+"");
    }

    public void onClick_save(View view) {

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", true));
            bw.write("L: F: R: M:");
            bw.close();

            Toast.makeText(this,"저장완료", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onClick_read(View view) {

        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"test.txt"));
            String readStr = "";
            String str = null;
            while(((str = br.readLine()) != null)){
                readStr += str +"\n";
            }
            br.close();

            Toast.makeText(this, readStr.substring(0, readStr.length()-1), Toast.LENGTH_SHORT).show();

            //클립보드에 복사
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label",readStr.substring(0, readStr.length()-1));
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "주소가 클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show();


        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
