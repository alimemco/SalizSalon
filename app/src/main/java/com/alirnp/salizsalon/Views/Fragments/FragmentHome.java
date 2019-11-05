package com.alirnp.salizsalon.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alirnp.salizsalon.Adapters.CategoryAdapter;
import com.alirnp.salizsalon.Adapters.ItemsAdapter;
import com.alirnp.salizsalon.Adapters.ItemsVerticalAdapter;
import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.BannerSlider.MainSliderAdapter;
import com.alirnp.salizsalon.BannerSlider.PicassoImageLoadingService;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.ActivityChooseTime;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment
        implements ItemsAdapter.OnItemClick {

    private Slider slider;
    private View view;

    private RecyclerView rcvCategory;
    private RecyclerView rcvItems;


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

        initItemsRecyclerView();

        return view;
    }

    private void initItemsRecyclerView() {

        rcvItems.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        ItemsAdapter itemsAdapter = new ItemsAdapter(DataGenerator.getHomeItems());
        itemsAdapter.setOnItemClick(this);
        rcvItems.setAdapter(itemsAdapter);

    }

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
    private Callback<ResponseJson> callbackCategory = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                Result result = response.body().getResult().get(0);
                if (Boolean.parseBoolean(result.getSuccess())) {

                    rcvCategory.setAdapter(new CategoryAdapter(result.getItems()));

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
    private Callback<ResponseJson> callbackPosts = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                List<Result> result = response.body().getResult();

                ItemsVerticalAdapter verticalAdapter = new ItemsVerticalAdapter(result);

/*
                for (int i = 0; i < result.getItems().size(); i++) {
                    Utils.log(FragmentHome.class,result.getItems().get(i).getTitle());
                }*/

            }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Utils.log(MainActivity.class, t.toString());
        }
    };

    @Override
    public void onStart() {
        super.onStart();


    }

    private void initView() {
        slider = view.findViewById(R.id.fragment_home_banner);

        rcvCategory = view.findViewById(R.id.fragment_home_rcv_category);
        rcvItems = view.findViewById(R.id.fragment_home_rcv_items);

        rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        if (getContext() != null)
            if (Utils.isConnected(getContext())) {
                initSlider();
                initApiServices();
            }



    }

    private void initApiServices() {

        MyApplication.getApi().getBannerImages().enqueue(callbackBanner);
        MyApplication.getApi().getCategory("category").enqueue(callbackCategory);
        MyApplication.getApi().getCategory("posts").enqueue(callbackPosts);
    }

    private void initSlider() {
        Slider.init(new PicassoImageLoadingService(getContext()));
    }

    @Override
    public void OnClick(int ID) {

        switch (ID) {
            case 0:
                startActivity(new Intent(getContext(), ActivityChooseTime.class));
                break;

            case 1:
                Toast.makeText(getContext(), "به زودی", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
