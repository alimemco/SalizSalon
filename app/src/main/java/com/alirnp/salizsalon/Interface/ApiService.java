package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("salizBanner.json")
    Call<ArrayList<Banner>> getBannerImages();


    @GET("salizGet.php")
    Call<ResponseJson> getCategory(@Query("get") String get);

    @GET("salizGet.php")
    Call<ResponseJson> getTimes(@Query("get") String get, @Query("day") String day);

    @GET("salizGet.php")
    Call<ResponseBody> getPosts(@Query("get") String get);

    @FormUrlEncoded
    @POST("salizGet.php")
    Call<ResponseBody> putTime(@QueryMap Map<Constants.resultMap, String> times);

}