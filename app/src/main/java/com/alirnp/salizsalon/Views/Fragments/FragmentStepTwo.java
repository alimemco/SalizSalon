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
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.ActivityChooseTime;

import java.util.ArrayList;

public class FragmentStepTwo extends Fragment implements ServicesAdapter.onServiceSelect {

    private View view;
    private RecyclerView rcv;
    private MyTextView finalPriceTv;

    private OnStepReady onStepReady;


    public FragmentStepTwo(OnStepReady onStepReady) {
        this.onStepReady = onStepReady;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_two, container, false);
        initViews();
        initRcView();
        return view;
    }

    private void initViews() {

        rcv = view.findViewById(R.id.fragment_step_two_rcv);
        finalPriceTv = view.findViewById(R.id.fragment_step_two_finalPrice);

        showPrice(0);

    }

    private void showPrice(int price) {
        String prc = " مجموع :  " + Utils.numberToTextPrice(price);
        finalPriceTv.setText(prc);
    }

    private void initRcView() {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        ServicesAdapter adapter = new ServicesAdapter(DataGenerator.getServices());
        adapter.setOnServiceSelect(this);
        rcv.setAdapter(adapter);
    }

    private void putToData(ArrayList<Service> services) {
        ActivityChooseTime.setValues(Constants.data.SERVICES, services);
    }

    private void validateStep(boolean enable) {
        if (onStepReady != null)
            onStepReady.OnReady(1, enable);
    }

    @Override
    public void services(ArrayList<Service> model) {

        if (model != null) {

            showPrice(calculatorPrice(model));
            validateStep(model.size() > 0);

            putToData(model);
        }


/*
        if (onDataReady != null)
            onDataReady.onServicesReceived(model);*/
    }

    private int calculatorPrice(ArrayList<Service> model) {
        int prc = 0;
        for (int i = 0; i < model.size(); i++) {
            prc += model.get(i).getPrice();
        }
        return prc;
    }
}
