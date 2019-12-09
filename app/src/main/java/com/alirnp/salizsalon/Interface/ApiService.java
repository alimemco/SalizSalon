package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.MyUnitTest.ResultAdmin;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET(Constants.API_V1 + "get.php")
    Call<SalizResponse> get(@Query("request") String request);

    @Headers("Cache-control: no-cache")
    @GET(Constants.API_V1 + "get.php")
    Call<SalizResponse> getTimes(
            @Query("request") String request,
            @Query("DAY") String day);

    /**
     * @param request {RESERVE}
     * @param info    {DAY_NAME, MONTH_NAME, DAY_OF_MONTH, HOUR, PRICE, PRICE, SERVICES}
     */
    @Headers("Cache-control: no-cache")
    @PUT(Constants.API_V1 + "service.php")
    Call<SalizResponse> reserve(
            @Query("request") String request,
            @QueryMap Map<String, String> info
    );


    /**
     * @param request {REGISTER , LOGIN , EDIT}
     * @param info    { first_name , last_name , phone , password }
     */
    @POST(Constants.API_V1 + "users.php")
    Call<SalizResponse> userManager(
            @Query("request") String request,
            @QueryMap Map<String, String> info);


    @Headers("Cache-control: no-cache")
    @GET(Constants.API_V1 + "get.php")
    Call<SalizResponse> getUserReserveList(
            @Query("request") String request,
            @Query("PHONE") String phone);



    @Headers("Cache-control: no-cache")
    @GET(Constants.API_V1 + "manager.php")
    Call<SalizResponse> manage(
            @Query("request") String request,
            @Query("TOKEN") String TOKEN);


    @Headers("Cache-control: no-cache")
    @GET(Constants.API_V1 + "manager.php")
    Call<SalizResponse> manage(
            @Query("request") String request,
            @Query("TOKEN") String TOKEN,
            @QueryMap Map<String, String> map);




    @Headers("Cache-control: no-cache")
    @GET(Constants.API_V1 + "manager.php")
    Call<ResultAdmin> manageTime(
            @Query("request") String request,
            @Query("TOKEN") String TOKEN);


    @GET(Constants.API_SMS + "sendSms.php")
    Call<ResponseBody> sendSms(
            @Query("TOKEN") String TOKEN,
            @QueryMap Map<String, String> map
    );
}