package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.CategoryAdapter;
import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.BannerSlider.MainSliderAdapter;
import com.alirnp.salizsalon.BannerSlider.PicassoImageLoadingService;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment {

    private Slider slider;
    private View view;

    private RecyclerView rcv;
    private Callback<ArrayList<Banner>> callbackBanner = new Callback<ArrayList<Banner>>() {
        @Override
        public void onResponse(Call<ArrayList<Banner>> call, Response<ArrayList<Banner>> response) {
            if (response.body() != null) {
                slider.setAdapter(new MainSliderAdapter(response.body()));

            }
        }

        @Override
        public void onFailure(Call<ArrayList<Banner>> call, Throwable t) {
            Utils.log(MainActivity.class, "banner" + t.toString());
        }
    };
    private Callback<ResponseJson> callbackNested = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                Result result = response.body().getResult().get(0);
                if (Boolean.parseBoolean(result.getSuccess())) {

                    rcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    rcv.setAdapter(new CategoryAdapter(result.getItems()));

                } else {
                    Utils.log(FragmentHome.class, "UnSuccess");
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Utils.log(MainActivity.class, t.toString());
        }
    };


    public FragmentHome() {

    }

    public static FragmentHome newInstance() {

        Bundle args = new Bundle();

        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        initSlider();
    }

    private void initView() {
        slider = view.findViewById(R.id.fragment_home_banner);
        rcv = view.findViewById(R.id.fragment_home_rcv);
    }

    private void initSlider() {
        Slider.init(new PicassoImageLoadingService(getContext()));

        MyApplication.getApi().getBannerImages().enqueue(callbackBanner);
        MyApplication.getApi().getNested().enqueue(callbackNested);

    }
}
