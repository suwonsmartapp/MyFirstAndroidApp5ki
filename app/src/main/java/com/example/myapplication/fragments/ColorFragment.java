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

    private int mColor = Color.WHITE;

    // 필수
    public ColorFragment() {
    }

    public static ColorFragment newInstance(int color) {
        ColorFragment colorFragment = new ColorFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        colorFragment.setArguments(bundle);
        return colorFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color, container, false);

        Bundle bundle = getArguments();
        int color = bundle.getInt("color");
        mColor = color;
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
