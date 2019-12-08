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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alirnp.salizsalon.ADMIN.Adapter.ManageOrderAdapter;
import com.alirnp.salizsalon.Dialog.LoadingDialog;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageOrder extends Fragment
        implements ManageOrderAdapter.OnChangeOrderStatus,
        SwipeRefreshLayout.OnRefreshListener,
        ManageOrderAdapter.OnAcceptOrder {

    private View view;
    private RecyclerView rcv;
    private ManageOrderAdapter adapter;
    private LoadingDialog dialog;
    private SwipeRefreshLayout swp;

    private int positionOrder;

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
                .manage(Constants.EDIT_ORDERS, Constants.TOKEN, map)
                .enqueue(callbackEditOrder());


    }

    private void initViews() {
        swp = view.findViewById(R.id.fragment_manage_order_swp);
        rcv = view.findViewById(R.id.fragment_manager_order);

        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ManageOrderAdapter();
        adapter.setOnChangeOrderStatus(this);
        adapter.setOnAcceptOrder(this);
        adapter.setSearching();

        rcv.setAdapter(adapter);

        if (getContext() != null)
            swp.setColorSchemeResources(R.color.colorPrimary);

        swp.setOnRefreshListener(this);
    }

    private Callback<SalizResponse> callback() {
        return new Callback<SalizResponse>() {
            @Override
            public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
                swp.setRefreshing(false);

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
            public void onFailure(Call<SalizResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                adapter.setNotFound();
                swp.setRefreshing(false);

            }
        };
    }


    private Callback<SalizResponse> callbackEditOrder() {
        return new Callback<SalizResponse>() {
            @Override
            public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Result result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {
                            adapter.changeOrder(result.getItems(), positionOrder);
                        } else {
                            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SalizResponse> call, Throwable t) {
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

        positionOrder = position;

        editOrders(id, timeID, status.getStatus());
    }

    private void sendSmsToClient(Item item) {
        StringBuilder message = new StringBuilder();
        String date = item.getDayName() + " " + item.getDayOfMonth() + " " + item.getMonthName();
        String companyName = getResources().getString(R.string.company_name);
        message
                .append(item.getFirst_name())
                .append(" عزیز, نوبت شما برای ")
                .append(date).append(" ")
                .append(" ساعت ")
                .append(item.getHour()).append(" ")
                .append("تایید شد.").append("\n")
                .append(" قیمت تقریبی :").append(item.getPrice()).append(" تومان ").append("\n")
                .append(companyName)
        ;

        Utils.sendSmsToClient(message.toString());

    }

    @Override
    public void onRefresh() {
        getOrders();
    }

    @Override
    public void OnAccept(Item item) {
        sendSmsToClient(item);
    }
}
