package com.alirnp.salizsalon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alirnp.salizsalon.CustomViews.MyButton;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

public class ErrorConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private MyButton tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_connection);

        initView();
        setListener();

    }

    private void setListener() {
        tryAgainButton.setOnClickListener(this);
    }

    private void initView() {
        tryAgainButton = findViewById(R.id.activity_error_connection_button);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.activity_error_connection_button) {
            if (Utils.isConnected(ErrorConnectionActivity.this)) {
                startActivity(new Intent(ErrorConnectionActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
