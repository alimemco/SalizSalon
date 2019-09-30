package com.alirnp.salizsalon;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.alirnp.salizsalon.Interface.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static ApiService api;

    public static Typeface iranSans;
    public static Typeface iranSansBold;

    public static Typeface getIranSans(Context context) {
        if (iranSans == null) {
            iranSans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile.ttf");
        }
        return iranSans;
    }

    public static ApiService getApi() {
        return api;
    }

    public static Typeface getIranSansBold(Context context) {
        if (iranSansBold == null) {
            iranSansBold = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Bold.ttf");

        }
        return iranSansBold;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://khodemon.ir/SalizApp/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        api = retrofit.create(ApiService.class);
    }
}
