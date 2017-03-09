package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 2017. 3. 9..
 */

public class SongFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        List<Song> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add(new Song("제목 " + i, "아티스트 " + i));
        }
        recyclerView.setAdapter(new SongRecyclerAdapter(data));
    }

    public static class SongRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Song> mData;

        public SongRecyclerAdapter(List<Song> data) {
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Song song = mData.get(position);
            holder.titleTextView.setText(song.getTitle());
            holder.artistTextView.setText(song.getArtist());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView artistTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(android.R.id.text1);
            artistTextView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
