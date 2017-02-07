package com.example.myapplication.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myapplication.models.Weather;

import java.util.List;

/**
 * Created by junsuk on 2017. 2. 7..
 */

public class WeatherAdapter extends BaseAdapter {
    private Context mContext;
    private List<Weather> mData;

    public WeatherAdapter(Context context, List<Weather> data) {
        mContext = context;
        mData = data;
    }

    // 아이템 갯수
    @Override
    public int getCount() {
        return mData.size();
    }

    // position번째 아이템
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    // postion번째 id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // position번째의 레이아웃
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convertView
        return null;
    }
}
