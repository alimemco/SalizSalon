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

import com.alirnp.salizsalon.Adapters.ItemsAdapter;
import com.alirnp.salizsalon.BannerSlider.MainSliderAdapter;
import com.alirnp.salizsalon.BannerSlider.PicassoImageLoadingService;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.ActivityChooseTime;

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


    private Callback<ResponseJson> callbackBanner = new Callback<ResponseJson>() {
        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
            if (response.isSuccessful())
                if (response.body() != null) {
                    ResultItems result = response.body().getResult().get(0);
                    boolean success = Boolean.parseBoolean(result.getSuccess());
                    if (success) {
                        slider.setAdapter(new MainSliderAdapter(result.getItems()));
                    } else {
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();


    }

    private void initView() {

        rcvCategory = view.findViewById(R.id.fragment_home_rcv_category);
        rcvItems = view.findViewById(R.id.fragment_home_rcv_items);

        rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        if (Utils.connected(getContext())) {
            initSlider();
            initApiServices();
        } else {
            Toast.makeText(getContext(), "connection error", Toast.LENGTH_SHORT).show();
        }



    }


    private void initApiServices() {
        MyApplication.getApi().get(Constants.BANNERS).enqueue(callbackBanner);
    }

    private void initSlider() {
        slider = view.findViewById(R.id.fragment_home_banner);
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
