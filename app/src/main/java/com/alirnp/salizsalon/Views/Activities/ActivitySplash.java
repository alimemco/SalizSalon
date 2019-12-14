package com.alirnp.salizsalon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Utils.isConnected(this))
            startActivity(new Intent(this, MainActivity.class));
        else
            startActivity(new Intent(this, ErrorConnectionActivity.class));

        finish();
    }
}
