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

import com.alirnp.salizsalon.ADMIN.Adapter.MyAdapterTime;
import com.alirnp.salizsalon.ADMIN.Adapter.MyHeadFootAdapter;
import com.alirnp.salizsalon.Expand.GroupList;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageTimes extends Fragment {

    private View view;
    private RecyclerView rcv;
    //  private ManageTimesAdapter adapter;

    public FragmentManageTimes() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_times, container, false);
        initViews();
        getTimes();

        return view;
    }

    private void initViews() {
        rcv = view.findViewById(R.id.fragment_manager_times);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO chan
//        adapter = new ManageTimesAdapter();
//        adapter.setSearching();
//
//
//        rcv.setAdapter(adapter);
    }

    private void getTimes() {
        MyApplication.getApi()
                .manage(Constants.TIMES, Constants.TOKEN)
                .enqueue(callback());
    }
/*
    private Callback<Week> callback() {
        return new Callback<Week>() {
            @Override
            public void onResponse(Call<Week> call, Response<Week> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Result result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success){

                            List<Item> items = result.getItems();
                            adapter.setData(items);
                        }else {
                            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        adapter.setNotFound();
                    }
                }
            }

            @Override
            public void onFailure(Call<Week> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                adapter.setNotFound();

            }
        };
    }
    */

    private Callback<ResponseJson> callback() {
        return new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ResultItems result = response.body().getResult().get(0);
                        boolean success = Boolean.parseBoolean(result.getSuccess());
                        if (success) {
                            List<Item> items = result.getItems();

                            List<GroupList> groupItems = new ArrayList<>();
                            List<Item> childItems = new ArrayList<>();

                            for (int i = 0; i < items.size(); i++) {
                                Item item = new Item();
                                item.setHour("hour");
                                item.setDay("day");
                                item.setReserved("reserved");

                                childItems.add(item);

                            }
                            groupItems.add(new GroupList("title", childItems));

                            MyAdapterTime adapter = new MyAdapterTime(childItems);
                            MyHeadFootAdapter wrappedAdapter = new MyHeadFootAdapter(adapter);

                            rcv.setAdapter(wrappedAdapter);
                            //  adapter.setData(groupItems);
                        } else {
                            //   adapter.setNotFound();
                        }
                    } else {
                        //adapter.setNotFound();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                // adapter.setNotFound();

            }
        };
    }

}
