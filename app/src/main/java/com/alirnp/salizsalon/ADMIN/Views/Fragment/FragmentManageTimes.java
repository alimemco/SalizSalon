package com.alirnp.salizsalon.ADMIN.Views.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentManageTimes extends Fragment {


    public FragmentManageTimes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_times, container, false);
    }

}
