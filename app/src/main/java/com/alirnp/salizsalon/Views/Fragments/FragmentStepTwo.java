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
import com.alirnp.salizsalon.Generator.DataGenerator;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.Model.Service;
import com.alirnp.salizsalon.R;

import java.util.List;

public class FragmentStepTwo extends Fragment implements ServicesAdapter.onServiceSelect {

    private View view;
    private RecyclerView rcv;

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

    }

    private void initRcView() {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        ServicesAdapter adapter = new ServicesAdapter(DataGenerator.getServices());
        adapter.setOnServiceSelect(this);
        rcv.setAdapter(adapter);
    }

    private void validateStep(boolean enable) {
        if (onStepReady != null)
            onStepReady.OnReady(1, enable);
    }

    @Override
    public void services(List<Service> model) {

        if (model != null) {
            if (model.size() > 0) {
                validateStep(true);

            } else {
                validateStep(false);
            }
        }
    }
}
