package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mMinusButton;
    private Button mPlusButton;
    private TextView mQuantityTextView;

    // 수량
    private int mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 초기화
        init();

        // 레이아웃 표시
        setContentView(R.layout.activity_coffee);

        // 레이아웃에서 특정 id를 인스턴스 변수와 연결
        mMinusButton = (Button) findViewById(R.id.minus_button);
        mPlusButton = (Button) findViewById(R.id.plus_button);
        mQuantityTextView = (TextView) findViewById(R.id.quantity_text);

        // 무명클래스
        mMinusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // debug
                Log.d(TAG, "마이너스 버튼 클릭");
                Log.v(TAG, "일반로그");
                Log.e(TAG, "에러로그");
                Log.i(TAG, "정보로그");
                Log.w(TAG, "경고로그");

                // 토스트 메세지
                Toast.makeText(MainActivity.this,
                        "메세지",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        mQuantity = 0;
    }
}
