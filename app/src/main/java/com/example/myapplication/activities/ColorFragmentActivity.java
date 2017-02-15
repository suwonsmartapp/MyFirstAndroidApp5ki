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

        // 프래그먼트 생성 세 가지 방법
        // XML에서 프래그먼트 가져오기
        ColorFragment colorFragment = (ColorFragment) getSupportFragmentManager().findFragmentById(R.id.color_frag);
        // 인수전달 1. 퍼블릭 메서드를 통한 인수 전달
        colorFragment.setColor(Color.BLUE);

        // 동적으로 프래그먼트 추가
        ColorFragment colorFragment2 = new ColorFragment();
        // 인수전달 2. Bundle을 setArguments에 전달하는 방법
        Bundle bundle = new Bundle();
        bundle.putInt("color", Color.YELLOW);
        bundle.putString("text", "글자");
        colorFragment2.setArguments(bundle);

        // 인수전달 3. 팩토리 패턴을 활용하여 생성과 동시에 전달
        ColorFragment colorFragment3 = ColorFragment.newInstance(Color.YELLOW);

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
