package com.alirnp.salizsalon;

import android.app.Application;

import com.alirnp.salizsalon.Interface.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static ApiService api;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://khodemon.ir/SalizApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        api = retrofit.create(ApiService.class);
    }

    public static ApiService getApi() {
        return api;
    }
}
