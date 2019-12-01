package com.alirnp.salizsalon.BannerSlider;

import com.alirnp.salizsalon.NestedJson.Item;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private List<Item> bannerList;

    public MainSliderAdapter(List<Item> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int getItemCount() {
        return bannerList == null ? 0 : bannerList.size() ;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        viewHolder.bindImageSlide(bannerList.get(position).getUrl());
    }
}