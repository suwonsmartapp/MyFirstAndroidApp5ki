package com.example.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Memo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by junsuk on 2017. 3. 6..
 */

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.ViewHolder> {

    // EventBus 용 이벤트
    public static class ItemClickEvent {
        public ItemClickEvent(int position, long id) {
            this.position = position;
            this.id = id;
        }

        public int position;
        public long id;
    }

    private List<Memo> mData;

    public MemoRecyclerAdapter(List<Memo> memoList) {
        mData = memoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰를 새로 만들 때
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memo, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 데이터
        final Memo memo = mData.get(position);

        // 화면에 뿌리기
        holder.titleTextView.setText(memo.getTitle());
        holder.contentTextView.setText(memo.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MemoActivity#onItemClick
                EventBus.getDefault().post(new ItemClickEvent(position, memo.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void swap(List<Memo> memoList) {
        mData = memoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            // 레이아웃 들고 오기
            TextView titleTextView = (TextView) itemView.findViewById(R.id.title_text);
            TextView contentTextView = (TextView) itemView.findViewById(R.id.content_text);

            // 뷰 홀더에 넣는다
            this.titleTextView = titleTextView;
            this.contentTextView = contentTextView;
        }
    }
}
