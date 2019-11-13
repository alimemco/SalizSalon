package com.alirnp.salizsalon.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Utils;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Utils.isConnected(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            TextView tv = findViewById(R.id.activity_splash_txt);
            tv.setText("internet error");

        }


    }
}
