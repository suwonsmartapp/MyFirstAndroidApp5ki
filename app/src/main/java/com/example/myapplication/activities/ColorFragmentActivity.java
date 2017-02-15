package com.example.myapplication.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.fragments.ColorFragment;

public class ColorFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_fragment);

        // XML에서 프래그먼트 가져오기
        ColorFragment colorFragment = (ColorFragment) getSupportFragmentManager().findFragmentById(R.id.color_frag);
        colorFragment.setColor(Color.BLUE);

        // 동적으로 프래그먼트 추가
        ColorFragment colorFragment2 = new ColorFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("color", Color.YELLOW);
        bundle.putString("text", "글자");

        colorFragment2.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, colorFragment2)
                .commit();

    }

    public void onClick(View view) {
        ColorFragment newColorFragment = new ColorFragment();
        int color = Color.YELLOW;
        newColorFragment.setColor(color);
        // 기존의 프래그먼트를 교체
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, newColorFragment)
                .commit();
    }
}
