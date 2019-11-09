package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.ServicesAdapter;
import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.ActivityChooseTime;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStepTwo extends Fragment implements ServicesAdapter.onServiceSelect {

    private View view;
    private RecyclerView rcv;
    private MyTextView finalPriceTv, finalPeriodTv;

    private OnStepReady onStepReady;


    public FragmentStepTwo(OnStepReady onStepReady) {
        this.onStepReady = onStepReady;

    }

    private Callback<ResponseJson> callback = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                ResultItems result = response.body().getResult().get(0);
                if (Boolean.parseBoolean(result.getSuccess())) {

                    ServicesAdapter adapter = new ServicesAdapter(result.getItems());
                    adapter.setOnServiceSelect(FragmentStepTwo.this);
                    rcv.setAdapter(adapter);

                } else {
                    Utils.log(FragmentHome.class, "UnSuccess");
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Utils.log(MainActivity.class, t.toString());
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_two, container, false);
        initViews();
        MyApplication.getApi().get(Constants.SERVICES).enqueue(callback);
        return view;
    }

    private void showPrice(int price) {
        String prc = " مجموع :  " + Utils.numberToTextPrice(price);
        finalPriceTv.setText(prc);
    }

    private void initViews() {

        rcv = view.findViewById(R.id.fragment_step_two_rcv);
        finalPriceTv = view.findViewById(R.id.fragment_step_two_finalPrice);
        finalPeriodTv = view.findViewById(R.id.fragment_step_two_finalPeriod);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        showPrice(0);
        showPeriod(0);

    }

    private void showPeriod(int period) {
        String prd = " ~ " + period + " دقیقه ";
        finalPeriodTv.setText(prd);
    }

    private void validateStep(boolean enable) {
        if (onStepReady != null)
            onStepReady.OnReady(1, enable);
    }

    private void putToData(ArrayList<Item> services) {
        ActivityChooseTime.setValues(Constants.data.SERVICES, services);
    }

    private int calculatorPrice(ArrayList<Item> model) {
        int prc = 0;
        for (int i = 0; i < model.size(); i++) {
            prc += Integer.valueOf(model.get(i).getPrice());
        }
        return prc;
    }

    private int calculatorPeriod(ArrayList<Item> model) {
        int prc = 0;
        for (int i = 0; i < model.size(); i++) {
            prc += Integer.valueOf(model.get(i).getPeriod());
        }
        return prc;
    }

    @Override
    public void services(ArrayList<Item> model) {
        if (model != null) {

            showPrice(calculatorPrice(model));
            showPeriod(calculatorPeriod(model));
            validateStep(model.size() > 0);

            putToData(model);
        }
    }




}
