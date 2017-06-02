package com.aectann.bh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Thread logoTimer = new Thread() {
            @Override
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 1200) {
                        sleep(50);
                        logoTimer = logoTimer + 100;
                    }
                    ;
                    startActivity(new Intent("com.wazup.CLEARSCREEN"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        logoTimer.start();
    }
}
