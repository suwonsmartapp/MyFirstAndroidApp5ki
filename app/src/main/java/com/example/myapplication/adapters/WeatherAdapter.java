package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private static final String TAG = WeatherAdapter.class.getSimpleName();
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
        ViewHolder viewHolder;
        // convertView : 재사용 되는 뷰
        if (convertView == null) {
            viewHolder = new ViewHolder();

            // 뷰를 새로 만들 때
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_weather, parent, false);

            // 레이아웃 들고 오기
            ImageView imageView = (ImageView) convertView.findViewById(R.id.weather_image);
            TextView locationTextView = (TextView) convertView.findViewById(R.id.location_text);
            TextView temperatureTextView = (TextView) convertView.findViewById(R.id.temperature_text);

            // 뷰 홀더에 넣는다
            viewHolder.weatherImage = imageView;
            viewHolder.locationTextView = locationTextView;
            viewHolder.temperatureTextView = temperatureTextView;

            convertView.setTag(viewHolder);
        } else {
            // 재사용 할 때
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.d(TAG, "getView: " + position);

        // 데이터
        Weather weather = mData.get(position);

        // 화면에 뿌리기
        viewHolder.weatherImage.setImageResource(weather.getImageRes());
        viewHolder.locationTextView.setText(weather.getCountry());
        viewHolder.temperatureTextView.setText(weather.getTemperature());

        // 홀수 줄은 빨간 색
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        // 클릭 된 아이템이면 노란색
        if (mSeletedPosition == position) {
            convertView.setBackgroundColor(Color.YELLOW);
        }

        return convertView;
    }

    // -1이면 선택된게 없다
    private int mSeletedPosition = -1;

    public void setSelect(int position) {
        mSeletedPosition = position;
    }

    // findViewById로 가져온 View 들을 보관
    private static class ViewHolder {
        ImageView weatherImage;
        TextView locationTextView;
        TextView temperatureTextView;
    }
}
