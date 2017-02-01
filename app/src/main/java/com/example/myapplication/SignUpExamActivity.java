package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUpExamActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int REQUEST_CODE_SIGN_UP = 1000;
    private EditText mIdEditText;
    private EditText mPassEditText;
    private EditText mPass2EditText;
    private EditText mEmailEditText;
    private RadioGroup mGenderGroup;

    private ArrayList<EditText> mEditTextList;
    private String mGenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_exam);

        mEditTextList = new ArrayList<>();

        mIdEditText = (EditText) findViewById(R.id.id_edit);
        mPassEditText = (EditText) findViewById(R.id.pass_edit);
        mPass2EditText = (EditText) findViewById(R.id.pass2_edit);
        mEmailEditText = (EditText) findViewById(R.id.email_edit);

        mEditTextList.add(mIdEditText);
        mEditTextList.add(mPassEditText);
        mEditTextList.add(mPass2EditText);
        mEditTextList.add(mEmailEditText);

        mGenderGroup = (RadioGroup) findViewById(R.id.gender_group);
        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mGenger = ((RadioButton)findViewById(checkedId)).getText().toString();
            }
        });

        findViewById(R.id.reset_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_button:
                reset();
                break;
            case R.id.sign_up_button:
                signUp();
                break;
        }
    }


    private boolean isValid() {
        for (EditText editText : mEditTextList) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                return false;
            }
        }

        if (mGenderGroup.getCheckedRadioButtonId() == -1) {
            return false;
        }

        return true;
    }

    private void signUp() {
        if (!isValid()) {
            Toast.makeText(this, "모두 입력 해 주셔야 합니다", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword()) {
            Toast.makeText(this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
        } else {
            // 가입
            Intent intent = new Intent(this, SignUpExamTargetActivity.class);
            intent.putExtra("id", mIdEditText.getText().toString());
            intent.putExtra("pass", mPassEditText.getText().toString());
            intent.putExtra("email", mEmailEditText.getText().toString());
            intent.putExtra("gender", mGenger);

            startActivityForResult(intent, REQUEST_CODE_SIGN_UP);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_CODE_SIGN_UP && 
                resultCode == RESULT_OK) {
            Toast.makeText(this, "확인 버튼을 누르셨습니다", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword() {
        return mPassEditText.getText().toString().equals(mPass2EditText.getText().toString());
    }

    private void reset() {
        for (EditText editText : mEditTextList) {
            editText.setText("");
        }

        mGenderGroup.clearCheck();
    }
}
