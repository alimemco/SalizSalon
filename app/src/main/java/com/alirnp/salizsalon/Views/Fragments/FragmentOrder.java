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
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOrder extends Fragment {

    private View view;
    private UserReserveAdapter adapter = new UserReserveAdapter();

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
        RecyclerView rcv = view.findViewById(R.id.fragment_order_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);

        User user = MyApplication.getSharedPrefManager().getUser();

        String phone = user.getPhone();
            if (phone != null) {
                MyApplication.getApi().getUserReserveList(Constants.USER_RESERVE_LIST, phone).enqueue(callback());
            }

    }


    private Callback<SalizResponse> callback() {
        return new Callback<SalizResponse>() {
            @Override
            public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        Result result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {

                            adapter.setData(result.getItems());

                        } else {
                            String msg = result.getMessage();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            @Override
            public void onFailure(Call<SalizResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }




}
