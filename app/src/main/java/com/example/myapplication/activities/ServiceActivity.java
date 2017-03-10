package com.example.myapplication.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.services.MyIntentService;
import com.example.myapplication.services.MyService;

public class ServiceActivity extends AppCompatActivity {

    private MyService mService;
    boolean mBound = false;


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

    public void onStartService(View view) {
        // 1. Main스레드에서 돈다. 그래서 Thread를 별도 생성해서 실행해야 한다
        // 2. 병렬 실행 가능
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void onBindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void getNumber(View view) {
        if (mBound) {
            Toast.makeText(this, "" + mService.getTime(), Toast.LENGTH_SHORT).show();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 바인드 성공
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mService = binder.getService();
            Toast.makeText(ServiceActivity.this, "바인드 성공", Toast.LENGTH_SHORT).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 강제 종료시에 호출
            mBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 바인딩 끊기
        if (mBound) {
            unbindService(mConnection);
        }
    }
}
