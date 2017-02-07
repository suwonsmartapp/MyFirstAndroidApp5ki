package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
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
        // convertView : 재사용 되는 뷰
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_weather, parent, false);
        }

        // 레이아웃 들고 오기
        ImageView imageView = (ImageView) convertView.findViewById(R.id.weather_image);
        TextView locationTextView = (TextView) convertView.findViewById(R.id.location_text);
        TextView temperatureTextView = (TextView) convertView.findViewById(R.id.temperature_text);

        // 데이터
        Weather weather = mData.get(position);

        // 화면에 뿌리기
        imageView.setImageResource(weather.getImageRes());
        locationTextView.setText(weather.getLocation());
        temperatureTextView.setText(weather.getTemperature());

        return convertView;
    }
}
