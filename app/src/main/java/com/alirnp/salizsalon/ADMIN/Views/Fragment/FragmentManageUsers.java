package com.alirnp.salizsalon.ADMIN.Views.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alirnp.salizsalon.ADMIN.Adapter.ManageUsersAdapter;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentManageUsers extends Fragment {

    private View view;
    private ManageUsersAdapter adapter;
    private Callback<SalizResponse> callback = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
            String error = "{}";
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    Result result = response.body().getResult().get(0);
                    boolean success = Boolean.valueOf(result.getSuccess());
                    if (success) {
                        adapter.setResult(result.getItems());
                        return;
                    } else {
                        error = result.getMessage();
                    }
                } else {
                    if (getContext() != null)
                        error = getContext().getString(R.string.error_empty_body);
                }

            } else {
                error = response.message();
            }

            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    public FragmentManageUsers() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_users, container, false);
        initView();
        getUsers();
        return view;
    }

    private void getUsers() {
        MyApplication.getApi().manage(Constants.GET_USERS, Constants.TOKEN).enqueue(callback);
    }

    private void initView() {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_manage_users_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ManageUsersAdapter();
        recyclerView.setAdapter(adapter);
    }

}
