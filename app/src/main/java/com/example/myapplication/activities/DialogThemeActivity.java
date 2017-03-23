package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class DialogThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_theme);

        if (getIntent() != null) {
            String data = getIntent().getStringExtra("data");
            ((TextView) findViewById(R.id.text_view)).setText(data);

            String image = getIntent().getStringExtra("image");
            if (image != null) {
                ((ImageView) findViewById(R.id.image_view)).setImageResource(R.drawable.man);
            }

        }
    }
}
