package com.alirnp.salizsalon.BannerSlider;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private ArrayList<Banner> bannerList;

    public MainSliderAdapter(ArrayList<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int getItemCount() {
        return bannerList == null ? 0 : bannerList.size() ;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        viewHolder.bindImageSlide(bannerList.get(position).getImage());
    }
}