package com.alirnp.salizsalon.ADMIN.Sticky;

import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.MyApplication;

import com.alirnp.salizsalon.MyUnitTest.Item;
import com.alirnp.salizsalon.MyUnitTest.ResultAdmin;
import com.alirnp.salizsalon.MyUnitTest.Time;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StickyActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticky_activity);

        initAdapter();
        // initData();

        getTimes();

    }


    private void initAdapter() {
        adapter = new RecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerViewStick);
        recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        List<Model> modelList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Model headerModel = new Model("Header " + i, ItemType.Header);
            modelList.add(headerModel);
            for (int j = 1; j < 8; j++) {
                Model model = new Model("Post " + i + " " + j, ItemType.Post);
                modelList.add(model);
            }
        }
        adapter.submitList(modelList);
    }

    private void getTimes() {
        MyApplication.getApi()
                .manageV2(Constants.TIMES, Constants.TOKEN)
                .enqueue(callbackV2());
    }


    private Callback<ResultAdmin> callbackV2() {
        return new Callback<ResultAdmin>() {
            @Override
            public void onResponse(Call<ResultAdmin> call, Response<ResultAdmin> response) {
                ResultAdmin result = response.body();
                if (result != null) {
                    if (result.isSuccess()) {

                        List<Model> modelList = new ArrayList<>();
                        List<Item> items = result.getItems();

                        for (int i = 0; i < items.size(); i++) {
                            Item item = items.get(i);
                            Model headerModel = new Model(item.getDay(), ItemType.Header);
                            List<Time> times = item.getTimes();
                            modelList.add(headerModel);

                            for (int j = 0; j < times.size(); j++) {
                                Time time = times.get(j);
                                Model model = new Model(time.getHour(), ItemType.Post, time.isReserved());
                                modelList.add(model);
                            }

                        }
                        adapter.submitList(modelList);
                    } else {
                        Toast.makeText(StickyActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StickyActivity.this, "null", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResultAdmin> call, Throwable t) {
                Toast.makeText(StickyActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                // adapter.setNotFound();

            }
        };
    }

}