package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.ItemsVerticalAdapter;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOrder extends Fragment {

    RecyclerView rcv;
    private View view;
    private Callback<ResponseJson> callbackPosts = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                List<ResultItems> result = response.body().getResult();

                rcv.setAdapter(new ItemsVerticalAdapter(result));


            }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Utils.log(MainActivity.class, t.toString());
        }
    };

    public FragmentOrder() {
        // Required empty public constructor
    }

    public static FragmentOrder newInstance() {

        Bundle args = new Bundle();

        FragmentOrder fragment = new FragmentOrder();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);
        initViews();
        return view;
    }

    private void initViews() {

        rcv = view.findViewById(R.id.fragment_order_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MyApplication.getApi().getCategory("posts").enqueue(callbackPosts);

    }


}
