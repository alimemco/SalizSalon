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
import com.alirnp.salizsalon.Dialog.LoadingDialog;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageOrder extends Fragment
        implements ManageOrderAdapter.OnChangeOrderStatus {

    private View view;
    private RecyclerView rcv;
    private ManageOrderAdapter adapter;
    private LoadingDialog dialog;

    private int posotionOrder;
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
        MyApplication.getApi()
                .manage(Constants.ORDERS, Constants.TOKEN)
                .enqueue(callback());
    }

    private void editOrders(int id, int timeID, String status) {
        Map<String, String> map = new HashMap<>();
        map.put(Constants.ID, String.valueOf(id));
        map.put(Constants.TIME_ID, String.valueOf(timeID));
        map.put(Constants.STATUS, status);
        MyApplication.getApi()
                .manageEdit(Constants.EDIT_ORDERS, Constants.TOKEN, map)
                .enqueue(callbackEditOrder());


    }

    private void initViews() {
        rcv = view.findViewById(R.id.fragment_manager_order);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ManageOrderAdapter();
        adapter.setOnChangeOrderStatus(this);
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


    private Callback<ResponseJson> callbackEditOrder() {
        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ResultItems result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {

                            adapter.changeOrder(result.getItems(), posotionOrder);
                            // Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onOrderStatusChange(int id, int timeID, int position, Constants.statusReserve status) {

        dialog = new LoadingDialog();
        if (getFragmentManager() != null)
            dialog.show(getFragmentManager(), "dialog");

        posotionOrder = position;
        editOrders(id, timeID, status.getStatus());
    }
}
