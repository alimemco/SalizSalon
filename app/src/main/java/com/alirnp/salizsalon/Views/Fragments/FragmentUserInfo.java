package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;


public class FragmentUserInfo extends Fragment {

    private View view;
    private TextView textView;

    public FragmentUserInfo() {
    }

    public static FragmentUserInfo newInstance() {

        Bundle args = new Bundle();

        FragmentUserInfo fragment = new FragmentUserInfo();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initViews();

        User user = MyApplication.getSharedPrefManager().getUser();
        if (user != null) {
            String firstName = user.getFirstName();
            if (firstName != null)
                textView.setText(firstName + " test ");
        }

        return view;
    }

    private void initViews() {
        textView = view.findViewById(R.id.fragment_user_info_txt);
    }

}
