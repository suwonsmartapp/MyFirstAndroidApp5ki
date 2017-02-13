package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListViewExamActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Integer> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_exam);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        if (getIntent().getSerializableExtra("data") != null) {
            mData = (List<Integer>) getIntent().getSerializableExtra("data");
        } else {
            mData = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                mData.add(i);
            }
        }

        MyAdapter adapter = new MyAdapter(mData);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        // 데이터 뒤집기
        Collections.reverse(mData);

        Intent intent = new Intent(this, ListViewExamActivity.class);
        intent.putExtra("data", (Serializable) mData);
        startActivity(intent);
    }

    private static class MyAdapter extends BaseAdapter {

        private final List<Integer> mData;

        public MyAdapter(List<Integer> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            // 레이아웃
            if (convertView == null) {
                viewHolder = new ViewHolder();

                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_text, parent, false);

                viewHolder.textView = (TextView) convertView.findViewById(R.id.text_text);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // 데이터 표시
            int data = mData.get(position);
            viewHolder.textView.setText("" + data);

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView textView;
    }
}
