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
import com.alirnp.salizsalon.NestedJson.Item;
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

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {

    private MyEditText phoneEdt, passwordEdt;
    private MyButton loginBtn;
    private Map<String, String> loginInfo;

    private String phone;
    private String password;

    private LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }


    private void initViews() {
        phoneEdt = findViewById(R.id.activity_login_phone);
        passwordEdt = findViewById(R.id.activity_login_password);
        loginBtn = findViewById(R.id.activity_login_loginBtn);

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_login_loginBtn) {

            if (validatedInfo()) {
                showLoading();
                MyApplication.getApi().userManager(Constants.LOGIN, loginInfo).enqueue(callback());
            }


        }
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
                            loginSuccess(result);

                        } else {
                            if (result.getMessage().equals("userNotFound"))
                                Toast.makeText(ActivityLogin.this, "شماره تلفن وجود ندارد", Toast.LENGTH_SHORT).show();
                            else if (result.getMessage().equals("passwordNotMatch"))
                                Toast.makeText(ActivityLogin.this, "پسورد اشتباه است", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ActivityLogin.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(ActivityLogin.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityLogin.this, "unSuccess", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SalizResponse> call, Throwable t) {

                Utils.log(ActivityLogin.class, t.getMessage());
                Toast.makeText(ActivityLogin.this, R.string.error_fail, Toast.LENGTH_LONG).show();
                dismissLoading();
            }
        };
    }

    private void loginSuccess(Result result) {
        Item item = result.getItems().get(0);
        String firstName = item.getFirst_name();
        String lastName = item.getLast_name();
        String level = item.getLevel();

        Toast.makeText(ActivityLogin.this, "با موفقیت وارد شدید", Toast.LENGTH_SHORT).show();

        User user = new User(firstName, lastName, phone, level);
        MyApplication.saveUserInSharePreference(ActivityLogin.this, user);
        finish();
        Utils.sendMessageLogin(ActivityLogin.this);
    }

    private boolean validatedInfo() {

        //TODO fix#1 if edit text is empty return false

        if (phoneEdt.getText() != null && passwordEdt.getText() != null) {
            phone = phoneEdt.getText().toString();
            password = passwordEdt.getText().toString();

            loginInfo = new HashMap<>();
            loginInfo.put(Constants.PHONE, phone);
            loginInfo.put(Constants.PASSWORD, password);

            return true;
        }
        return false;
    }


    private void showLoading() {
        FragmentManager fm = getSupportFragmentManager();
        dialog = new LoadingDialog();
        dialog.show(fm, "LoadingDialog");
    }

    private void dismissLoading() {
        if (dialog != null)
            dialog.dismiss();
    }


}
