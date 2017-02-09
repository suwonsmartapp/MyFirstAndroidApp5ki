package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class IntentExamActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE = 1000;
    private EditText mIdEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_exam);

        mIdEditText = (EditText) findViewById(R.id.id_edit);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit);

        findViewById(R.id.login_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, IntentExam2Activity.class);
        intent.putExtra("id", mIdEditText.getText().toString());
        intent.putExtra("password", mPasswordEditText.getText().toString());

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            String text = data.getStringExtra("result");
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }
}
