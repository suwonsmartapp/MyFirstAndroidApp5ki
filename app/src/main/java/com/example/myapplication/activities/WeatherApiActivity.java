package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragments.WeatherFragment;
import com.example.myapplication.interfaces.WeatherApi;
import com.example.myapplication.models.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_api);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        weatherApi.getWeatherList().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {

                // 날씨 데이터
                List<Weather> weatherList = response.body();
                Log.d("Weather", "onResponse: " + weatherList);

                WeatherFragment fragment = WeatherFragment.newInstance(weatherList);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Toast.makeText(WeatherApiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
