package com.jasim.mobileappdevelop.icare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent theIntent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(theIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
