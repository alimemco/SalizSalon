package com.alirnp.salizsalon.HolderForTestApp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirnp.salizsalon.Adapters.ItemsVerticalAdapter;
import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.ResponseJson;
import com.alirnp.salizsalon.NestedJson.ResultItems;
import com.alirnp.salizsalon.R;
import com.alirnp.salizsalon.Utils.Constants;
import com.alirnp.salizsalon.Utils.Utils;
import com.alirnp.salizsalon.Views.Activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityShop extends AppCompatActivity {

    RecyclerView rcv;
    private Callback<ResponseJson> callbackPosts = new Callback<ResponseJson>() {

        @Override
        public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

            if (response.body() != null) {

                List<ResultItems> result = response.body().getResult();

                rcv.setAdapter(new ItemsVerticalAdapter(result));


            }
        }

        @Override
        public void onFailure(Call<ResponseJson> call, Throwable t) {
            Utils.log(MainActivity.class, t.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initViews();
    }

    private void initViews() {

        rcv = findViewById(R.id.activity_shop_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        MyApplication.getApi().get(Constants.POSTS).enqueue(callbackPosts);

    }

}
