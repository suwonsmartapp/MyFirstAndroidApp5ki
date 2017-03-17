package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.MyUtils;

public class CustomDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_design);
    }

    public void showToast(View view) {
        MyUtils.makeText(this, "나만의 토스트 ㅋㅋㅋ", Toast.LENGTH_SHORT).show();
    }
}
