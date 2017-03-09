package com.example.myapplication.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Memo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by junsuk on 2017. 3. 6..
 */

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.ViewHolder> {

    private final Context mContext;

    // EventBus 용 이벤트
    public static class ItemClickEvent {
        public ItemClickEvent(View imageView, View titleView, View contentView, int position, long id) {
            this.position = position;
            this.id = id;
            this.imageView = imageView;
            this.titleView = titleView;
            this.contentView = contentView;
        }

        public int position;
        public long id;
        public View imageView;
        public View titleView;
        public View contentView;
    }

    private List<Memo> mData;

    public MemoRecyclerAdapter(Context context, List<Memo> memoList) {
        mContext = context;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // 데이터
        final Memo memo = mData.get(position);

        // 화면에 뿌리기
        holder.titleTextView.setText(memo.getTitle());
        holder.contentTextView.setText(memo.getContent());

        if (memo.getImagePath() != null) {
            Glide.with(mContext).load(memo.getImagePath()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MemoActivity#onItemClick
                EventBus.getDefault().post(new ItemClickEvent(holder.imageView,
                        holder.titleTextView,
                        holder.contentTextView,
                        position,
                        memo.getId()));
            }
        });
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void swap(List<Memo> memoList) {
        mData = memoList;
        notifyDataSetChanged();
    }

    public void insert(List<Memo> memoList) {
        mData = memoList;
        notifyItemInserted(0);
    }

    public void update(List<Memo> memoList, int position) {
        mData = memoList;
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView contentTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            // 레이아웃 들고 오기
            TextView titleTextView = (TextView) itemView.findViewById(R.id.title_text);
            TextView contentTextView = (TextView) itemView.findViewById(R.id.content_text);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);

            // 뷰 홀더에 넣는다
            this.titleTextView = titleTextView;
            this.contentTextView = contentTextView;
            this.imageView = imageView;
        }
    }
}
