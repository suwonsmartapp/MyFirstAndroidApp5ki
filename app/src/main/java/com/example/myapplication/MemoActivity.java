package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.adapters.MemoAdapter;
import com.example.myapplication.models.Memo;

import java.util.ArrayList;
import java.util.List;

public class MemoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MemoActivity.class.getSimpleName();
    public static final int REQUEST_CODE_NEW_MEMO = 1000;
    public static final int REQUEST_CODE_UPDATE_MEMO = 1001;

    private List<Memo> mMemoList;
    private MemoAdapter mAdapter;
    private ListView mMemoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        mMemoListView = (ListView) findViewById(R.id.memo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoActivity.this, Memo2Activity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_MEMO);
            }
        });

        // 데이터
        mMemoList = new ArrayList<>();

        // 어댑터
        mAdapter = new MemoAdapter(mMemoList);

        mMemoListView.setAdapter(mAdapter);

        // 이벤트
        mMemoListView.setOnItemClickListener(this);

        // ContextMenu
        registerForContextMenu(mMemoListView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");

            if (requestCode == REQUEST_CODE_NEW_MEMO) {
                // 새 메모
                mMemoList.add(new Memo(title, content));
            } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {
                long id = data.getLongExtra("id", -1);
                // 수정
                Memo memo = mMemoList.get((int) id);
                memo.setTitle(title);
                memo.setContent(content);
            }
            mAdapter.notifyDataSetChanged();

            Log.d(TAG, "onActivityResult: " + title + ", " + content);
            Toast.makeText(this, "저장 되었습니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "취소 되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Memo memo = mMemoList.get(position);

        Intent intent = new Intent(this, Memo2Activity.class);
        intent.putExtra("id", id);
        intent.putExtra("memo", memo);

        startActivityForResult(intent, REQUEST_CODE_UPDATE_MEMO);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_memo, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteMemo(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteMemo(long id) {
        mMemoList.remove((int) id);
        mAdapter.notifyDataSetChanged();
    }
}
