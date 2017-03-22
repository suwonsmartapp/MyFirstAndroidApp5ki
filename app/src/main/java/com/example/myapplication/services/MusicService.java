package com.example.myapplication.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.myapplication.R;
import com.example.myapplication.activities.MusicPlayerActivity;

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

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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

            // foreground service
            showNotification();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showNotification() {
        String title = mRetriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_TITLE));
        String artist = mRetriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_ARTIST));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentTitle(title);
//        builder.setContentText(artist);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_view);
        remoteViews.setTextViewText(R.id.title_text, title);
        remoteViews.setTextViewText(R.id.artist_text, artist);

        builder.setCustomContentView(remoteViews);

        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher);

//        builder.setLargeIcon(bitmap);

        remoteViews.setImageViewBitmap(R.id.image_view, bitmap);

        // 알림을 클릭하면 수행될 인텐트
        Intent resultIntent = new Intent(this, MusicPlayerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // 클릭하면 날리기
        builder.setAutoCancel(true);

        // 색상
        builder.setColor(Color.YELLOW);

        // 기본 알림음
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        // 진동
        builder.setVibrate(new long[]{100, 200, 300});

        Intent stopIntent = new Intent(this, MusicService.class);
        stopIntent.setAction(ACTION_RESUME);
        PendingIntent stopPendingIntent = PendingIntent.getService(this,
                1, stopIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 액션
//        builder.addAction(R.mipmap.ic_launcher, "중지", stopPendingIntent);
//        builder.addAction(R.mipmap.ic_launcher, "다음곡", pendingIntent);
//        builder.addAction(R.mipmap.ic_launcher, "이전곡", pendingIntent);

        // 알림 표시
        startForeground(1, builder.build());
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
