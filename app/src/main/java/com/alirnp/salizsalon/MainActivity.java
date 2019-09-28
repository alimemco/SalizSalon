package com.alirnp.salizsalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.BannerSlider.MainSliderAdapter;
import com.alirnp.salizsalon.BannerSlider.PicassoImageLoadingService;
import com.alirnp.salizsalon.Utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private Slider slider;
    private BottomNavigationView btmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initSlider();

    }

    private void initSlider() {
        Slider.init(new PicassoImageLoadingService(MainActivity.this));
        MyApplication.getApi().getBannerImages().enqueue(callback);
    }

    private void initViews() {
        slider = findViewById(R.id.activity_main_banner);
        btmView = findViewById(R.id.activity_main_bottomNav);

        btmView.setOnNavigationItemSelectedListener(this);
    }

    private Callback<List<Banner>> callback = new Callback<List<Banner>>() {
        @Override
        public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
            if (response.body() != null){
                slider.setAdapter(new MainSliderAdapter(response.body()));
            }
        }

        @Override
        public void onFailure(Call<List<Banner>> call, Throwable t) {
            Utils.log(MainActivity.class,t.toString());
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.account:
                Toast.makeText(this, "acc", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.setting:
                Toast.makeText(this, "seeting", Toast.LENGTH_SHORT).show();
                return true;

        }

        return false;
    }
}
