package com.example.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Memo;

public class Memo2Activity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private ImageView mImageView;

    private String mImageUri;

    private long mId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        mImageView = (ImageView) findViewById(R.id.appbar_image);
        mTitleEditText = (EditText) findViewById(R.id.title_edit);
        mContentEditText = (EditText) findViewById(R.id.content_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 타이틀 없애기
        getSupportActionBar().setTitle("");

        if (getIntent() != null) {
            if (getIntent().hasExtra("memo")) {
                // 보여주기

                mId = getIntent().getLongExtra("id", -1);
                mImageUri = getIntent().getStringExtra("image");
                if (mImageUri != null) {
                    Glide.with(this).loadFromMediaStore(Uri.parse(mImageUri)).into(mImageView);
                }

                Memo memo = (Memo) getIntent().getSerializableExtra("memo");
                mTitleEditText.setText(memo.getTitle());
                mContentEditText.setText(memo.getContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_memo2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true;
            case R.id.action_cancel:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void save() {
        Intent intent = new Intent();
        intent.putExtra("title", mTitleEditText.getText().toString());
        intent.putExtra("content", mContentEditText.getText().toString());
        intent.putExtra("id", mId);
        intent.putExtra("image", mImageUri);
        int position = getIntent().getIntExtra("position", -1);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
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

            mImageUri = uri.toString();

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
