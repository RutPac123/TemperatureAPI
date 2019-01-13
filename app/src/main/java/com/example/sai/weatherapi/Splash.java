package com.example.sai.weatherapi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private int SPLASH_TIME=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(Splash.this,MainActivity.class);
               startActivity(intent);
               finish();
           }
       },SPLASH_TIME);
    }
}
