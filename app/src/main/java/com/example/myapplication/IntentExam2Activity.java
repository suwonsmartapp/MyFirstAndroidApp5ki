package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class IntentExam2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_exam2);

        if (getIntent() != null) {
            String id = getIntent().getStringExtra("id");
            String password = getIntent().getStringExtra("password");

            Toast.makeText(this, "id = " + id + ", pw = " + password, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        String text = ((Button) view).getText().toString();

        Intent intent = new Intent();
        intent.putExtra("result", text);

        setResult(RESULT_OK, intent);
        finish();
    }
}
