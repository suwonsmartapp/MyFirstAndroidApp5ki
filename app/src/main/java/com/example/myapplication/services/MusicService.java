package com.example.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by junsuk on 2017. 3. 16..
 */

public class MusicService extends Service {
    public static String ACTION_PLAY = "play";
    public static String ACTION_RESUME = "resume";

    private MediaPlayer mMediaPlayer;
    private MediaMetadataRetriever mRetriever;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_PLAY.equals(action)) {
            playMusic((Uri) intent.getParcelableExtra("uri"));
        } else if (ACTION_RESUME.equals(action)) {
            clickResumeButton();
        }
        return START_STICKY;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void playMusic(Uri uri) {
        try {
            // 현재 재생중인 정보
            mRetriever = new MediaMetadataRetriever();
            mRetriever.setDataSource(this, uri);

            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    /**
                     * {@link com.example.myapplication.fragments.MusicControllerFragment#updateUI(Boolean)}
                     */
                    EventBus.getDefault().post(isPlaying());
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MediaMetadataRetriever getMetaDataRetriever() {
        return mRetriever;
    }

    private void clickResumeButton() {
        if (isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }

        /**
         * {@link com.example.myapplication.fragments.MusicControllerFragment#updateUI(Boolean)}
         */
        EventBus.getDefault().post(isPlaying());
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MusicService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MusicService.this;
        }
    }
}
