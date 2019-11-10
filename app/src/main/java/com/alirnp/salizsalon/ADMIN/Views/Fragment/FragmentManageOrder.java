package com.alirnp.salizsalon.ADMIN.Views.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.ADMIN.Adapter.ManageOrderAdapter;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageOrder extends Fragment {

    private View view ;
    private RecyclerView rcv ;
    public FragmentManageOrder() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manage_order, container, false);
        initViews();
        getOrders();
        return view;
    }

    private void getOrders() {
        MyApplication.getApi().manage(Constants.ORDERS).enqueue(callback());
    }

    private void initViews() {
        rcv = view.findViewById(R.id.fragment_manager_order);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private Callback<ResponseJson> callback (){
        return  new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        List<Item> items = response.body().getResult().get(0).getItems();
                        ManageOrderAdapter adapter = new ManageOrderAdapter(items);
                       rcv.setAdapter(adapter);
                    }else {
                        Toast.makeText(getContext(), "resp == null", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        };
    }

}
