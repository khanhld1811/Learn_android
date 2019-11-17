package com.duykhanh.employeemanager.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public class RetrofitResponse {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String BASE_URL){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
