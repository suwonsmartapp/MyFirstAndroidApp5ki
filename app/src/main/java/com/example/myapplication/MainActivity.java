package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int QUANTITY_MIN = 0;
    public static final int QUANTITY_MAX = 10;
    public static final int COFFEE_PRICE = 3000;
    public static final int CREAM_PRICE = 500;

    private TextView mQuantityTextView;
    private TextView mResultTextView;
    private CheckBox mCreamCheckBox;
    private EditText mCommentEditText;

    private Chronometer mChronometer;

    // 수량
    private int mQuantity;

    // 휘핑크림
    private boolean mIsCream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 초기화
        init();


        // 레이아웃 표시
        setContentView(R.layout.activity_coffee);

        mChronometer = (Chronometer) findViewById(R.id.timer);
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                Log.d(TAG, "onChronometerTick: " + chronometer.getText().toString());
                sound();
            }
        });

        // 레이아웃에서 특정 id를 인스턴스 변수와 연결
        mQuantityTextView = (TextView) findViewById(R.id.quantity_text);
        mResultTextView = (TextView) findViewById(R.id.result_text);
        mCreamCheckBox = (CheckBox) findViewById(R.id.cream_check);
        mCommentEditText = (EditText) findViewById(R.id.comment_edit);

        // 무명클래스
        findViewById(R.id.minus_button).setOnClickListener(this);
        findViewById(R.id.plus_button).setOnClickListener(this);
        findViewById(R.id.order_button).setOnClickListener(this);

        mCreamCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsCream = isChecked;
                displayResult();
            }
        });
    }

    private void displayResult() {
        mQuantityTextView.setText("" + mQuantity);

        int total = COFFEE_PRICE * mQuantity;

        if (mIsCream) {
            total += CREAM_PRICE;
        } else {
            total -= CREAM_PRICE;
        }

        String result = String.format("가격 : %d원\n수량 : %d개\n휘핑크림 : %s\n감사합니다",
                total,
                mQuantity,
                mIsCream);
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


                // OrderCheckActivity 화면을 시작
                Intent intent = new Intent(this, OrderCheckActivity.class);
                // 데이터 담기
                intent.putExtra("result", message);
                intent.putExtra("comment", mCommentEditText.getText().toString());
                startActivity(intent);
                break;
        }

    }

    // 메뉴를 붙이는 부분
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                Toast.makeText(this, "설정 아직 미구현", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_menu2:
                sound();
                return true;
            case R.id.action_menu3:
                startChronometer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startChronometer() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    private void sound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.monkey);
        mediaPlayer.start();
    }
}
