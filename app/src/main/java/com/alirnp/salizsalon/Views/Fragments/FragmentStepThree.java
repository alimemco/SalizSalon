package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStepThree extends Fragment {


    public FragmentStepThree() {
        // Required empty public constructor
    }

    public static FragmentStepThree newInstance() {

        Bundle args = new Bundle();

        FragmentStepThree fragment = new FragmentStepThree();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_three, container, false);
    }

}
