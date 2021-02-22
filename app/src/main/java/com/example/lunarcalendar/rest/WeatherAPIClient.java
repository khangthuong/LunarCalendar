package com.example.lunarcalendar.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherAPIClient {
    public static final String BASE_URL_WEATHER_API = "https://api.openweathermap.org/";
    public static final String API_KEY_WEATHER_API = "a184bde24c284ec8b749f772d04db678";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_WEATHER_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
