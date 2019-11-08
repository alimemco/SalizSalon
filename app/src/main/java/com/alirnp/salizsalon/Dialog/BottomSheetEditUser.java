package com.alirnp.salizsalon.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BottomSheetEditUser extends BottomSheetDialogFragment implements View.OnClickListener {


    private MyEditText firstNameEdt, lastNameEdt, phoneEdt, newPasswordEdt;
    private String firstName, lastName, phone, newPhone, password;
    private MyButton editBtn;
    private View view;

    private OnUserUpdate onUserUpdate;

    private LoadingDialog dialog;

    private Map<String, String> infoMap = new HashMap<>();


    public BottomSheetEditUser(OnUserUpdate onUserUpdate) {
        this.onUserUpdate = onUserUpdate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_edit_user, container, false);
        initViews();
        getUserValues();
        return view;
    }

    private void getUserValues() {
        User user = MyApplication.getSharedPrefManager().getUser();

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phone = user.getPhone();

        if (firstName != null)
            firstNameEdt.setText(firstName);

        if (lastName != null)
            lastNameEdt.setText(lastName);

        if (phone != null)
            phoneEdt.setText(phone);
    }

    private void initViews() {
        firstNameEdt = view.findViewById(R.id.bottom_sheet_edit_user_firstName);
        lastNameEdt = view.findViewById(R.id.bottom_sheet_edit_user_lastName);
        phoneEdt = view.findViewById(R.id.bottom_sheet_edit_user_phone);
        newPasswordEdt = view.findViewById(R.id.bottom_sheet_edit_user_newPassword);
        editBtn = view.findViewById(R.id.bottom_sheet_edit_user_btn_edit);

        editBtn.setOnClickListener(this);
    }

    private boolean validateInfo() {

        if (firstNameEdt.getText() != null
                && lastNameEdt.getText() != null &&
                phoneEdt.getText() != null) {

            firstName = firstNameEdt.getText().toString();
            lastName = lastNameEdt.getText().toString();
            newPhone = phoneEdt.getText().toString();
            phone = MyApplication.getSharedPrefManager().getUser().getPhone();

            infoMap.put(Constants.PHONE, phone);

            if (!firstName.equals("") &&
                    !lastName.equals("") &&
                    !newPhone.equals("")) {

                infoMap.put(Constants.FIRST_NAME, firstName);
                infoMap.put(Constants.LAST_NAME, lastName);
                infoMap.put(Constants.NEW_PHONE, "11111");


                if (newPasswordEdt.getText() != null) {
                    password = newPasswordEdt.getText().toString();
                    if (!password.equals("")) {
                        infoMap.put(Constants.PASSWORD, password);
                    }
                }

                return true;
            }
        }

        return false;
    }

    private void showLoading() {
        FragmentManager fm = getChildFragmentManager();
        dialog = new LoadingDialog();
        dialog.show(fm, "LoadingDialog");
    }

    private void dismissLoading() {
        dialog.dismiss();
    }


    private Callback<ArrayList<Result>> callback() {
        return new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Result result = response.body().get(0);
                        if (Boolean.parseBoolean(result.getSuccess())) {

                            editSuccess();

                        } else {
                            Utils.log(getClass(), result.getMessage());
                            Toast.makeText(getContext(), "خطا در تغییر اطلاعات", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                dismissLoading();
            }
        };
    }

    private void editSuccess() {
        Toast.makeText(getContext(), "اطلاعات با موفقیت تغییر کرد", Toast.LENGTH_SHORT).show();
        MyApplication.saveUserInSharePreference(getContext(), new User(firstName, lastName, newPhone));
        dismiss();
        if (onUserUpdate != null)
            onUserUpdate.OnUpdate();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottom_sheet_edit_user_btn_edit) {
            if (validateInfo()) {
                showLoading();
                MyApplication.getApi().userManager(Constants.EDIT, infoMap).enqueue(callback());

            } else
                Toast.makeText(getContext(), "فیلد ها نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
        }
    }


    public interface OnUserUpdate {
        void OnUpdate();
    }
}
