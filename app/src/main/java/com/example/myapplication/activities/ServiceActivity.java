package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.services.MyIntentService;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    public void onStartIntentService(View view) {
        // 1. 순차적으로 실행 된다
        // 2. Worker 스레드로 실행된다
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction("play");
        intent.putExtra("path", "file://dfadsf");
        startService(intent);
    }
}
