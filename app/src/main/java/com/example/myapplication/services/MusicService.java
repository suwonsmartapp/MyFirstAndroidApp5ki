package com.example.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

/**
 * Created by junsuk on 2017. 3. 16..
 */

public class MusicService extends Service {
    public static String ACTION_PLAY = "play";
    public static String ACTION_PAUSE = "pause";

    private MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_PLAY.equals(action)) {
            playMusic((Uri) intent.getParcelableExtra("uri"));
        } else if (ACTION_PAUSE.equals(action)) {

        }
        return START_STICKY;
    }

    @Subscribe
    public void playMusic(Uri uri) {
        try {
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();

                    /**
                     * {@link com.example.myapplication.fragments.MusicControllerFragment#updatePlayButton(Boolean)}
                     */
                    EventBus.getDefault().post(isPlaying());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Subscribe
    public void clickPlayButton(View v) {
        if (isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }

        /**
         * {@link com.example.myapplication.fragments.MusicControllerFragment#updatePlayButton(Boolean)}
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
        return null;
    }
}
