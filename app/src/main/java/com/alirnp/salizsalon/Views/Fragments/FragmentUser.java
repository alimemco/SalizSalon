package com.alirnp.salizsalon.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Interface.OnLoginUser;
import com.alirnp.salizsalon.Model.InterfaceModel;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Views.Activities.ActivityLogin;
import com.alirnp.salizsalon.Views.Activities.ActivityRegister;


public class FragmentUser extends Fragment implements
        View.OnClickListener {

    private View view;
    private MyButton loginBtn, registerBtn;
    private OnLoginUser onLoginUser;


    public FragmentUser(OnLoginUser onLoginUser) {
        this.onLoginUser = onLoginUser;
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
                Intent register = new Intent(getContext(), ActivityRegister.class);
                register.putExtra(Constants.INTERFACE_ON_LOGIN_USER, new InterfaceModel(onLoginUser));
                startActivity(register);
                break;

            case R.id.fragment_user_btn_login:
                Intent login = new Intent(getContext(), ActivityLogin.class);
                login.putExtra(Constants.INTERFACE_ON_LOGIN_USER, new InterfaceModel(onLoginUser));
                startActivity(login);
                break;
        }
    }
}
