package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.Interface.OnStepReady;
import com.alirnp.salizsalon.R;

public class FragmentStepThree extends Fragment {

    private OnStepReady onStepReady;


    public FragmentStepThree(OnStepReady onStepReady) {
        this.onStepReady = onStepReady;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (onStepReady != null) {
            onStepReady.OnReady(3, true);
        }
        return inflater.inflate(R.layout.fragment_step_three, container, false);
    }

}
