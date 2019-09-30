package com.alirnp.salizsalon.Views.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;


public class FragmentUser extends Fragment {

    private View view;
    private AppCompatImageView imageView;


    public FragmentUser() {
        // Required empty public constructor
    }

    public static FragmentUser newInstance() {

        Bundle args = new Bundle();

        FragmentUser fragment = new FragmentUser();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user, container, false);
        initViews();
        return view;
    }

    private void initViews() {

        imageView = view.findViewById(R.id.fagment_user_image);


        String url = "https://image.flaticon.com/icons/svg/682/682619.svg";


        GlideToVectorYou
                .init()
                .with(getActivity())
                .setPlaceHolder(R.color.colorAccent, R.color.design_default_color_primary)
                .load(Uri.parse(url), imageView);
    }

}
