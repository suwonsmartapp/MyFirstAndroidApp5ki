package com.example.myapplication.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals("play")) {
            Log.d(TAG, "play");
            String path = intent.getStringExtra("path");

        } else {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    Log.d(TAG, "onHandleIntent: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
