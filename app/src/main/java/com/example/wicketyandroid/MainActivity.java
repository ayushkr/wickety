package com.example.wicketyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    long delay=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a01_screen1);





        Timer timer=new Timer();

        TimerTask k=new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent a=new Intent(MainActivity.this,AuthenticationEntry.class);
                startActivity(a);


            }
        };

      timer.schedule(k,delay);

    }
}
