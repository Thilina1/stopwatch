package com.androiddev.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * @param
 *
 */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            /**
             * This is FlashScreen 4 seconds stay on screen
             * After 4 seconds move to LoginActivity.class
             */
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                finish();
            }
        }, 4000);   //4 seconds stay on screen
    }
}