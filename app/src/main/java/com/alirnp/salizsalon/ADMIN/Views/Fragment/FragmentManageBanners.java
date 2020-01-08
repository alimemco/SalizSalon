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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alirnp.salizsalon.ADMIN.Adapter.ManageBannersAdapter;
import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Dialog.BottomSheetAddBanner;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentManageBanners extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    private Context context;
    private View view;
    private MyButton addButton;
    private ManageBannersAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private BroadcastReceiver bannerChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getBanners();
        }
    };


    public FragmentManageBanners() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_manage_banners, container, false);
        initViews();
        initRecyclerView();
        initSwipeRefreshLayout();
        initReceiver();

        getBanners();

        return view;
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ManageBannersAdapter();
        adapter.setSearching();
        recyclerView.setAdapter(adapter);
    }

    private void initReceiver() {
        LocalBroadcastManager.getInstance(context).registerReceiver(bannerChangedReceiver, new IntentFilter(Constants.EVENT_BANNER_CHANGED));
    }

    private void getBanners() {

        MyApplication.getApi()
                .manage(Constants.BANNERS, Constants.TOKEN)
                .enqueue(callback());
    }

    private void initViews() {
        recyclerView = view.findViewById(R.id.fragment_manage_banners_rcv);
        addButton = view.findViewById(R.id.fragment_manage_banners_button_add);
        swipeRefreshLayout = view.findViewById(R.id.fragment_manage_banners_swp);

        addButton.setOnClickListener(this);
    }

    private Callback<SalizResponse> callback() {
        return new Callback<SalizResponse>() {
            @Override
            public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
                swipeRefreshLayout.setRefreshing(false);

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
                swipeRefreshLayout.setRefreshing(false);

            }
        };
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onRefresh() {
        getBanners();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fragment_manage_banners_button_add) {
            BottomSheetAddBanner bottomSheetAddBanner = new BottomSheetAddBanner();
            if (getFragmentManager() != null)
                bottomSheetAddBanner.show(getFragmentManager(), bottomSheetAddBanner.getTag());
        }
    }
}
