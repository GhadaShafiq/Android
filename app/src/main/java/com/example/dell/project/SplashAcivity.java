package com.example.dell.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class SplashAcivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashAcivity.this,MainActivity.class);
                SplashAcivity.this.startActivity(mainIntent);
                SplashAcivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
