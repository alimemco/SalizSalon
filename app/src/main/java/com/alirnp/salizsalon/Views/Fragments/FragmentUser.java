package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.ItemsVerticalAdapter;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentUser extends Fragment {

    private View view;
    private AppCompatImageView imageView;

    RecyclerView rcv;


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

    private Callback<ResponseJson> callbackPosts = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                List<Result> result = response.body().getResult();

                rcv.setAdapter(new ItemsVerticalAdapter(result));

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

    private void initViews() {

        rcv = view.findViewById(R.id.fragment_user_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MyApplication.getApi().getCategory("posts").enqueue(callbackPosts);

    }


}
