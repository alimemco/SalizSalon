package com.alirnp.salizsalon.Interface;

import com.alirnp.salizsalon.BannerSlider.Banner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

  @GET("salizBanner.json")
  Call<List<Banner>> getBannerImages();
}