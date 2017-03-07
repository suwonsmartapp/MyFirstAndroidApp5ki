package com.example.myapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.receiver.MyLocalReceiver;

/**
 * 매니페스트에 리시버를 등록 하지 않는다
 * onStart(), onStop() 코드로 리시버 등록, 해제한다
 */
public class BroadcastReceiverActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mReceiver = new MyLocalReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction("com.example.myapplication.broadcast.ACTION_TEST");

        // 리시버 등록
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // 리시버 해제
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {
        // 나만의 액션을 쏘기
        Intent intent = new Intent("com.example.myapplication.broadcast.ACTION_TEST");
        // 순서없는
        sendBroadcast(intent);

        // 순서있는
//        sendOrderedBroadcast()
    }
}
