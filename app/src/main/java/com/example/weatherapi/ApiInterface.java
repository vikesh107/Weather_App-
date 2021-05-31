package com.example.weatherapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface  {

    @GET("weather")
    Call<Example> getweather(@Query("q") String CityName,@Query("appid")  String apikey  );
}
