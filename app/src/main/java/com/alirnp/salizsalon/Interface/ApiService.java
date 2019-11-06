package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.NestedJson.ResponseJson;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
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

    @GET("reserve.php")
    Call<ArrayList<Result>> reserve(
            @Query("DAY_NAME") String dayName,
            @Query("MONTH_NAME") String monthName,
            @Query("DAY_OF_MONTH") String dayOfMonth,
            @Query("HOUR") String hour,
            @Query("PRICE") int price,
            @Query("SERVICES") String services
    );


    /**
     * @param request {REGISTER , LOGIN}
     * @param info    { first_name , last_name , phone , password }
     */
    @POST("api/v1/users.php")
    Call<ArrayList<Result>> registerOrLogin(
            @Query("request") String request,
            @QueryMap Map<String, String> info);


    @GET("api/v1/get.php")
    Call<ResponseJson> getUserReserveList(
            @Query("request") String request,
            @Query("phone") String phone);


}