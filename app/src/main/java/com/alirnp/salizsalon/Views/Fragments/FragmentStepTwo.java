package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.R;

public class FragmentStepTwo extends Fragment {


    public FragmentStepTwo() {
    }

    public static FragmentStepTwo newInstance() {

        Bundle args = new Bundle();

        FragmentStepTwo fragment = new FragmentStepTwo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_two, container, false);
    }

}
