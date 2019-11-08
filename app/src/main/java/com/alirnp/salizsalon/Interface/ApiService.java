package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.NestedJson.ResponseJson;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

/*
    @GET("salizBanner.json")
    Call<ArrayList<Banner>> getBannerImages();


    @GET("salizGet.php")
    Call<ResponseJson> getCategory(@Query("get") String get);

    @Headers("Cache-control: no-cache")
    @GET("salizGet.php")
    Call<ResponseJson> getTimes(@Query("get") String get, @Query("day") String day);
*/

    @GET("salizBanner.json")
    Call<ArrayList<Banner>> getBannerImages();


    @GET("api/v1/get.php")
    Call<ResponseJson> getCategory(@Query("request") String request);

    @Headers("Cache-control: no-cache")
    @GET("api/v1/get.php")
    Call<ResponseJson> getTimes(@Query("request") String request, @Query("DAY") String day);

    /**
     * @param request {RESERVE}
     * @param info    {DAY_NAME, MONTH_NAME, DAY_OF_MONTH, HOUR, PRICE, PRICE, SERVICES}
     */
    @Headers("Cache-control: no-cache")
    @PUT("api/v1/service.php")
    Call<ArrayList<Result>> reserve(
            @Query("request") String request,
            @QueryMap Map<String, String> info
    );


    /**
     * @param request {REGISTER , LOGIN , EDIT}
     * @param info    { first_name , last_name , phone , password }
     */
    @POST("api/v1/users.php")
    Call<ArrayList<Result>> userManager(
            @Query("request") String request,
            @QueryMap Map<String, String> info);

    @Headers("Cache-control: no-cache")
    @GET("api/v1/get.php")
    Call<ResponseJson> getUserReserveList(
            @Query("request") String request,
            @Query("PHONE") String phone);


}