package com.example.myapplication.login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by junsuk on 2017. 3. 14..
 */

public class RetrofitUtil {

    private Retrofit mRetrofit;

    private UserApi mUserApi;

    public RetrofitUtil() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UserApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mUserApi = mRetrofit.create(UserApi.class);
    }

    public UserApi getUserApi() {
        return mUserApi;
    }

}
