package com.alirnp.salizsalon.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Views.Activities.ActivityLogin;
import com.alirnp.salizsalon.Views.Activities.ActivityRegister;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetLoginOrRegister extends BottomSheetDialogFragment implements View.OnClickListener {

    private View view;
    private MyButton login, register;

    public BottomSheetLoginOrRegister() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_user, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        login = view.findViewById(R.id.bottom_sheet_user_login);
        register = view.findViewById(R.id.bottom_sheet_user_register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_sheet_user_login:
                startActivity(new Intent(getContext(), ActivityLogin.class));
                dismiss();
                break;

            case R.id.bottom_sheet_user_register:
                startActivity(new Intent(getContext(), ActivityRegister.class));
                dismiss();
                break;
        }
    }
}