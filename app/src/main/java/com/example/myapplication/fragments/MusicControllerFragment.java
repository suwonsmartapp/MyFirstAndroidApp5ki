package com.example.myapplication.fragments;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
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
    }

    @Subscribe
    public void updateUI(final MediaMetadataRetriever retriever) {
        String title = retriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_TITLE));
        String artist = retriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_ARTIST));

        // 오디오 앨범 자켓 이미지
        byte albumImage[] = retriever.getEmbeddedPicture();
        if (null != albumImage) {
            Glide.with(this).load(albumImage).into(mAlbumImageView);
        }

        mTitleTextView.setText(title);
        mArtistTextView.setText(artist);
    }

    @Subscribe
    public void updatePlayButton(Boolean isPlaying) {
        mPlayButton.setText(isPlaying ? "중지" : "재생");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

        /**
         * {@link com.example.myapplication.activities.MusicPlayerActivity#clickPlayButton(View)}
         */
        EventBus.getDefault().post(v);
    }
}
