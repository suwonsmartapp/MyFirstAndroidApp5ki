package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by junsuk on 2017. 2. 13..
 */

public class TextFragment extends ColorFragment {

    private String mText = "";
    private TextView mTextView;

    public TextFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mTextView = (TextView) view.findViewById(R.id.text_text);
        mTextView.setText(mText);
        return view;
    }

    public void setText(String text) {
        mText = text;

        if (mTextView != null) {
            mTextView.setText(mText);
        }
    }
}
