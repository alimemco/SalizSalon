package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Dialog.LoadingDialog;
import com.alirnp.salizsalon.Interface.OnLoginUser;
import com.alirnp.salizsalon.Model.JSON.Result;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener {

    private MyEditText firstNameEdt, lastNameEdt, phoneEdt, passwordEdt;
    private String firstName, lastName, phone, password;
    private Map<String, String> infoMap = new HashMap<>();

    private LoadingDialog dialog;

    private OnLoginUser onLoginUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initViews();

    }

    private void initViews() {
        firstNameEdt = findViewById(R.id.activity_register_firstName);
        lastNameEdt = findViewById(R.id.activity_register_lastName);
        phoneEdt = findViewById(R.id.activity_register_phone);
        passwordEdt = findViewById(R.id.activity_register_password);

        MyButton register = findViewById(R.id.activity_register_registerBtn);
        register.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_register_registerBtn) {

            if (validateInfo()) {
                registerToServer();
            } else {
                Toast.makeText(this, "Not Validate", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInfo() {


        if (firstNameEdt.getText() != null
                && lastNameEdt.getText() != null &&
                phoneEdt.getText() != null &&
                passwordEdt.getText() != null) {

            firstName = firstNameEdt.getText().toString();
            lastName = firstNameEdt.getText().toString();
            phone = firstNameEdt.getText().toString();
            password = firstNameEdt.getText().toString();

            if (!firstName.equals("") &&
                    !lastName.equals("") &&
                    !phone.equals("") &&
                    !password.equals("")) {

                infoMap.put(Constants.FIRST_NAME, firstName);
                infoMap.put(Constants.LAST_NAME, lastName);
                infoMap.put(Constants.PHONE, phone);
                infoMap.put(Constants.PASSWORD, password);
                return true;
            }
        }

        return false;
    }


    private void registerToServer() {
        MyApplication.getApi().registerOrLogin(Constants.REGISTER, infoMap).enqueue(callback());
        showLoading();
    }

    private Callback<ArrayList<Result>> callback() {
        return new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (Boolean.parseBoolean(response.body().get(0).getSuccess())) {
                            Toast.makeText(ActivityRegister.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                            MyApplication.saveUserInSharePreference(ActivityRegister.this, new User());
                            finish();

                        } else {
                            if (response.body().get(0).getMessage().equals("userExist"))
                                Toast.makeText(ActivityRegister.this, "شماره تلفن تکراری است", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                Toast.makeText(ActivityRegister.this, t.toString(), Toast.LENGTH_SHORT).show();
                dismissLoading();
            }
        };
    }
/*
    private void saveUserInSharePreference() {

        SharedPrefManager sharedPrefManager = new SharedPrefManager(ActivityRegister.this);
        sharedPrefManager.saveUser(new User(firstName, lastName, phone));
    }*/

    private void showLoading() {
        FragmentManager fm = getSupportFragmentManager();
        dialog = new LoadingDialog();
        dialog.show(fm, "LoadingDialog");
    }

    private void dismissLoading() {
        dialog.dismiss();
    }


}
