package com.example.lunarcalendar.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPIInterface {
    @GET("data/2.5/weather?units=metric")
    Call<ResponseBody> getCurentCondition(@Query ("lat") double latitude,
                                          @Query ("lon") double longitude,
                                          @Query ("appid") String key
    );
}
