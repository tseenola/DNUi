package com.tenseenola.dnui.lsn3;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tenseenola.dnui.R;

public class TestLsn3Activity extends AppCompatActivity {

    private CircleProgress mCircleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lsn3);
        mCircleProgress = findViewById(R.id.CircleProgress);
        mCircleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1;i<=100;i++){
                            mCircleProgress.setProgress(i);
                            SystemClock.sleep(100);
                        }
                    }
                }).start();
            }
        });
    }
}