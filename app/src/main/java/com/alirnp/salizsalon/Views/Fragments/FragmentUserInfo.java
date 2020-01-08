package com.alirnp.salizsalon.Views.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyTextView;
import com.alirnp.salizsalon.Dialog.BottomSheetEditUser;
import com.alirnp.salizsalon.Interface.OnLogoutUser;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.alirnp.salizsalon.Utils.Utils;


public class FragmentUserInfo extends Fragment implements View.OnClickListener,
        BottomSheetEditUser.OnUserUpdate {

    private View view;
    private MyTextView nameTv, phoneTv, levelTv;
    private MyButton exitBtn;
    private AppCompatImageView editImg;

    private OnLogoutUser onLogoutUser;


    public FragmentUserInfo() {
    }

    public FragmentUserInfo(OnLogoutUser onLogoutUser) {
        this.onLogoutUser = onLogoutUser;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_info, container, false);
        initViews();

        setValuesFromSharedPref();

        return view;
    }

    private void setValuesFromSharedPref() {
        User user = MyApplication.getSharedPrefManager().getUser();
        if (user != null) {
            String name = user.getFirstName() + " " + user.getLastName();
            String phone = user.getPhone();


            levelTv.setText(Utils.parseUserLevel(user.getLevel()));
            nameTv.setText(name);

            if (phone != null)
                phoneTv.setText(phone);

        }
    }

    private void initViews() {
        nameTv = view.findViewById(R.id.fragment_user_info_txt_name);
        phoneTv = view.findViewById(R.id.fragment_user_info_txt_phone);
        exitBtn = view.findViewById(R.id.fragment_user_info_btn_exit);
        editImg = view.findViewById(R.id.fragment_user_info_img_edit);
        levelTv = view.findViewById(R.id.fragment_user_info_level);

        exitBtn.setOnClickListener(this);
        editImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_user_info_btn_exit:
                if (onLogoutUser != null)
                    onLogoutUser.onLogout();
                break;


            case R.id.fragment_user_info_img_edit:
                showBottomDialog();
                break;
        }

    }

    private void showBottomDialog() {
        BottomSheetEditUser bottomSheetEditUser = new BottomSheetEditUser(this);
        bottomSheetEditUser.show(getChildFragmentManager(), bottomSheetEditUser.getTag());

    }

    @Override
    public void OnUpdate() {
        setValuesFromSharedPref();
    }
}
