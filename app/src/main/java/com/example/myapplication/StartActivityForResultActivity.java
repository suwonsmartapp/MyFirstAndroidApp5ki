package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivityForResultActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = StartActivityForResultActivity.class.getSimpleName();
    public static final int REQUEST_CODE_EXAMPLE = 1000;
    public static final int REQUEST_CODE_NEW_MEMO = 2000;
    public static final int REQUEST_CODE_UPDATE_MEMO = 3000;
    private EditText mValueEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_for_result);

        mValueEditText = (EditText) findViewById(R.id.value_edit);

        findViewById(R.id.submit_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, TargetActivity.class);
        intent.putExtra("value", mValueEditText.getText().toString());
//        startActivity(intent);
        // 주거니 받거니
        startActivityForResult(intent, REQUEST_CODE_EXAMPLE);
    }

    // 받을 때 호출 되는 콜백 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EXAMPLE && resultCode == RESULT_OK && data != null) {
            Log.d(TAG, "onActivityResult: " + requestCode);
            Log.d(TAG, "onActivityResult: " + resultCode);
            Log.d(TAG, "onActivityResult: " + data);
            String result = data.getStringExtra("result");
            int value = data.getIntExtra("int", -1);
            Toast.makeText(this, result + ", int: " + value, Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_NEW_MEMO) {

        } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {

        }
    }
}
