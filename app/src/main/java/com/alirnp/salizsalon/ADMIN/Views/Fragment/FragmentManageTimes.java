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

import com.alirnp.salizsalon.ADMIN.Model.Model;
import com.alirnp.salizsalon.ADMIN.Adapter.ManageTimeAdapter;
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


public class FragmentManageTimes extends Fragment {

    private View view;

    private ManageTimeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;

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
        adapter = new ManageTimeAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.fragment_manager_times);
        recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        recyclerView.setAdapter(adapter);
    }

    private void getTimes() {
        MyApplication.getApi()
                .manageTime(Constants.TIMES, Constants.TOKEN)
                .enqueue(callback());
    }

    private Callback<ResultAdmin> callback() {
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
                            Model headerModel = new Model(item.getDay(), Constants.state.HEADER.getStatus());
                            List<Time> times = item.getTimes();
                            modelList.add(headerModel);

                            for (int j = 0; j < times.size(); j++) {
                                Time time = times.get(j);
                                Model model = new Model(time.getDay(), time.getHour(), Constants.state.MAIN.getStatus(), time.isReserved());
                                modelList.add(model);
                            }

                        }
                        adapter.submitList(modelList);
                    } else {
                        adapter.setNotFound();
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    adapter.setNotFound();
                }

            }

            @Override
            public void onFailure(Call<ResultAdmin> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                // adapter.setNotFound();

            }
        };
    }


}
