package com.alirnp.salizsalon.ADMIN.Views.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alirnp.salizsalon.ADMIN.Adapter.ManageTimeAdapter;
import com.alirnp.salizsalon.ADMIN.Model.Model;
import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Dialog.AddTimeDialog;
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


public class FragmentManageTimes extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener,
        AddTimeDialog.OnDoneProgress {

    private View view;


    private ManageTimeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;

    private SwipeRefreshLayout swp;

    public FragmentManageTimes() {
    }


    private BroadcastReceiver receiverEditedUser = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTimes();
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_times, container, false);

        initViews();
        getTimes();
        registerBroadcast();
        return view;
    }

    private void registerBroadcast() {
        if (getContext() != null)
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiverEditedUser,
                    new IntentFilter(Constants.EVENT_EDITED_TIME));
    }

    private void getTimes() {
        MyApplication.getApi()
                .manageTime(Constants.TIMES, Constants.TOKEN)
                .enqueue(callback());
    }

    private void initViews() {
        adapter = new ManageTimeAdapter();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.fragment_manage_times_rcv);
        MyButton addTimeBtn = view.findViewById(R.id.fragment_manage_times_btn_addTime);
        addTimeBtn.setOnClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        recyclerView.setAdapter(adapter);

        swp = view.findViewById(R.id.fragment_manage_times_swp);
        swp.setColorSchemeResources(R.color.colorPrimary);
        swp.setOnRefreshListener(this);
    }

    private Callback<ResultAdmin> callback() {
        return new Callback<ResultAdmin>() {
            @Override
            public void onResponse(Call<ResultAdmin> call, Response<ResultAdmin> response) {
                swp.setRefreshing(false);

                ResultAdmin result = response.body();
                if (result != null) {
                    if (result.isSuccess()) {

                        List<Model> modelList = new ArrayList<>();
                        List<Item> items = result.getItems();

                        for (int i = 0; i < items.size(); i++) {
                            Item item = items.get(i);
                            Model headerModel = new Model(item.getDay(), Constants.state.HEADER);
                            List<Time> times = item.getTimes();
                            modelList.add(headerModel);

                            for (int j = 0; j < times.size(); j++) {
                                Time time = times.get(j);
                                Model model = new Model(time.getID(), time.getDay(), time.getHour(), Constants.state.MAIN, time.isReserved());
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
                adapter.setNotFound();
                swp.setRefreshing(false);

            }
        };
    }

    @Override
    public void onRefresh() {
        getTimes();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_manage_times_btn_addTime) {
            AddTimeDialog dialog = new AddTimeDialog();
            dialog.setOnDoneProgress(this);
            if (getFragmentManager() != null)
                dialog.show(getFragmentManager(), "AddTimeDialog");
        }
    }

    @Override
    public void OnDone() {
        getTimes();
    }
}
