package com.ipin.mynote;

// 10120164 M HASBI FADILAH , IF4

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
            }
        }, 3000L);

    }
}