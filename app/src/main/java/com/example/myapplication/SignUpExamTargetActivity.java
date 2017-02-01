package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SignUpExamTargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_exam_target);

        if (getIntent() != null) {
            String id = getIntent().getStringExtra("id");
            String pass = getIntent().getStringExtra("pass");
            String email = getIntent().getStringExtra("email");
            String gender = getIntent().getStringExtra("gender");

            String result = String.format("아이디 : %s\n비밀번호 : %s\n이메일 : %s     성별 : %s",
                    id,
                    pass,
                    email,
                    gender);

            TextView resultTextView = (TextView) findViewById(R.id.result_text);
            resultTextView.setText(result);
        }

        findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
