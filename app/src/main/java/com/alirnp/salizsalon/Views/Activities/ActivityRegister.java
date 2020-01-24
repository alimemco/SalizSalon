package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Dialog.LoadingDialog;
import com.alirnp.salizsalon.Model.User;
import com.alirnp.salizsalon.NestedJson.Result;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.MyApplication;
import com.alirnp.salizsalon.Utils.Utils;

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
                if (phoneIsValidate()) {
                    registerToServer();
                } else {
                    Toast.makeText(this, "شماره تلفن صحیح نیست", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Not Validate", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean phoneIsValidate() {
        if (phone != null)
            return phone.matches("(\\+98|0)?9\\d{9}");
        return false;
    }

    private boolean validateInfo() {


        if (firstNameEdt.getText() != null
                && lastNameEdt.getText() != null &&
                phoneEdt.getText() != null &&
                passwordEdt.getText() != null) {

            firstName = firstNameEdt.getText().toString();
            lastName = lastNameEdt.getText().toString();
            phone = phoneEdt.getText().toString();
            password = passwordEdt.getText().toString();

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
        showLoading();
        MyApplication.getApi().userManager(Constants.REGISTER, infoMap).enqueue(callback());
    }

    private Callback<SalizResponse> callback() {
        return new Callback<SalizResponse>() {
            @Override
            public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {
                dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Result result = response.body().getResult().get(0);
                        if (Boolean.parseBoolean(result.getSuccess())) {

                            registerSuccess();

                        } else {
                            if (result.getMessage().equals("userExist"))
                                Toast.makeText(ActivityRegister.this, "شماره تلفن تکراری است", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<SalizResponse> call, Throwable t) {
                Utils.log(ActivityLogin.class, t.getMessage());
                Toast.makeText(ActivityRegister.this, R.string.error_fail, Toast.LENGTH_LONG).show();
                dismissLoading();
            }
        };
    }

    private void registerSuccess() {
        Toast.makeText(ActivityRegister.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        MyApplication.saveUserInSharePreference(ActivityRegister.this,
                new User(
                        firstName,
                        lastName,
                        phone,
                        Constants.user_level.NEW_COMER.getLevel()));
        finish();
        Utils.sendMessageLogin(ActivityRegister.this);
    }

    private void showLoading() {
        FragmentManager fm = getSupportFragmentManager();
        dialog = new LoadingDialog();
        dialog.show(fm, "LoadingDialog");
    }

    private void dismissLoading() {
        dialog.dismiss();
    }


}
