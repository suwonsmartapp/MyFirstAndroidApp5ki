package com.example.myapplication.login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by junsuk on 2017. 3. 14..
 */

public interface UserApi {

    String BASE_URL = "http://suwonsmartapp.iptime.org/test/junsuk2/";

    @GET("login.php")
    Call<ResultModel> login(@Query("email") String email,
                            @Query("password") String password);
}
