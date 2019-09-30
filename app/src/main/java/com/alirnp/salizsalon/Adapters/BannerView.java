package com.alirnp.salizsalon.Adapters;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.BannerSlider.Banner;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BannerView extends Fragment {

    private ImageView imageView;

    private ArrayList<Banner> bannerList;
    private int position;


    public BannerView() {

    }

    public static BannerView newInstance(int position, ArrayList<Banner> bannerList) {

        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        args.putParcelableArrayList(Constants.IMAGE_LIST, bannerList);
        BannerView fragment = new BannerView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bannerList = getArguments().getParcelableArrayList(Constants.IMAGE_LIST);
            position = getArguments().getInt(Constants.POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.banner_view, container, false);

        imageView = rootView.findViewById(R.id.banner_view_image);

        String link = bannerList.get(position).getImage();

        Picasso.get()
                .load(link)
                .into(imageView);

        return rootView;
    }


}
