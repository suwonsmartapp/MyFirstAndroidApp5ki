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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.services.MusicService;

import java.util.Locale;

/**
 * Created by junsuk on 2017. 3. 9..
 */

public class PlayerFragment extends Fragment {

    private MusicService mService;
    private boolean mBound = false;

    private ImageView mAlbumImageView;
    private TextView mDurationTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAlbumImageView = (ImageView) view.findViewById(R.id.album_image);
        mDurationTextView = (TextView) view.findViewById(R.id.duration_text);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
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

    private void updateUI(boolean playing) {
        if (playing) {
            MediaMetadataRetriever retriever = mService.getMetaDataRetriever();
            if (retriever != null) {
                // ms값
                int longDuration = mService.getDuration();

                int min = longDuration / 1000 / 60;
                int sec = longDuration / 1000 % 60;

                mDurationTextView.setText(String.format(Locale.KOREA, "%d:%02d", min, sec));

                // 오디오 앨범 자켓 이미지
                byte albumImage[] = retriever.getEmbeddedPicture();
                if (null != albumImage) {
                    Glide.with(this).load(albumImage).into(mAlbumImageView);
                } else {
                    Glide.with(this).load(R.mipmap.ic_launcher).into(mAlbumImageView);
                }

            }
        }
    }


}
