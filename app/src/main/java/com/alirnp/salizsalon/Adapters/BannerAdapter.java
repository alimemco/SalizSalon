package com.alirnp.salizsalon.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.alirnp.salizsalon.BannerSlider.Banner;

import java.util.ArrayList;

public class BannerAdapter extends FragmentStatePagerAdapter {


    private ArrayList<Banner> bannerList;

    public BannerAdapter(FragmentManager fm, ArrayList<Banner> bannerList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.bannerList = bannerList;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return BannerView.newInstance(position, bannerList);
    }

    @Override
    public int getCount() {
        return bannerList == null ? 0 : bannerList.size();
    }
}
