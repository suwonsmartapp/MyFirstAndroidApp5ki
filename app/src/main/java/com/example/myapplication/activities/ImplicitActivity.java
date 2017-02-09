package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class ImplicitActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPhoneEditText;
    private EditText mUrlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mPhoneEditText = (EditText) findViewById(R.id.phone_edit);
        mUrlEditText = (EditText) findViewById(R.id.url_edit);

        findViewById(R.id.phone_button).setOnClickListener(this);
        findViewById(R.id.url_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_button:
                String phoneNumber = mPhoneEditText.getText().toString();
                dialPhone(phoneNumber);
                break;
            case R.id.url_button:
                String url = mUrlEditText.getText().toString();
                showUrl(url);
                break;
        }
    }

    private void showUrl(String url) {
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void dialPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "수행할 앱이 없어요", Toast.LENGTH_SHORT).show();
        }
    }
}
