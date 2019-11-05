package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.Interface.OnLogoutUser;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;


public class FragmentUserInfo extends Fragment implements View.OnClickListener {

    private View view;
    private TextView textView;
    private MyButton exitBtn;

    private OnLogoutUser onLogoutUser;

    public FragmentUserInfo(OnLogoutUser onLogoutUser) {
        this.onLogoutUser = onLogoutUser;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initViews();

        initSharedPreferences();


        return view;
    }

    private void initSharedPreferences() {
        User user = MyApplication.getSharedPrefManager().getUser();
        if (user != null) {
            String firstName = user.getFirstName();
            if (firstName != null)
                textView.setText(firstName);
        }
    }

    private void initViews() {
        textView = view.findViewById(R.id.fragment_user_info_txt);
        exitBtn = view.findViewById(R.id.fragment_user_info_btn_exit);

        exitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_user_info_btn_exit) {

            if (onLogoutUser != null) {
                onLogoutUser.onLogout();
            }
        }

    }
}
