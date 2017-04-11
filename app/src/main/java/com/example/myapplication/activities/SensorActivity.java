package com.example.myapplication.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;

public class SensorActivity extends AppCompatActivity
        implements SensorEventListener {

    private static final String TAG = SensorActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mLightSensor;
    private View mRoot;
    private float mCurrentLux = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mRoot = findViewById(R.id.root);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 센서 이벤트 등록
        mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 센서 이벤트 해제
        mSensorManager.unregisterListener(this);
    }

    private long mPrevTime = System.currentTimeMillis();

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 1초에 한번씩
        long time = System.currentTimeMillis();
        if (time - mPrevTime < 1000) {
            return;
        }
        mPrevTime = time;

        // 조도
        float lux = event.values[0];

        getSupportActionBar().setTitle("조도 : " + lux + " lx");

        // 부드럽게 전환되는 Drawable
        ColorDrawable[] drawables = new ColorDrawable[]{
                new ColorDrawable(color(mCurrentLux)),
                new ColorDrawable(color(lux))
        };
        TransitionDrawable drawable = new TransitionDrawable(drawables);
        drawable.startTransition(1000);

        mRoot.setBackground(drawable);

        mCurrentLux = lux;
    }

    private int color(float lux) {
        if (lux < 255f / 7f) {
            return Color.MAGENTA;
        } else if (lux < 255f / 7f * 2f) {
            return Color.parseColor("#4b0082");
        } else if (lux < 255f / 7f * 3f) {
            return Color.BLUE;
        } else if (lux < 255f / 7f * 4f) {
            return Color.GREEN;
        } else if (lux < 255f / 7f * 5f) {
            return Color.YELLOW;
        } else if (lux < 255f / 7f * 6f) {
            return Color.parseColor("#ff8c00");
        }
        return Color.RED;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
