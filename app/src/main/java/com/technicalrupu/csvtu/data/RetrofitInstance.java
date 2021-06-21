package com.technicalrupu.csvtu.data;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
      Api api;
    Retrofit retrofit;
    public RetrofitInstance() {
         retrofit = new Retrofit.Builder()
                .baseUrl("https://csvtu01.herokuapp.com/")
                 .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Api getServices()
    {
        return retrofit.create(Api.class);
    }

}
