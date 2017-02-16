package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.GeoIp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GeoIpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mAddressEditText;
    private TextView mResultTextView;

    private Retrofit mRetrofit;
    private FreeGeoIpService mService;

    private ProgressBar mProgressBar;

    interface FreeGeoIpService {
        @GET("json/{address}")
        Call<GeoIp> getGeoIp(@Path("address") String address);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_ip);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://freegeoip.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(FreeGeoIpService.class);

        mAddressEditText = (EditText) findViewById(R.id.address_edit);
        mResultTextView = (TextView) findViewById(R.id.result_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        findViewById(R.id.submit_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mProgressBar.setVisibility(View.VISIBLE);

        // http://freegeoip.net/json/[mAddressEditText]
        mService.getGeoIp(mAddressEditText.getText().toString())
                .enqueue(new Callback<GeoIp>() {
                    @Override
                    public void onResponse(Call<GeoIp> call, Response<GeoIp> response) {
                        GeoIp geoIp = response.body();

                        if (geoIp != null) {
                            mResultTextView.setText(geoIp.toString());
                        } else {
                            Toast.makeText(GeoIpActivity.this, "잘못된 입력입니다", Toast.LENGTH_SHORT).show();
                        }

                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GeoIp> call, Throwable t) {
                        Toast.makeText(GeoIpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}
