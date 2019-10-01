package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.NestedJson.ResponseJson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("salizBanner.json")
    Call<ArrayList<Banner>> getBannerImages();


    @GET("salizGet.php")
    Call<ResponseJson> getCategory(@Query("get") String get);

    @GET("salizGet.php")
    Call<ResponseBody> getPosts(@Query("get") String get);
}