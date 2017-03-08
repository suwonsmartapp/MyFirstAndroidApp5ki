package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class ScrollingActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        mImageView = (ImageView) findViewById(R.id.appbar_image);

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
    }

    public void onImageClick(View view) {
        // 그림 줘
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            // 그림이 정상적으로 선택되었을 때

            // 사진 경로
            Uri uri = data.getData();
            // 라이브러리
            Glide.with(this).loadFromMediaStore(uri).into(mImageView);


            // 이미지뷰에 bitmap 설정

            // 사진을 bitmap으로 얻기
            // 그냥
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//            mImageView.setImageBitmap(bitmap);
        }
    }
}
