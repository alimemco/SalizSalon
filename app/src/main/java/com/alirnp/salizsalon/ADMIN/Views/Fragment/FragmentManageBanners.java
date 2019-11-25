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

import com.alirnp.salizsalon.ADMIN.Adapter.ManageBannersAdapter;
import com.alirnp.salizsalon.ADMIN.Adapter.ManageOrderAdapter;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageBanners extends Fragment {

    private View view;
    private RecyclerView rcv;
    private ManageBannersAdapter adapter;

    public FragmentManageBanners() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manage_banners, container, false);
        initViews();
        getBanners();
        //TODO enable banner
        return view;
    }

    private void getBanners() {

        MyApplication.getApi()
                .manage(Constants.BANNERS, Constants.TOKEN)
                .enqueue(callback());
    }

    private void initViews() {
        rcv = view.findViewById(R.id.fragment_manage_banners_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ManageBannersAdapter();
        adapter.setSearching();

        rcv.setAdapter(adapter);
    }

    private Callback<ResponseJson> callback() {
        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Item> items = response.body().getResult().get(0).getItems();
                        adapter.setData(items);

                    } else {
                        adapter.setNotFound();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                adapter.setNotFound();

            }
        };
    }


}
