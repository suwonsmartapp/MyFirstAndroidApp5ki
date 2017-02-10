package com.example.myapplication.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

public class LifeCycleActivity extends AppCompatActivity {

    private static final String TAG = LifeCycleActivity.class.getSimpleName();

    private TextView mScoreTextView;
    private int mScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        mScoreTextView = (TextView) findViewById(R.id.score_text);

        Log.d(TAG, "onCreate: ");
        
        // 복원 여기도 되고 (null 체크 필요)
//        if (savedInstanceState != null) {
//            mScore = savedInstanceState.getInt("score");
//            setScore(mScore);
//        }

        // 읽어오기
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mScore = sharedPreferences.getInt("score", 0);
        setScore(mScore);
    }

    // 복원 여기도 됨
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 복원 (null 체크 불필요)
        mScore = savedInstanceState.getInt("score");
        setScore(mScore);
    }

    private void setScore(int score) {
        mScoreTextView.setText("점수 : " + score);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");

        // 저장
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score", mScore);
        editor.apply();     // 비동기 (async)
//        editor.commit()   // 동기 (sync)
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    // 인스턴스 상태 저장
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        
        outState.putInt("score", mScore);
    }

    public void onClick(View view) {
        mScore += 100;
        setScore(mScore);
    }
}
