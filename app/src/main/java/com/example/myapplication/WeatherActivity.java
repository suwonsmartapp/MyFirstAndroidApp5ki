package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.adapters.WeatherAdapter;
import com.example.myapplication.models.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mListView;
    private WeatherAdapter mAdapter;
    private List<Weather> mWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mListView = (ListView) findViewById(R.id.list_view);

        // 날씨 데이터
        mWeatherList = new ArrayList<>();
        mWeatherList.add(new Weather(R.drawable.sunny, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.blizzard, "안양", "23도"));
        mWeatherList.add(new Weather(R.drawable.cloudy, "안산", "24도"));
        mWeatherList.add(new Weather(R.drawable.rainy, "평택", "22도"));
        mWeatherList.add(new Weather(R.drawable.snow, "서울", "26도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "부산", "21도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "도쿄", "10도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "워싱턴", "5도"));
        mWeatherList.add(new Weather(R.drawable.snow, "평양", "6도"));
        mWeatherList.add(new Weather(R.drawable.snow, "베이징", "12도"));
        mWeatherList.add(new Weather(R.drawable.rainy, "수원", "49도"));
        mWeatherList.add(new Weather(R.drawable.rainy, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.blizzard, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.blizzard, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "수원", "27도"));
        mWeatherList.add(new Weather(R.drawable.sunny, "수원", "27도"));

        // 어댑터
        mAdapter = new WeatherAdapter(this, mWeatherList);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.setSelect(position);
        // 데이터가 변경됨을 알려줌 = 다시 그려라
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // 롱 클릭시 해당 아이템 삭제
        mWeatherList.remove(position);

        // 어댑터에 변경을 통지 = 다시 그려라
        mAdapter.notifyDataSetChanged();
        return true;
    }
}
