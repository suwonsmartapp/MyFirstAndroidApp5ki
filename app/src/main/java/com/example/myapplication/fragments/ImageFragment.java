package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by junsuk on 2017. 2. 14..
 */

public class ImageFragment extends Fragment {

    private OnImageTouchListener mListener;
    private ImageView mImageView;

    public interface OnImageTouchListener {
        void onImageTouch(ImageView view, String message);
    }

    public void setOnImageTouchListener(OnImageTouchListener listener) {
        mListener = listener;
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onImageTouch(mImageView, "임의의 데이터");
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image_view);

        return view;
    }

}
