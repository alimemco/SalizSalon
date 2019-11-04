package com.alirnp.salizsalon.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.CustomViews.MyEditText;
import com.alirnp.salizsalon.Model.JSON.Result;
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
    private Map<String, String> infoMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            if (!firstNameEdt.getText().toString().equals("") &&
                    !lastNameEdt.getText().toString().equals("") &&
                    !phoneEdt.getText().toString().equals("") &&
                    !passwordEdt.getText().toString().equals("")) {

                infoMap.put(Constants.FIRST_NAME, firstNameEdt.getText().toString());
                infoMap.put(Constants.LAST_NAME, lastNameEdt.getText().toString());
                infoMap.put(Constants.PHONE, phoneEdt.getText().toString());
                infoMap.put(Constants.PASSWORD, passwordEdt.getText().toString());
                return true;
            }
        }

        return false;
    }


    private void registerToServer() {
        MyApplication.getApi().register(Constants.REGISTER, infoMap).enqueue(callback());
    }

    private Callback<ArrayList<Result>> callback() {
        return new Callback<ArrayList<Result>>() {
            @Override
            public void onResponse(Call<ArrayList<Result>> call, Response<ArrayList<Result>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (Boolean.parseBoolean(response.body().get(0).getSuccess())) {
                            Toast.makeText(ActivityRegister.this, "Success :D", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityRegister.this, response.body().get(0).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Result>> call, Throwable t) {
                Toast.makeText(ActivityRegister.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


}
