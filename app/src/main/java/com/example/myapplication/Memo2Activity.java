package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.myapplication.models.Memo;

public class Memo2Activity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;

    private long mId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo2);

        mTitleEditText = (EditText) findViewById(R.id.title_edit);
        mContentEditText = (EditText) findViewById(R.id.content_edit);

        if (getIntent() != null) {
            if (getIntent().hasExtra("memo")) {
                // 보여주기

                mId = getIntent().getLongExtra("id", -1);

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
        setResult(RESULT_OK, intent);
        finish();
    }
}
