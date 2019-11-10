package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.FinalAdapter;
import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.Model.Day;
import com.alirnp.salizsalon.Model.Hour;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;

import java.util.ArrayList;

public class FragmentStepThree extends Fragment {

    private OnStepReady onStepReady;
    private View view;
    private RecyclerView rcv;
    private FinalAdapter adapter;
    private int finalPrice;


    public FragmentStepThree(OnStepReady onStepReady) {
        this.onStepReady = onStepReady;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Day day = getArguments().getParcelable(Constants.DAY);
            Hour hour = getArguments().getParcelable(Constants.HOUR);
            ArrayList<Item> services = getArguments().getParcelableArrayList(Constants.SERVICES);

            adapter = new FinalAdapter(day, hour, services);

            initFinalPrice(services);

        }
    }

    private void initFinalPrice(ArrayList<Item> services) {
        finalPrice = 0;
        if (services != null)
            for (int i = 0; i < services.size(); i++) {
                finalPrice += Integer.valueOf(services.get(i).getPrice());
            }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (onStepReady != null) {
            onStepReady.OnReady(3, true);
        }
        view = inflater.inflate(R.layout.fragment_step_three, container, false);
        initView();
        initRCV();
        return view;
    }

    private void initView() {
        MyTextView finalPriceTv = view.findViewById(R.id.fragment_step_three_finalPrice);

        finalPriceTv.setText(Utils.numberToTextPrice(finalPrice));
    }

    private void initRCV() {
        rcv = view.findViewById(R.id.fragment_step_three_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter);
    }

}
