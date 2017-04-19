package com.example.myapplication.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.services.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by junsuk on 2017. 3. 9..
 */

public class MusicControllerFragment extends Fragment implements View.OnClickListener {

    private ImageView mAlbumImageView;
    private TextView mTitleTextView;
    private TextView mArtistTextView;
    private Button mPlayButton;

    private MusicService mService;
    private boolean mBound = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_controller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAlbumImageView = (ImageView) view.findViewById(R.id.album_image);
        mTitleTextView = (TextView) view.findViewById(R.id.title_text);
        mArtistTextView = (TextView) view.findViewById(R.id.artist_text);

        mPlayButton = (Button) view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(this);

        view.findViewById(R.id.prev_music_button).setOnClickListener(this);
        view.findViewById(R.id.next_music_button).setOnClickListener(this);
    }

    private void updateMetaData(MediaMetadataRetriever retriever) {
        if (retriever != null) {
            String title = retriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_TITLE));
            String artist = retriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_ARTIST));

            // 오디오 앨범 자켓 이미지
            byte albumImage[] = retriever.getEmbeddedPicture();
            if (null != albumImage) {
                Glide.with(this).load(albumImage).into(mAlbumImageView);
            } else {
                Glide.with(this).load(R.mipmap.ic_launcher).into(mAlbumImageView);
            }

            mTitleTextView.setText(title);
            mArtistTextView.setText(artist);
        }
    }

    @Subscribe
    public void updateUI(Boolean isPlaying) {
        mPlayButton.setText(isPlaying ? "중지" : "재생");
        updateMetaData(mService.getMetaDataRetriever());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MusicService.class);
        switch (v.getId()) {
            case R.id.play_button:
                intent.setAction(MusicService.ACTION_RESUME);
                break;
            case R.id.prev_music_button:
                intent.setAction(MusicService.ACTION_PREV);
                break;
            case R.id.next_music_button:
                intent.setAction(MusicService.ACTION_NEXT);
                break;
        }
        getActivity().startService(intent);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            // UI 갱신
            updateUI(mService.isPlaying());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
