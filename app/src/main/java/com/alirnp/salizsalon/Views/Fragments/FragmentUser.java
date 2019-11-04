package com.alirnp.salizsalon.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Views.Activities.ActivityRegister;


public class FragmentUser extends Fragment implements
        View.OnClickListener {

    private View view;
    private MyButton loginBtn, registerBtn;


    public FragmentUser() {
    }

    public static FragmentUser newInstance() {

        Bundle args = new Bundle();

        FragmentUser fragment = new FragmentUser();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        registerBtn = view.findViewById(R.id.fragment_user_btn_register);
        loginBtn = view.findViewById(R.id.fragment_user_btn_login);

        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_user_btn_register:
                startActivity(new Intent(getContext(), ActivityRegister.class));
                break;

            case R.id.fragment_user_btn_login:

                break;
        }
    }
}
