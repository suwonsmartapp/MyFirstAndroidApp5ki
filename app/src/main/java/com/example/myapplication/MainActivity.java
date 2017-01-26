package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int QUANTITY_MIN = 0;
    public static final int QUANTITY_MAX = 10;
    public static final int COFFEE_PRICE = 3000;

    private TextView mQuantityTextView;
    private TextView mResultTextView;

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
        mQuantityTextView = (TextView) findViewById(R.id.quantity_text);
        mResultTextView = (TextView) findViewById(R.id.result_text);

        // 무명클래스
        findViewById(R.id.minus_button).setOnClickListener(this);
        findViewById(R.id.plus_button).setOnClickListener(this);
        findViewById(R.id.order_button).setOnClickListener(this);

    }

    private void displayResult() {
        mQuantityTextView.setText("" + mQuantity);

        String result = "가격 : " + (COFFEE_PRICE * mQuantity)
                + "원\n감사합니다";
        mResultTextView.setText(result);
    }

    private void init() {
        mQuantity = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus_button:
                mQuantity--;
                if (mQuantity < QUANTITY_MIN) {
                    mQuantity = QUANTITY_MIN;
                }
                displayResult();
                break;
            case R.id.plus_button:
                mQuantity++;
                if (mQuantity > QUANTITY_MAX) {
                    mQuantity = QUANTITY_MAX;
                }
                displayResult();
                break;
            case R.id.order_button:
                String message = mResultTextView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
