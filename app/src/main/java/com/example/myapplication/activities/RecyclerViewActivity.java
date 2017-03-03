package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("data " + i);
        }

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(data);

        recyclerView.setAdapter(adapter);


    }

    /**
     * EventBus 에서 보내는 이벤트 수신하는 콜백 메서드
     * @param event
     */
    @Subscribe
    public void onItemClick(ItemClickEvent event) {
        Toast.makeText(this, "" + event.position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // EventBus에 구독자로 현재 액티비티 추가
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // EventBus에 구독자에서 제거
        EventBus.getDefault().unregister(this);
    }

    /**
     * EventBus 에서 발송할 이벤트
     */
    private static class ItemClickEvent {
        View view;
        int position;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_text);
        }
    }

    private static class MyRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<String> mData;

        public MyRecyclerAdapter(List<String> dataList) {
            mData = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_text, parent, false);
            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // EventBus를 통해 이벤트 발송
                    // RecyclerViewActivity#onItemClick
                    ItemClickEvent event = new ItemClickEvent();
                    event.view = holder.itemView;
                    event.position = position;
                    EventBus.getDefault().post(event);
                }

            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
