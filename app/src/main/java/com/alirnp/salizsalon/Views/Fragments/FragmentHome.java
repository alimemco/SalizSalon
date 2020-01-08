package com.alirnp.salizsalon.Views.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alirnp.salizsalon.Adapters.ItemsAdapter;
import com.alirnp.salizsalon.BannerSlider.MainSliderAdapter;
import com.alirnp.salizsalon.BannerSlider.PicassoImageLoadingService;
import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.ActivityChooseTime;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;


public class FragmentHome extends Fragment
        implements ItemsAdapter.OnItemClick,
        View.OnClickListener {

    private Slider slider;
    private View view;

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

    private Callback<SalizResponse> callbackBanner = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            String error;

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        Result result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {
                            if (result.getItems() != null) {
                                initSlider();
                                slider.setAdapter(new MainSliderAdapter(result.getItems()));
                                return;
                            } else {
                                error = getString(R.string.error_emptyItems);
                            }

                        } else {
                            error = result.getMessage();
                        }
                    } else {
                        error = getString(R.string.error_emptyHttpOK);
                    }

                } else {
                    error = getString(R.string.error_unSuccess);
                }
            } else {
                error = getString(R.string.error_empty_body);
            }
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    private Callback<SalizResponse> callbackUserLevel = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            String error;

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        Result result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {
                            if (result.getItems() != null) {
                                Item item = result.getItems().get(0);
                                if (item != null) {

                                    String level = item.getLevel();
                                    User user = MyApplication.getSharedPrefManager().getUser();
                                    if (user != null) {
                                        user.setLevel(level);

                                        Utils.sendMessageAdminLevel(getContext());
                                    }

                                }

                                return;
                            } else {
                                error = getString(R.string.error_emptyItems);
                            }

                        } else {
                            error = result.getMessage();
                        }
                    } else {
                        error = getString(R.string.error_emptyHttpOK);
                    }

                } else {
                    error = getString(R.string.error_unSuccess);
                }
            } else {
                error = getString(R.string.error_empty_body);
            }
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();

        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void initItemsRecyclerView() {
        rcvItems.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        ItemsAdapter itemsAdapter = new ItemsAdapter(DataGenerator.getHomeItems());
        itemsAdapter.setOnItemClick(this);
        rcvItems.setAdapter(itemsAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void initView() {

        rcvItems = view.findViewById(R.id.fragment_home_rcv_items);

        MyTextView addressButton = view.findViewById(R.id.fragment_home_textView_map_direction);

        addressButton.setOnClickListener(this);

        if (getContext() != null)
            if (Utils.isConnected(getContext())) {
                getBanners();
                checkUserLevel();
            } else {
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
            }

    }


    private void getBanners() {
        MyApplication.getApi().get(Constants.BANNERS).enqueue(callbackBanner);
    }

    private void checkUserLevel() {
        String phone = null;
        User user = MyApplication.getSharedPrefManager().getUser();
        if (user != null)
            phone = user.getPhone();
        if (phone != null) {
            Map<String, String> map = new HashMap<>();
            map.put(Constants.PHONE, phone);
            MyApplication.getApi().get(Constants.GET_USER_LEVEL, map).enqueue(callbackUserLevel);
        }


    }

    private void initSlider() {
        slider = view.findViewById(R.id.fragment_home_banner);
        slider.setInterval(3000);
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

    private void goToLocation() {
        String salizLocation = "30.722634,49.180997";
        String salizLocationName = "(saliz salon)";

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + salizLocation + salizLocationName);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.fragment_home_textView_map_direction) {
            goToLocation();
        }
    }
}
