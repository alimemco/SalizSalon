package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.UserReserveAdapter;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOrder extends Fragment {


    private View view;
    private RecyclerView rcv;

    public FragmentOrder() {
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
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        User user = MyApplication.getSharedPrefManager().getUser();

        String phone = user.getPhone();
            if (phone != null) {
                MyApplication.getApi().getUserReserveList(Constants.USER_RESERVE_LIST, phone).enqueue(callback());
            }

    }


    private Callback<ResponseJson> callback() {
        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        ResultItems result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {

                            UserReserveAdapter adapter = new UserReserveAdapter(result.getItems());
                            rcv.setAdapter(adapter);

                        } else {
                            String msg = result.getMessage();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }




}
