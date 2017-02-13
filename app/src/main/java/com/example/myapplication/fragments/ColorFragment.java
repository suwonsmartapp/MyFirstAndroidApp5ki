package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * Created by junsuk on 2017. 2. 13..
 */

public class ColorFragment extends Fragment {

    private int mColor = Color.RED;

    // 필수
    public ColorFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        view.setBackgroundColor(mColor);
        return view;
    }

    public void setColor(int color) {
        mColor = color;
        if (getView() != null) {
            getView().setBackgroundColor(mColor);
        }
    }
}
