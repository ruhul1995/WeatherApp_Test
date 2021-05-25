package com.example.weatherapp_test.network_call;

import com.example.weatherapp_test.model.Example;
import com.example.weatherapp_test.model.Main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/data/2.5/box/city?")
    Call<Example>getResults(
            @Query("bbox") String mName,
            @Query("APPID") String mAppId
    );
}
